package toylangs.modul;

import toylangs.modul.ast.*;

import java.util.Map;

public class ModulEvaluator {
    private final int m;
    private final Map<String, Integer> env;

    protected ModulEvaluator(int m, Map<String, Integer> env) {
        this.m = m;
        this.env = env;
    }

    public static int eval(ModulProg prog, Map<String, Integer> env) {
        ModulEvaluator modulEvaluator = new ModulEvaluator(prog.modulus(), env);
        return modulEvaluator.eval(prog.expr());
    }

    protected int eval(ModulExpr expr) {
        int result = switch (expr) {
            case ModulNum(int value) -> value;
            case ModulVar(String name) -> {
                Integer value = env.get(name);
                if (value == null)
                    throw new ModulException();
                else
                    yield value;
            }
            case ModulNeg(ModulExpr subExpr) -> -eval(subExpr);
            case ModulAdd(ModulExpr left, ModulExpr right) -> eval(left) + eval(right);
            case ModulMul(ModulExpr left, ModulExpr right) -> eval(left) * eval(right);
            case ModulPow(ModulExpr base, int power) -> evalPow(base, power);
        };

        return normalize(result); // ModulPow korral üleliigne
    }

    // Eraldi meetod, et ModulMaster saaks taaskasutada.
    protected int evalPow(ModulExpr base, int power) {
        int baseValue = eval(base);
        int result = 1;
        for (int i = 0; i < power; i++) {
            result = normalize(result * baseValue);
        }
        return result;
    }

    /**
     * Normaliseerib väärtuse poollõiku [0, m).
     */
    protected int normalize(int value) {
        return ((value % m) + m) % m; // negatiivsete arvude jaoks keerulisem
    }
}
