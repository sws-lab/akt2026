package toylangs.vhile;

import toylangs.vhile.ast.*;

import java.util.HashMap;
import java.util.Map;

public class VhileEvaluator {
    private final Map<String, Integer> env;

    private VhileEvaluator(Map<String, Integer> initialEnv) {
        this.env = new HashMap<>(initialEnv); // teeme koopia, sest initialEnv pole muudetav
    }

    public static Map<String, Integer> eval(VhileStmt stmt, Map<String, Integer> initialEnv) {
        VhileEvaluator vhileEvaluator = new VhileEvaluator(initialEnv);
        vhileEvaluator.evalStmt(stmt);

        return vhileEvaluator.env;
    }

    private int evalExpr(VhileExpr expr) {
        throw new UnsupportedOperationException();
    }

    private void evalStmt(VhileStmt node) {
        throw new UnsupportedOperationException();
    }

    /**
     * Erind, mille viskamise ja püüdmise abil saab katkestamislauseid käsitleda.
     */
    private static class BreakException extends RuntimeException {
        private final int level;

        public BreakException(int level) {
            this.level = level;
        }

        public int getLevel() {
            return level;
        }
    }
}
