package toylangs.dialoog.ast;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public record DialoogBinary(BinOp op, DialoogNode leftExpr, DialoogNode rightExpr) implements DialoogNode {

    public enum BinOp {
        DialoogEq("on", Object::equals),

        DialoogAnd("&", (x,y) -> (boolean)x && (boolean)y),
        DialoogOr("|", (x,y) -> (boolean)x || (boolean)y),

        DialoogAdd("+", (x,y) -> (int)x + (int)y),
        DialoogSub("-", (x,y) -> (int)x - (int)y),
        DialoogMul("*", (x,y) -> (int)x * (int)y),
        DialoogDiv("/", (x,y) -> (int)x / (int)y);

        private final String symb;
        private final BinaryOperator<Object> javaOp;

        BinOp(String symb, BinaryOperator<Object> javaOp) {
            this.symb = symb;
            this.javaOp = javaOp;
        }

        public String getSymb() {
            return symb;
        }

        public BinaryOperator<Object> toJava() {
            return javaOp;
        }

        private final static Map<String, BinOp> symbolMap =
                Arrays.stream(BinOp.values()).collect(toMap(BinOp::getSymb, identity()));

        public static BinOp fromSymb(String symb) {
            return symbolMap.get(symb);
        }
    }

    public DialoogBinary(String symb, DialoogNode leftExpr, DialoogNode rightExpr) {
        this(BinOp.fromSymb(symb), leftExpr, rightExpr);
    }

    @Override
    public String getNodeLabel() {
        return op.toString().substring(7).toLowerCase();
    }

    @Override
    public List<Object> getChildren() {
        return List.of(leftExpr, rightExpr);
    }

    @Override
    public String toString() {
        return "binop(" +
                "\"" + op.symb + "\"" +
                ", " + leftExpr +
                ", " + rightExpr +
                ")";
    }
}
