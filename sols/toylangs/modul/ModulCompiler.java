package toylangs.modul;

import cma.CMaProgram;
import cma.CMaProgramWriter;
import toylangs.modul.ast.*;

import java.util.List;

import static cma.instruction.CMaBasicInstruction.Code.*;
import static cma.instruction.CMaIntInstruction.Code.LOADA;
import static cma.instruction.CMaIntInstruction.Code.LOADC;

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
        switch (expr) {
            case ModulNum(int value) -> pw.visit(LOADC, value);
            case ModulVar(String name) -> {
                int variableIndex = variables.indexOf(name);
                if (variableIndex < 0)
                    throw new ModulException();
                else {
                    pw.visit(LOADA, variableIndex);
                }
            }
            case ModulNeg(ModulExpr exp) -> {
                compile(exp);
                pw.visit(NEG);
            }
            case ModulAdd(ModulExpr left, ModulExpr right) -> {
                compile(left);
                compile(right);
                pw.visit(ADD);
            }
            case ModulMul(ModulExpr left, ModulExpr right) -> {
                compile(left);
                compile(right);
                pw.visit(MUL);
            }
            case ModulPow(ModulExpr base, int power) -> compilePow(base, power);
        }

        normalize(); // ModulPow korral üleliigne
    }

    // Eraldi meetod, et ModulMaster saaks taaskasutada.
    protected void compilePow(ModulExpr base, int power) {
        if (power == 0) {
            pw.visit(LOADC, 1);
        } else {
            compile(base);
            for (int i = 0; i < power - 1; i++) {
                pw.visit(DUP);
            }
            for (int i = 0; i < power - 1; i++) {
                pw.visit(MUL);
                normalize();
            }
        }
    }

    /**
     * Normaliseerib väärtuse poollõiku [0, m).
     */
    protected void normalize() {
        pw.visit(LOADC, m);
        pw.visit(MOD);
        pw.visit(LOADC, m);
        pw.visit(ADD);
        pw.visit(LOADC, m);
        pw.visit(MOD);
    }
}
