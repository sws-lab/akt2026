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
        for (HulkStmt stmt : prog.statements()) {
            // evalCond haldab tingimuslauseid
            if (stmt.cond() == null || evalCond(stmt.cond())) {
                Set<Character> elements = evalExpr(stmt.expr());
                env.put(stmt.name(), elements);
            }
        }
    }

    private Set<Character> evalExpr(HulkExpr node) {
        return switch (node) {
            case HulkLit(Set<Character> elements) -> elements;
            case HulkVar(Character name) -> {
                if (env.containsKey(name))
                    yield env.get(name);
                else
                    throw new NoSuchElementException(name.toString());
            }
            case HulkBinOp(Character op, HulkExpr left, HulkExpr right) -> {
                Set<Character> leftValue = evalExpr(left);
                Set<Character> rightValue = evalExpr(right);
                assert leftValue != null;
                assert rightValue != null;
                Set<Character> result = new HashSet<>(leftValue);
                switch (op) {
                    case '+' -> result.addAll(rightValue);
                    case '-' -> result.removeAll(rightValue);
                    case '&' -> result.retainAll(rightValue);
                }
                yield result;
            }
        };
    }

    private boolean evalCond(HulkCond cond) {
        Set<Character> subset = evalExpr(cond.subset());
        Set<Character> superset = evalExpr(cond.superset());
        assert superset != null;
        assert subset != null;
        return superset.containsAll(subset);
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
