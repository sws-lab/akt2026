package toylangs.modul;

import cma.CMaProgram;
import cma.CMaProgramWriter;
import toylangs.modul.ast.*;

import java.util.List;

public class ModulCompiler {
    public final CMaProgramWriter pw = new CMaProgramWriter();
    private final int m;
    private final List<String> variables;

    protected ModulCompiler(int m, List<String> variables) {
        this.m = m;
        this.variables = variables;
    }

    public static CMaProgram compile(ModulProg prog, List<String> variables) {
        ModulCompiler modulCompiler = new ModulCompiler(prog.modulus(), variables);
        modulCompiler.compile(prog.expr());
        return modulCompiler.pw.toProgram();
    }

    protected void compile(ModulExpr expr) {
        throw new UnsupportedOperationException();
    }
}
