package toylangs.hulk;

import toylangs.hulk.ast.*;

import java.util.*;

import static toylangs.hulk.ast.HulkNode.*;

public class HulkEvaluator {
    private final Map<Character, Set<Character>> env;

    public HulkEvaluator(Map<Character, Set<Character>> initialEnv) {
        this.env = new HashMap<>(initialEnv); // teeme koopia, sest initialEnv pole muudetav
    }

    public static Map<Character, Set<Character>> eval(HulkProg prog, Map<Character, Set<Character>> initialEnv) {
        HulkEvaluator hulkEvaluator = new HulkEvaluator(initialEnv);
        hulkEvaluator.evalProg(prog);
        return hulkEvaluator.env;
    }

    private void evalProg(HulkProg prog) {
        throw new UnsupportedOperationException();
    }

    static void main() {
        ArrayList<HulkStmt> laused = new ArrayList<>();
        laused.add(stmt('A', lit('a'), null));  // A := {a}
        laused.add(stmt('B', lit('b'), null));  // B := {b}
        laused.add(stmt('D', binop('+', var('A'), var('B')), null));  // D := (A+B)
        HulkProg s = prog(laused);
        System.out.println(s);
        System.out.println();

        Map<Character, Set<Character>> env = new HashMap<>();
        eval(s, env);
        System.out.println(env);  // {A=[a], B=[b], D=[a, b]}
    }
}
