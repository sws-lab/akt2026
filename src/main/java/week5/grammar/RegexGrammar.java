package week5.grammar;

import week4.regex.RegexParser;
import week4.regex.ast.*;

public class RegexGrammar {

    // Konstandid ilutrüki jaoks
    private static final char EPS = 'ε';
    private static final String ARROW = " → ";

    public static void printGrammar(RegexNode node) {
        throw new UnsupportedOperationException();
    }

    static void main() {
        printGrammar(RegexParser.parse("(a|bc)*"));
    }
}
