package toylangs.pullet;

import cma.*;
import toylangs.pullet.ast.*;

import java.util.*;

import static toylangs.pullet.ast.PulletNode.*;

public class PulletCompiler {
    private final static List<String> VARS = Arrays.asList("x", "y", "z");

    private final CMaProgramWriter pw = new CMaProgramWriter();
    private final Map<String, Integer> env = new HashMap<>();

    public PulletCompiler() {
        // Lisame keskkonda kõik muutujad
        for (String var : VARS)
            env.put(var, VARS.indexOf(var));
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
        throw new UnsupportedOperationException();
    }

    static void main() {
        PulletNode avaldis = let("a", num(666), let("b", diff(var("a"), num(1)), num(3)));
        System.out.println(avaldis);
        System.out.println(PulletEvaluator.eval(avaldis));
        System.out.println(CMaInterpreter.run(compile(avaldis), new CMaStack(0, 0, 0)));
    }
}
