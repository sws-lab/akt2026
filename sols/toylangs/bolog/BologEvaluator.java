package toylangs.bolog;

import toylangs.bolog.ast.*;

import java.util.List;
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
        return switch (node) {
            case BologLit(boolean value) -> value;
            case BologVar(String name) -> trueVars.contains(name);
            case BologNand(BologNode left, BologNode right) -> !(eval(left) && eval(right));
            case BologImp(BologNode conclusion, List<BologNode> assumptions) ->
                    eval(conclusion) || !assumptions.stream().allMatch(this::eval);
        };
    }
}
