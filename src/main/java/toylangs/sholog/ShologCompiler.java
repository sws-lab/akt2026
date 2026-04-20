package toylangs.sholog;

import cma.CMaProgram;
import cma.CMaProgramWriter;
import toylangs.sholog.ast.*;

import java.util.List;

public class ShologCompiler {

    /**
     * Defineerimata muutuja veakood.
     */
    private static final int UNDEFINED_CODE = 127;

    private final CMaProgramWriter pw = new CMaProgramWriter();
    private final List<String> variables;

    public ShologCompiler(List<String> variables) {
        this.variables = variables;
    }

    public static CMaProgram compile(ShologNode node, List<String> variables) {
        ShologCompiler shologCompiler = new ShologCompiler(variables);
        shologCompiler.compile(node);
        return shologCompiler.pw.toProgram();
    }

    private void compile(ShologNode node) {
        throw new UnsupportedOperationException();
    }
}
