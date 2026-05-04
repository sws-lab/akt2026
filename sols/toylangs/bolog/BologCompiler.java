package toylangs.bolog;

import cma.CMaProgram;
import cma.CMaProgramWriter;
import toylangs.bolog.ast.*;

import java.util.Arrays;
import java.util.List;

import static cma.CMaUtils.bool2int;
import static cma.instruction.CMaBasicInstruction.Code.*;
import static cma.instruction.CMaIntInstruction.Code.LOADA;
import static cma.instruction.CMaIntInstruction.Code.LOADC;

public class BologCompiler {

    private static final List<String> VARS = Arrays.asList("P", "Q", "R", "S", "T", "U", "V"); // kasuta VARS.indexOf

    private final CMaProgramWriter pw = new CMaProgramWriter();

    public static CMaProgram compile(BologNode node) {
        BologCompiler bologCompiler = new BologCompiler();
        bologCompiler.compileNode(node);
        return bologCompiler.pw.toProgram();
    }

    private void compileNode(BologNode node) {
        switch (node) {
            case BologLit(boolean value) -> pw.visit(LOADC, bool2int(value));
            case BologVar(String name) -> pw.visit(LOADA, VARS.indexOf(name));
            case BologNand(BologNode left, BologNode right) -> {
                compileNode(left);
                compileNode(right);
                pw.visit(AND);
                pw.visit(NOT);
            }
            case BologImp(BologNode conclusion, List<BologNode> assumptions) -> {
                // Idee: (A -> B === !A || B); kõigepealt aga A = TRUE && A1 && A2 && ...
                pw.visit(LOADC, 1);
                for (BologNode assumption : assumptions) {
                    compileNode(assumption);
                    pw.visit(AND);
                }
                pw.visit(NOT);
                compileNode(conclusion);
                pw.visit(OR);
            }
        }
    }
}
