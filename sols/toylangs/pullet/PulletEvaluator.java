package toylangs.pullet;

import toylangs.pullet.ast.*;

import java.util.HashMap;
import java.util.Map;

public class PulletEvaluator {

    // Vana hea eval meetod... tuleb avaldist väärtustada!
    public static int eval(PulletNode node) {
        return evalNode(node, new HashMap<>());
    }

    private static int evalNode(PulletNode node, Map<String, Integer> env) {
        return switch (node) {
            case PulletNum(int num) -> num;
            case PulletVar(String name) -> env.get(name);
            case PulletBinding(String name, PulletNode value, PulletNode body) -> {
                Integer evaluatedValue = evalNode(value, env);
                Map<String, Integer> newEnv = new HashMap<>(env);
                newEnv.put(name, evaluatedValue);
                yield evalNode(body, newEnv);
            }
            case PulletSum(String name, PulletNode lo, PulletNode hi, PulletNode body) -> {
                int from = evalNode(lo, env);
                int to = evalNode(hi, env);
                int result = 0;
                for (int i = from; i <= to; i++) {
                    Map<String, Integer> newEnv = new HashMap<>(env);
                    newEnv.put(name, i);
                    result += evalNode(body, newEnv);
                }
                yield result;
            }
            case PulletDiff(PulletNode left, PulletNode right) -> evalNode(left, env) - evalNode(right, env);
        };
    }
}
