package toylangs.parm;

import toylangs.parm.ast.*;

import java.util.HashMap;
import java.util.Map;

public class ParmEvaluator {
    private final Map<String, Integer> env;

    public ParmEvaluator() {
        this(new HashMap<>());
    }

    public ParmEvaluator(Map<String, Integer> env) {
        this.env = env;
    }

    public static int eval(ParmNode node) {
        ParmEvaluator parmEvaluator = new ParmEvaluator();
        return parmEvaluator.evalNode(node);
    }

    private int evalNode(ParmNode node) {
        return switch (node) {
            case ParmLit(int value) -> value;
            case ParmVar(String name) -> env.get(name);
            case ParmPlus(ParmNode left, ParmNode right) -> evalNode(left) + evalNode(right);
            case ParmUpdate(String variable, ParmNode value) -> {
                int val = evalNode(value);
                env.put(variable, val);
                yield val;
            }
            case ParmCompose(ParmNode fst, ParmNode snd, boolean parallel) -> {
                if (parallel) {
                    // leiame esimese avaldise väärtuskeskkonna muutused
                    Map<String, Integer> updates = computeUpdates(fst);

                    // väärtustame algse keskkonna
                    int result = evalNode(snd);
                    // lisame algsele keskkonnale paralleelse keskkonna väärtused
                    env.putAll(updates);

                    // tagastame teise avaldise väärtuse, teades ka paralleelse keskkonna väärtusi
                    yield result;
                } else {
                    evalNode(fst);
                    yield evalNode(snd);
                }
            }
        };
    }

    /**
     * Abimeetod paralleelse komponeerimise jaoks.
     * Väärtustab avaldist ja taastab keskkonda.
     * Tagastab ainult muutujad, mille väärtus on muutunud.
     */
    private Map<String, Integer> computeUpdates(ParmNode expr) {
        // loome paralleelsuse jaoks teise evaluator-objekti
        ParmEvaluator parallelEvaluator = new ParmEvaluator(new HashMap<>(env));

        // läbime paralleelse väärtustajaga
        parallelEvaluator.evalNode(expr);

        // jätame paralleelse väärtustaja keskkonnas alles ainult uued kirjed
        // PS: tuleb arvestada nii võtme kui ka väärtusega!
        parallelEvaluator.env.entrySet().removeAll(env.entrySet());

        // tagastame ainult uued kirjed
        return parallelEvaluator.env;
    }
}
