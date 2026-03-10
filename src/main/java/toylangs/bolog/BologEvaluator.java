package toylangs.bolog;

import toylangs.bolog.ast.BologNode;

import java.util.Set;

public class BologEvaluator {
    private final Set<String> trueVars;

    public BologEvaluator(Set<String> trueVars) {
        this.trueVars = trueVars;
    }

    public static boolean eval(BologNode node, Set<String> trueVars) {
        BologEvaluator bologEvaluator = new BologEvaluator(trueVars);
        return bologEvaluator.eval(node);
    }

    private boolean eval(BologNode node) {
        throw new UnsupportedOperationException();
    }
}
