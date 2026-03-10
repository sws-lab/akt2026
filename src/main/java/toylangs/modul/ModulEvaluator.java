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
        throw new UnsupportedOperationException();
    }
}
