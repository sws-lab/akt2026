package toylangs.dialoog.ast;

import cma.instruction.CMaBasicInstruction;

import java.util.Arrays;
import java.util.Map;
import java.util.function.UnaryOperator;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public record DialoogUnary(UnOp op, DialoogNode expr) implements DialoogNode {
    public enum UnOp {
        DialoogNeg("-", x -> -(int) x),
        DialoogNot("!", x -> !(boolean) x);

        private final String symb;
        private final UnaryOperator<Object> javaOp;

        UnOp(String symb, UnaryOperator<Object> javaOp) {
            this.symb = symb;
            this.javaOp = javaOp;
        }

        public String getSymb() {
            return symb;
        }

        public UnaryOperator<Object> toJava() {
            return javaOp;
        }

        public CMaBasicInstruction.Code toCMa() {
            return CMaBasicInstruction.Code.valueOf(toString().substring(7).toUpperCase());
        }

        private final static Map<String, UnOp> symbolMap =
                Arrays.stream(UnOp.values()).collect(toMap(UnOp::getSymb, identity()));

        public static UnOp fromSymb(String symb) {
            return symbolMap.get(symb);
        }
    }

    public DialoogUnary(String symb, DialoogNode expr) {
        this(UnOp.fromSymb(symb), expr);
    }

    @Override
    public String getNodeLabel() {
        return op.toString().substring(7).toLowerCase();
    }

    @Override
    public String toString() {
        return "unop(" +
                "\"" + op.getSymb() + "\"" +
                ", " + expr +
                ")";
    }
}
