package toylangs.vhile;

import cma.CMaLabel;
import cma.CMaProgram;
import cma.CMaProgramWriter;
import toylangs.vhile.ast.*;

import java.util.LinkedList;
import java.util.List;

import static cma.instruction.CMaBasicInstruction.Code.*;
import static cma.instruction.CMaIntInstruction.Code.*;
import static cma.instruction.CMaLabelInstruction.Code.JUMP;
import static cma.instruction.CMaLabelInstruction.Code.JUMPZ;

public class VhileCompiler {
    private final CMaProgramWriter pw = new CMaProgramWriter();
    private final List<String> variables;
    private final LinkedList<CMaLabel> loopEnds = new LinkedList<>(); // TODO: liiguta compile argumenti?

    private VhileCompiler(List<String> variables) {
        this.variables = variables;
    }

    public static CMaProgram compile(VhileStmt stmt, List<String> variables) {
        VhileCompiler vhileCompiler = new VhileCompiler(variables);
        vhileCompiler.compile(stmt);
        return vhileCompiler.pw.toProgram();
    }

    private void compile(VhileNode node) {
        switch (node) {
            case VhileNum(int value) -> pw.visit(LOADC, value);
            case VhileVar(String name) -> {
                int variableIndex = variables.indexOf(name);
                if (variableIndex < 0)
                    throw new VhileException();
                else {
                    pw.visit(LOADA, variableIndex);
                }
            }
            case VhileBinOp(VhileBinOp.Op op, VhileExpr left, VhileExpr right) -> {
                compile(left);
                compile(right);
                switch (op) {
                    case Add -> pw.visit(ADD);
                    case Mul -> pw.visit(MUL);
                    case Eq -> pw.visit(EQ);
                    case Neq -> pw.visit(NEQ);
                    default -> throw new UnsupportedOperationException("unknown op");
                }
            }
            case VhileAssign(String name, VhileExpr expr) -> {
                int variableIndex = variables.indexOf(name);
                if (variableIndex < 0)
                    throw new VhileException();
                else {
                    compile(expr);
                    pw.visit(STOREA, variableIndex);
                    pw.visit(POP);
                }
            }
            case VhileBlock(List<VhileStmt> stmts) -> {
                for (VhileStmt stmt : stmts) {
                    compile(stmt);
                }
            }
            case VhileLoop(VhileExpr condition, VhileStmt body) -> {
                CMaLabel _while = new CMaLabel();
                CMaLabel _end = new CMaLabel();
                loopEnds.addFirst(_end);

                pw.visit(_while);
                compile(condition);
                pw.visit(JUMPZ, _end);

                compile(body);
                pw.visit(JUMP, _while);

                pw.visit(_end);
                loopEnds.removeFirst();
            }
            case VhileEscape(int level) -> {
                level = level - 1;
                if (level < loopEnds.size())
                    pw.visit(JUMP, loopEnds.get(level));
                else
                    pw.visit(HALT); // liiga suur escape level, lõpetab kompileerimise ja kogu programmi töö erindita

            }
        }
    }
}
