package week6;

import week5.AktkToken;

import java.util.Map;

import static week5.AktkToken.Type.*;

public interface AktkNode {

    static AktkNode num(int value) {
        return new IntLiteral(value);
    }

    static AktkNode var(String name) {
        return new Variable(name);
    }

    private static AktkNode binop(AktkToken.Type type, AktkNode left, AktkNode right) {
        return new BinOp(type, left, right);
    }

    static AktkNode plus(AktkNode left, AktkNode right) {
        return binop(PLUS, left, right);
    }

    static AktkNode minus(AktkNode left, AktkNode right) {
        return binop(MINUS, left, right);
    }

    static AktkNode mul(AktkNode left, AktkNode right) {
        return binop(TIMES, left, right);
    }

    static AktkNode div(AktkNode left, AktkNode right) {
        return binop(DIV, left, right);
    }

    int eval(Map<String, Integer> env);

    default String toPrettyString() {
        return toPrettyString(0);
    }

    String toPrettyString(int contextPriority);

    record IntLiteral(int value) implements AktkNode {

        @Override
        public int eval(Map<String, Integer> env) {
            return value;
        }


        @Override
        public String toString() {
            return "num(" + value + ")";
        }

        @Override
        public String toPrettyString(int contextPriority) {
            return Integer.toString(value);
        }
    }

    record Variable(String name) implements AktkNode {

        @Override
        public int eval(Map<String, Integer> env) {
            return env.get(name());
        }


        @Override
        public String toString() {
            return "var(\"" + name + "\")";
        }


        @Override
        public String toPrettyString(int contextPriority) {
            return name;
        }
    }

    record BinOp(AktkToken.Type op, AktkNode left, AktkNode right) implements AktkNode {

        public BinOp {
            if (!isOperator(op)) throw new IllegalArgumentException();
        }

        private boolean isOperator(AktkToken.Type token) {
            return switch (token) {
                case PLUS, MINUS, TIMES, DIV -> true;
                default -> false;
            };
        }

        @Override
        public int eval(Map<String, Integer> env) {
            int l = left.eval(env);
            int r = right.eval(env);
            return switch (op) {
                case PLUS -> l + r;
                case MINUS -> l - r;
                case TIMES -> l * r;
                case DIV -> l / r;
                default -> throw new AssertionError("Impossible!");
            };
        }


        @Override
        public String toString() {
            return op.toString().toLowerCase() + "(" + left + ", " + right + ")";
        }

        @Override
        public String toPrettyString(int contextPriority) {
            int prio = priorityOf(op);
            int assoc = prio + (isAssoc(op) ? 0 : 1);
            String content = left.toPrettyString(prio) + symbolOf(op) + right.toPrettyString(assoc);
            if (contextPriority > prio) return "(" + content + ")";
            else return content;
        }

        private boolean isAssoc(AktkToken.Type op) {
            return switch (op) {
                case MINUS, DIV -> false;
                default -> true;
            };
        }

        private String symbolOf(AktkToken.Type op) {
            return switch (op) {
                case PLUS -> "+";
                case MINUS -> "-";
                case TIMES -> "*";
                case DIV -> "/";
                default -> null;
            };
        }

        private int priorityOf(AktkToken.Type op) {
            return switch (op) {
                case PLUS, MINUS -> 1;
                case TIMES, DIV -> 2;
                default -> 0;
            };
        }
    }
}
