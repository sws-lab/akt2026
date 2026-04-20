package toylangs.vhile;

import cma.CMaProgram;
import cma.CMaProgramWriter;
import toylangs.vhile.ast.*;

import java.util.List;

public class VhileCompiler {
    private final CMaProgramWriter pw = new CMaProgramWriter();
    private final List<String> variables;

    private VhileCompiler(List<String> variables) {
        this.variables = variables;
    }

    public static CMaProgram compile(VhileStmt stmt, List<String> variables) {
        VhileCompiler vhileCompiler = new VhileCompiler(variables);
        vhileCompiler.compile(stmt);
        return vhileCompiler.pw.toProgram();
    }

    private void compile(VhileNode node) {
        throw new UnsupportedOperationException();
    }
}
