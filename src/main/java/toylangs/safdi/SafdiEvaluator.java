package toylangs.safdi;

import toylangs.safdi.ast.*;

import java.util.Map;

public class SafdiEvaluator {

    public static class SafdiException extends RuntimeException {
    }

    private final Map<String, Integer> env;

    private SafdiEvaluator(Map<String, Integer> env) {
        this.env = env;
    }

    public static int eval(SafdiNode node, Map<String, Integer> env) {
        SafdiEvaluator safdiEvaluator = new SafdiEvaluator(env);
        return safdiEvaluator.evalNode(node);
    }

    private int evalNode(SafdiNode node) {
        throw new UnsupportedOperationException();
    }
}
