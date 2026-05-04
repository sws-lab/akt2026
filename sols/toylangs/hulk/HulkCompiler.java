package toylangs.hulk;

import cma.CMaLabel;
import cma.CMaProgram;
import cma.CMaProgramWriter;
import toylangs.hulk.ast.*;

import java.util.*;

import static cma.CMaUtils.bool2int;
import static cma.instruction.CMaBasicInstruction.Code.*;
import static cma.instruction.CMaIntInstruction.Code.*;
import static cma.instruction.CMaLabelInstruction.Code.JUMPZ;

public class HulkCompiler {
    private static final List<Character> SET_VARIABLES = Arrays.asList('X', 'A', 'B', 'C', 'D', 'G', 'H', 'V'); // kasuta SET_VARIABLES.indexOf

    // kasuta hulga literaali teisendamiseks arvuks (bitset)
    private static int set2int(Set<Character> set) {
        List<Character> ELEM_VARIABLES = Arrays.asList('x', 'y', 'z', 'a', 'b', 'c', 'u', 'v');
        int result = 0;
        if (set != null) {
            for (int i = 0; i < ELEM_VARIABLES.size(); i++) {
                result |= bool2int(set.contains(ELEM_VARIABLES.get(i))) << i;
            }
        }
        return result;
    }

    private final CMaProgramWriter pw = new CMaProgramWriter();

    public static CMaProgram compile(HulkProg prog) {
        HulkCompiler hulkCompiler = new HulkCompiler();
        hulkCompiler.compileNode(prog);
        return hulkCompiler.pw.toProgram();
    }

    private void compileNode(HulkNode node) {
        switch (node) {
            case HulkLit(Set<Character> elements) -> pw.visit(LOADC, set2int(elements));
            case HulkVar(Character name) -> pw.visit(LOADA, SET_VARIABLES.indexOf(name));
            case HulkBinOp(Character op, HulkExpr left, HulkExpr right) -> {
                compileNode(left);
                compileNode(right);
                switch (op) {
                    case '+' -> pw.visit(OR);
                    case '-' -> {
                        // A - B = A & ~B = A & (B ^ 11..1) = A & (B ^ -1)
                        pw.visit(LOADC, -1);
                        pw.visit(XOR);
                        pw.visit(AND);
                    }
                    case '&' -> pw.visit(AND);
                }
            }
            case HulkStmt(Character name, HulkExpr expr, HulkCond cond) -> {
                if (cond == null)
                    pw.visit(LOADC, 1);
                else
                    compileNode(cond);

                CMaLabel _endif = new CMaLabel();
                pw.visit(JUMPZ, _endif);

                compileNode(expr);
                pw.visit(STOREA, SET_VARIABLES.indexOf(name));
                pw.visit(POP);

                pw.visit(_endif);
            }
            case HulkProg(List<HulkStmt> stmts) -> {
                for (HulkStmt hulkStmt : stmts) {
                    compileNode(hulkStmt);
                }
            }
            case HulkCond(HulkExpr subset, HulkExpr superset) -> {
                compileNode(superset);
                pw.visit(DUP);

                compileNode(subset);

                // võtame ühendi mõlemast hulgast
                pw.visit(OR);

                // kontrollime, kas ühend on sama mis ülemhulk
                pw.visit(EQ);
            }
        }
    }
}
