package toylangs.dialoog;

import toylangs.dialoog.ast.*;

import java.util.*;

import static toylangs.dialoog.ast.DialoogNode.*;

public class DialoogEvaluator {

    public static class UndeclaredVariableException extends RuntimeException {
        public UndeclaredVariableException(String variable) {
            super("Variable \"" + variable + "\" has not been declared.");
        }
    }

    private final Map<String, Object> env;
    private final HashSet<Object> declarations = new HashSet<>();

    public DialoogEvaluator(Map<String, Object> env) {
        this.env = env;
    }

    public static Object eval(DialoogProg prog, Map<String, Object> env) {
        DialoogEvaluator dialoogEvaluator = new DialoogEvaluator(env);
        return dialoogEvaluator.eval(prog);
    }

    private Object eval(DialoogNode node) {
        return switch (node) {
            case DialoogLitInt(int value) -> value;
            case DialoogLitBool(boolean value) -> value;
            case DialoogVar(String name) -> {
                if (declarations.contains(name)) yield env.get(name);
                throw new UndeclaredVariableException(name);
            }
            case DialoogDecl(String name, _) -> {
                declarations.add(name);
                yield null;
            }
            case DialoogUnary(DialoogUnary.UnOp op, DialoogNode expr) -> op.toJava().apply(eval(expr));
            case DialoogBinary(DialoogBinary.BinOp op, DialoogNode leftExpr, DialoogNode rightExpr) -> {
                Object v1 = eval(leftExpr);
                Object v2 = eval(rightExpr);
                yield op.toJava().apply(v1, v2);
            }
            case DialoogProg(List<DialoogDecl> decls, DialoogNode expr) -> {
                for (DialoogDecl decl : decls)
                    eval(decl);
                yield eval(expr);
            }
            case DialoogTernary(DialoogNode guardExpr, DialoogNode trueExpr, DialoogNode falseExpr) -> {
                Object evaluatedGuardExpr = eval(guardExpr);
                assert evaluatedGuardExpr != null;
                boolean guard = (boolean) evaluatedGuardExpr;
                yield guard ? eval(trueExpr) : eval(falseExpr);
            }
        };
    }

    static void main() {
        DialoogProg prog = prog(
                decls(iv("x"), bv("a")),
                ifte(var("a"), var("x"), il(0)));
        Map<String, Object> env = new HashMap<>();
        env.put("a", true);
        env.put("x", 7);
        System.out.println(eval(prog, env));
    }
}
