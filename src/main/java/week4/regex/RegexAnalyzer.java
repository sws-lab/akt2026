package week4.regex;

import week4.regex.ast.*;

import java.util.HashSet;
import java.util.Set;

public class RegexAnalyzer {

    public static boolean matchesEmptyWord(RegexNode regex) {
        return false;
    }


    public static Set<Character> getFirst(String regex) {
        RegexNode node = RegexParser.parse(regex);
        return null;
    }


    public static Set<String> getAllWords(RegexNode regex) {
        throw new UnsupportedOperationException();
    }

    private static Set<String> combine(Set<String> s1, Set<String> s2) {
        Set<String> result = new HashSet<>();
        return result;
    }
}
