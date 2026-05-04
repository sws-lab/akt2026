package toylangs.dialoog;

import cma.CMaLabel;
import cma.CMaProgram;
import cma.CMaProgramWriter;
import toylangs.dialoog.ast.*;

import java.util.ArrayList;
import java.util.List;

import static cma.CMaUtils.bool2int;
import static cma.instruction.CMaIntInstruction.Code.LOADA;
import static cma.instruction.CMaIntInstruction.Code.LOADC;
import static cma.instruction.CMaLabelInstruction.Code.JUMP;
import static cma.instruction.CMaLabelInstruction.Code.JUMPZ;

public class DialoogCompiler {
    private final CMaProgramWriter pw = new CMaProgramWriter();
    private final List<String> vars = new ArrayList<>();

    public static CMaProgram compile(DialoogProg prog) {
        DialoogCompiler dialoogCompiler = new DialoogCompiler();
        dialoogCompiler.compileNode(prog);
        return dialoogCompiler.pw.toProgram();
    }

    private void compileNode(DialoogNode node) {
        switch (node) {
            case DialoogLitInt(int value) -> {
                pw.visit(LOADC, value);
            }
            case DialoogLitBool(boolean value) -> {
                pw.visit(LOADC, bool2int(value));
            }
            case DialoogVar(String name) -> {
                pw.visit(LOADA, vars.indexOf(name));
            }
            case DialoogDecl(String name, _) -> {
                vars.add(name);
            }
            case DialoogUnary(DialoogUnary.UnOp op, DialoogNode expr) -> {
                compileNode(expr);
                pw.visit(op.toCMa());
            }
            case DialoogBinary(DialoogBinary.BinOp op, DialoogNode leftExpr, DialoogNode rightExpr) -> {
                compileNode(leftExpr);
                compileNode(rightExpr);
                pw.visit(op.toCMa());
            }
            case DialoogTernary(DialoogNode guardExpr, DialoogNode trueExpr, DialoogNode falseExpr) -> {
                CMaLabel _false = new CMaLabel();
                CMaLabel _done = new CMaLabel();

                compileNode(guardExpr);
                pw.visit(JUMPZ, _false);

                compileNode(trueExpr);
                pw.visit(JUMP, _done);

                pw.visit(_false);
                compileNode(falseExpr);

                pw.visit(_done);
            }
            case DialoogProg(List<DialoogDecl> decls, DialoogNode expr) -> {
                for (DialoogDecl decl : decls)
                    compileNode(decl);
                compileNode(expr);
            }
        }
    }
}
