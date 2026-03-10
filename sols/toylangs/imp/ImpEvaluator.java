package toylangs.imp;

import toylangs.imp.ast.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImpEvaluator {
    private final Map<Character, Integer> env = new HashMap<>();

    public static int eval(ImpProg prog) {
        ImpEvaluator impEvaluator = new ImpEvaluator();
        return impEvaluator.evalNode(prog);
    }

    private int evalNode(ImpNode node) {
        return switch (node) {
            case ImpNum(int value) -> value;
            case ImpNeg(ImpNode exp) -> -evalNode(exp);
            case ImpAdd(ImpNode left, ImpNode right) -> evalNode(left) + evalNode(right);
            case ImpDiv(ImpNode numerator, ImpNode denominator) -> evalNode(numerator) / evalNode(denominator);
            case ImpVar(char name) -> env.get(name);
            case ImpAssign(char name, ImpNode exp) -> {
                env.put(name, evalNode(exp));
                yield 0; // tegelikkuses pole tagastusväärtust
            }
            case ImpProg(ImpNode exp, List<ImpAssign> assigns) -> {
                for (ImpAssign assign : assigns) {
                    evalNode(assign); // tagastusväärtust 0 ignoreeritakse
                }
                yield evalNode(exp);
            }
        };
    }
}
