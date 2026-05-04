package toylangs.imp;

import cma.CMaInterpreter;
import cma.CMaProgram;
import cma.CMaProgramWriter;
import cma.CMaStack;
import toylangs.imp.ast.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

import static cma.instruction.CMaBasicInstruction.Code.*;
import static cma.instruction.CMaIntInstruction.Code.*;
import static toylangs.imp.ast.ImpNode.*;

public class ImpCompiler {
    private final CMaProgramWriter pw = new CMaProgramWriter();
    private final List<Character> variables = new ArrayList<>();

    public static CMaProgram compile(ImpProg prog) {
        ImpCompiler impCompiler = new ImpCompiler();
        impCompiler.compileNode(prog);
        return impCompiler.pw.toProgram();
    }

    private void compileNode(ImpNode node) {
        switch (node) {
            case ImpNum(int value) -> pw.visit(LOADC, value);
            case ImpNeg(ImpNode exp) -> {
                compileNode(exp);
                pw.visit(NEG);
            }
            case ImpAdd(ImpNode left, ImpNode right) -> {
                compileNode(left);
                compileNode(right);
                pw.visit(ADD);
            }
            case ImpDiv(ImpNode numerator, ImpNode denominator) -> {
                compileNode(numerator);
                compileNode(denominator);
                pw.visit(DIV);
            }
            case ImpVar(char name) -> {
                int variableIndex = variables.indexOf(name);
                if (variableIndex < 0) throw new NoSuchElementException("Undefined variable " + name);
                pw.visit(LOADA, variableIndex);
            }
            case ImpAssign(char name, ImpNode exp) -> {
                compileNode(exp);

                int variableIndex = variables.indexOf(name);
                if (variableIndex >= 0) {
                    pw.visit(STOREA, variableIndex);
                    pw.visit(POP);
                } else {
                    variables.add(name);
                }
            }
            case ImpProg(ImpNode exp, List<ImpAssign> assigns) -> {
                for (ImpAssign assign : assigns) {
                    compileNode(assign);
                }
                compileNode(exp);
            }
        }
    }

    static void main() throws IOException {
        ImpProg prog = prog(
                var('x'),

                assign('x', num(5)),
                assign('y', add(var('x'), num(1))),
                assign('x', add(var('y'), num(1)))
        );

        // väärtustame otse
        System.out.printf("eval: %d%n", ImpEvaluator.eval(prog));

        // kompileeri avaldist arvutav CMa programm
        CMaProgram program = compile(prog);

        // kirjuta programm faili, mida saab Vam-iga vaadata
        CMaStack initialStack = new CMaStack();
        program.toFile(Paths.get("cmas", "imp.cma"), initialStack);

        // interpreteeri CMa programm
        CMaStack finalStack = CMaInterpreter.run(program, initialStack);
        System.out.printf("compiled: %d%n", finalStack.peek());
        System.out.printf("finalStack: %s%n", finalStack);
    }
}
