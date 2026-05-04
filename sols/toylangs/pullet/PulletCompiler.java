package toylangs.pullet;

import cma.*;
import toylangs.pullet.ast.*;

import java.util.*;

import static cma.instruction.CMaBasicInstruction.Code.*;
import static cma.instruction.CMaIntInstruction.Code.*;
import static cma.instruction.CMaLabelInstruction.Code.JUMP;
import static cma.instruction.CMaLabelInstruction.Code.JUMPZ;
import static toylangs.pullet.ast.PulletNode.*;

public class PulletCompiler {
    private final static List<String> VARS = Arrays.asList("x", "y", "z");

    private final CMaProgramWriter pw = new CMaProgramWriter();
    private final Map<String, Integer> env = new HashMap<>();
    private int SP;

    public PulletCompiler() {
        // Lisame keskkonda kõik muutujad
        for (String var : VARS)
            env.put(var, VARS.indexOf(var));
        // Määrame stackpointeri suuruseks keskkonna muutujate suuruse
        this.SP = this.env.size();
    }

    // Koodi genereerimiseks eeldame, et let'id ja summad ei esine alamavaldistena!
    // Meil on lisaks mõned globaalsed muutujad x, y, z, mis asuvad pesades 0, 1 ja 2.
    public static CMaProgram compile(PulletNode node) {
        PulletCompiler compiler = new PulletCompiler();
        compiler.compileNode(node);
        // Tagastame programmi
        return compiler.pw.toProgram();
    }

    private void compileNode(PulletNode node) {
        switch (node) {
            case PulletNum(int num) -> pw.visit(LOADC, num);
            case PulletVar(String name) -> pw.visit(LOADA, env.get(name));
            case PulletBinding(String name, PulletNode value, PulletNode body) -> {
                compileNode(value);
                env.put(name, SP++);
                compileNode(body);
            }
            case PulletSum(String name, PulletNode lo, PulletNode hi, PulletNode body) -> {
                // avaldis kujul idx = lo to hi in ...idx...

                // Alustame idx = lo
                compileNode(lo);
                int idx = SP++;
                env.put(name, idx);

                compileNode(hi);
                int high = SP++;
                CMaLabel _while = new CMaLabel();
                CMaLabel _exit = new CMaLabel();

                // Tulemuse salvestamiseks:
                pw.visit(LOADC, 0);

                // while (idx <= high)
                pw.visit(_while);
                pw.visit(LOADA, idx);
                pw.visit(LOADA, high);
                pw.visit(LEQ);
                pw.visit(JUMPZ, _exit);

                // result += keha;
                compileNode(body);
                pw.visit(ADD);

                // idx++;
                pw.visit(LOADA, idx);
                pw.visit(LOADC, 1);
                pw.visit(ADD);
                pw.visit(STOREA, idx);
                pw.visit(POP);

                pw.visit(JUMP, _while);

                pw.visit(_exit);
            }
            case PulletDiff(PulletNode left, PulletNode right) -> {
                compileNode(left);
                compileNode(right);
                pw.visit(SUB);
            }
        }
    }

    static void main() {
        PulletNode avaldis = let("a", num(666), let("b", diff(var("a"), num(1)), num(3)));
        System.out.println(avaldis);
        System.out.println(PulletEvaluator.eval(avaldis));
        System.out.println(CMaInterpreter.run(compile(avaldis), new CMaStack(0, 0, 0)));
    }
}
