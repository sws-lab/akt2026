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

    public DialoogEvaluator(Map<String, Object> env) {
        this.env = env;
    }

    public static Object eval(DialoogProg prog, Map<String, Object> env) {
        DialoogEvaluator dialoogEvaluator = new DialoogEvaluator(env);
        return dialoogEvaluator.eval(prog);
    }

    private Object eval(DialoogNode node) {
        throw new UnsupportedOperationException();
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
