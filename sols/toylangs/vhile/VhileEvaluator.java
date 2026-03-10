package toylangs.vhile;

import cma.CMaUtils;
import toylangs.vhile.ast.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VhileEvaluator {
    private final Map<String, Integer> env;

    private VhileEvaluator(Map<String, Integer> initialEnv) {
        this.env = new HashMap<>(initialEnv); // teeme koopia, sest initialEnv pole muudetav
    }

    public static Map<String, Integer> eval(VhileStmt stmt, Map<String, Integer> initialEnv) {
        VhileEvaluator vhileEvaluator = new VhileEvaluator(initialEnv);

        try {
            vhileEvaluator.evalStmt(stmt);
        } catch (BreakException _) {
            // liiga suur escape level, lõpetab kogu programmi töö erindita
        }

        return vhileEvaluator.env;
    }

    private int evalExpr(VhileExpr expr) {
        return switch (expr) {
            case VhileNum(int value) -> value;
            case VhileVar(String name) -> {
                Integer value = env.get(name);
                if (value == null)
                    throw new VhileException();
                else
                    yield value;
            }
            case VhileBinOp(VhileBinOp.Op op, VhileExpr left, VhileExpr right) -> {
                int leftValue = evalExpr(left);
                int rightValue = evalExpr(right);
                yield switch (op) {
                    case Add -> leftValue + rightValue;
                    case Mul -> leftValue * rightValue;
                    case Eq -> CMaUtils.bool2int(leftValue == rightValue);
                    case Neq -> CMaUtils.bool2int(leftValue != rightValue);
                };
            }
        };
    }

    private void evalStmt(VhileStmt node) {
        switch (node) {
            case VhileAssign(String name, VhileExpr expr) -> {
                if (env.containsKey(name)) {
                    env.put(name, evalExpr(expr));
                } else
                    throw new VhileException();
            }
            case VhileBlock(List<VhileStmt> stmts) -> {
                for (VhileStmt stmt : stmts) {
                    evalStmt(stmt);
                }
            }
            case VhileLoop(VhileExpr condition, VhileStmt body) -> {
                try {
                    while (CMaUtils.int2bool(evalExpr(condition))) {
                        evalStmt(body);
                    }
                } catch (BreakException e) {
                    if (e.getLevel() > 1)
                        throw new BreakException(e.getLevel() - 1);
                }
            }
            case VhileEscape(int level) -> {
                throw new BreakException(level);
            }
        }
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
