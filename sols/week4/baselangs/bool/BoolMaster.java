package week4.baselangs.bool;

import week4.baselangs.bool.ast.*;

import java.util.Set;
import java.util.TreeSet;

import static week4.baselangs.bool.BoolMaster.DecisionTree.FALSE;
import static week4.baselangs.bool.BoolMaster.DecisionTree.TRUE;
import static week4.baselangs.bool.ast.BoolNode.not;
import static week4.baselangs.bool.ast.BoolNode.or;

public class BoolMaster {

    // Meil on antud järgnev klass andmete kogumiseks:
    public static final class Stats {
        private final Set<Character> variables = new TreeSet<>();
        private boolean found = false;

        public Set<Character> getVariables() {
            return variables;
        }

        public boolean containsImp() {
            return found;
        }

        public void addVar(BoolVar node) {
            variables.add(node.name());
        }

        public void foundImp() {
            found = true;
        }
    }

    // Analüüsida avaldist kasutades Stats klassi meetodid.
    public static Stats analyze(BoolNode node) {
        Stats stats = new Stats();
        analyze(node, stats);
        return stats;
    }

    private static void analyze(BoolNode node, Stats stats) {
        switch (node) {
            case BoolVar var -> stats.addVar(var);
            case BoolNot(BoolNode exp) -> analyze(exp, stats);
            case BoolOr(BoolNode left, BoolNode right) -> {
                analyze(left, stats);
                analyze(right, stats);
            }
            case BoolImp(BoolNode antedecent, BoolNode consequent) -> {
                stats.foundImp();
                analyze(antedecent, stats);
                analyze(consequent, stats);
            }
        }
    }


    // Teisendada avaldises implikatsiooni teiste operaatoritega.
    public static BoolNode transform(BoolNode node) {
        return switch (node) {
            case BoolVar var -> var;
            case BoolNot(BoolNode expr) -> not(transform(expr));
            case BoolOr(BoolNode left, BoolNode right) -> or(transform(left), transform(right));
            case BoolImp(BoolNode antedecent, BoolNode consequent) -> or(
                    not(transform(antedecent)),
                    transform(consequent)
            );
        };
    }


    // Teha avaldisest otsustuspuu.
    public static DecisionTree createDecisionTree(BoolNode node) {
        return switch (node) {
            case BoolVar(char name) -> DecisionTree.decision(name, TRUE, FALSE);
            case BoolNot(BoolNode expr) -> createDecisionTree(expr).compose(FALSE, TRUE);
            case BoolOr(BoolNode left, BoolNode right) ->
                    createDecisionTree(left).compose(TRUE, createDecisionTree(right));
            case BoolImp(BoolNode antedecent, BoolNode consequent) -> {
                DecisionTree cons = createDecisionTree(consequent);
                yield createDecisionTree(antedecent).compose(cons, TRUE);
            }
        };
    }

    /**
     * Otsustuspuu on tõeväärtusavaldise puukujuline esitus, kus vahetipudeks on ainult muutujad.
     * See esitab see avaldise tõeväärtustabelit, vt. meetod eval!
     * <p>
     * Puu loomiseks võib kasutada meetod compose, mis jätkab otsustuspuud
     * x.compose(y,z) tulemuseks on selline puu, et
     * kui x on tõene, siis on tulemuseks y.
     * vastasel korral on tulemuseks z.
     * Seega on näiteks avaldise x eitus lihtsalt x.compose(FALSE, TRUE).
     */
    public abstract static class DecisionTree {

        public static final DecisionTree TRUE = new TrueNode();
        public static final DecisionTree FALSE = new FalseNode();

        public static DecisionTree decision(char c, DecisionTree tc, DecisionTree fc) {
            return new DecisionNode(c, tc, fc);
        }

        public abstract boolean eval(Set<Character> tv);

        public abstract DecisionTree compose(DecisionTree trueDecision, DecisionTree falseDecision);


        private static class DecisionNode extends DecisionTree {
            private final Character variable;
            private final DecisionTree trueTree;
            private final DecisionTree falseTree;

            public DecisionNode(Character variable, DecisionTree trueTree, DecisionTree falseTree) {
                this.variable = variable;
                this.trueTree = trueTree;
                this.falseTree = falseTree;
            }

            @Override
            public boolean eval(Set<Character> tv) {
                return tv.contains(variable) ? trueTree.eval(tv) : falseTree.eval(tv);
            }

            @Override
            public DecisionTree compose(DecisionTree trueDecision, DecisionTree falseDecision) {
                return new DecisionNode(variable,
                        trueTree.compose(trueDecision, falseDecision),
                        falseTree.compose(trueDecision, falseDecision));
            }

            @Override
            public String toString() {
                return "(" + variable + " ? " + trueTree + " : " + falseTree + ")";
            }
        }

        private static class TrueNode extends DecisionTree {
            @Override
            public boolean eval(Set<Character> tv) {
                return true;
            }

            @Override
            public DecisionTree compose(DecisionTree trueDecision, DecisionTree falseDecision) {
                return trueDecision;
            }

            @Override
            public String toString() {
                return "true";
            }
        }

        private static class FalseNode extends DecisionTree {
            @Override
            public boolean eval(Set<Character> tv) {
                return false;
            }

            @Override
            public DecisionTree compose(DecisionTree trueDecision, DecisionTree falseDecision) {
                return falseDecision;
            }

            @Override
            public String toString() {
                return "false";
            }
        }

    }
}
