package week5.grammar;

import week4.regex.RegexParser;
import week4.regex.ast.*;

public class RegexGrammar {

    // Konstandid ilutrüki jaoks
    private static final char EPS = 'ε';
    private static final String ARROW = " → ";

    public static void printGrammar(RegexNode node) {
        new RegexGrammar().printGrammarNode(node, 'S');
    }

    private char nextNt = 'A';
    // Genereeri järgmine mitte-terminal (eeldades, et neid liiga palju ei ole).
    private char getNextNt() {
        if (nextNt == 'S') nextNt++; // Jätame S vahele, et seda saaks kasutada algsümbolina.
        return nextNt++;
    }

    /**
     * @param nt Praegu defineeritav mitte-terminal
     */
    private void printGrammarNode(RegexNode regex, char nt) {
        switch (regex) {
            case Letter(char symbol) -> System.out.println(nt + ARROW + symbol);
            case Epsilon _ -> System.out.println(nt + ARROW + EPS);
            case Repetition(RegexNode child) -> {
                char childNt = getNextNt();
                System.out.println(nt + ARROW + childNt + nt);
                System.out.println(nt + ARROW + EPS);
                printGrammarNode(child, childNt);
            }
            case Concatenation(RegexNode left, RegexNode right) -> {
                char leftNt = getNextNt();
                char rightNt = getNextNt();
                System.out.println(nt + ARROW + leftNt + rightNt);
                printGrammarNode(left, leftNt);
                printGrammarNode(right, rightNt);
            }
            case Alternation(RegexNode left, RegexNode right) -> {
                char leftNt = getNextNt();
                char rightNt = getNextNt();
                System.out.println(nt + ARROW + leftNt);
                System.out.println(nt + ARROW + rightNt);
                printGrammarNode(left, leftNt);
                printGrammarNode(right, rightNt);
            }
        }
    }

    static void main() {
        printGrammar(RegexParser.parse("(a|bc)*"));
    }
}
