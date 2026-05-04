package toylangs.parm;

import cma.CMaProgram;
import cma.CMaProgramWriter;
import toylangs.parm.ast.*;

import java.util.Arrays;
import java.util.List;

import static cma.instruction.CMaBasicInstruction.Code.ADD;
import static cma.instruction.CMaBasicInstruction.Code.POP;
import static cma.instruction.CMaIntInstruction.Code.*;
import static toylangs.parm.ast.ParmNode.*;

public class ParmCompiler {

    public static final List<String> VARS = Arrays.asList("X", "Y", "Z", "A", "B", "C", "D"); // kasuta VARS.indexOf

    private final CMaProgramWriter pw = new CMaProgramWriter();

    public static CMaProgram compile(ParmNode node) {
        ParmCompiler parmCompiler = new ParmCompiler();
        parmCompiler.compileNode(node);
        return parmCompiler.pw.toProgram();
    }

    private void compileNode(ParmNode node) {
        switch (node) {
            case ParmLit(int value) -> pw.visit(LOADC, value);
            case ParmVar(String name) -> pw.visit(LOADA, VARS.indexOf(name));
            case ParmPlus(ParmNode left, ParmNode right) -> {
                compileNode(left);
                compileNode(right);
                pw.visit(ADD);
            }
            case ParmUpdate(String variable, ParmNode value) -> {
                compileNode(value);
                pw.visit(STOREA, VARS.indexOf(variable));
                // Siin ei ole POP!
            }
            case ParmCompose(ParmNode fst, ParmNode snd, boolean parallel) -> {
                assert !parallel;
                compileNode(fst);
                pw.visit(POP);
                compileNode(snd);
            }
        }
    }

    static void main() {
        CMaProgram cMaProgram = compile(plus(lit(10), lit(12)));
        System.out.println(cMaProgram.toString());
    }
}
