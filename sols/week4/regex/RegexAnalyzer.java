package week4.regex;

import week4.regex.ast.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class RegexAnalyzer {

    public static boolean matchesEmptyWord(RegexNode regex) {
        return switch (regex) {
            case Letter _ -> false;
            case Epsilon _, Repetition _ -> true;
            case Concatenation(RegexNode left, RegexNode right) -> matchesEmptyWord(left) && matchesEmptyWord(right);
            case Alternation(RegexNode left, RegexNode right) -> matchesEmptyWord(left) || matchesEmptyWord(right);
        };
    }


    public static Set<Character> getFirst(String regex) {
        RegexNode node = RegexParser.parse(regex);
        return getFirstResult(node).firstSet;
    }

    private record ResultType(Set<Character> firstSet, boolean matchesEmpty) {
    }

    private static ResultType getFirstResult(RegexNode regex) {
        return switch (regex) {
            case Alternation(RegexNode left, RegexNode right) -> {
                ResultType leftResult = getFirstResult(left);
                ResultType rightResult = getFirstResult(right);
                Set<Character> chars = new HashSet<>(leftResult.firstSet);
                chars.addAll(rightResult.firstSet);
                yield new ResultType(chars, leftResult.matchesEmpty || rightResult.matchesEmpty);
            }
            case Concatenation(RegexNode left, RegexNode right) -> {
                ResultType leftResult = getFirstResult(left);
                ResultType rightResult = getFirstResult(right);
                Set<Character> chars = new HashSet<>(leftResult.firstSet);
                // Siin on see oluline koht, kus kontrollime tühjust:
                if (leftResult.matchesEmpty) chars.addAll(rightResult.firstSet);
                yield new ResultType(chars, leftResult.matchesEmpty && rightResult.matchesEmpty);
            }
            case Epsilon _ -> new ResultType(Collections.emptySet(), true);
            case Letter(char symbol) -> new ResultType(Collections.singleton(symbol), false);
            case Repetition(RegexNode child) -> {
                ResultType childResult = getFirstResult(child);
                yield new ResultType(childResult.firstSet, true);
            }
        };
    }


    public static Set<String> getAllWords(RegexNode regex) {
        return switch (regex) {
            case Alternation(RegexNode left, RegexNode right) -> {
                Set<String> ret = new HashSet<>(getAllWords(left));
                ret.addAll(getAllWords(right));
                yield ret;
            }
            case Concatenation(RegexNode left, RegexNode right) -> combine(getAllWords(left), getAllWords(right));
            case Epsilon _ -> Collections.singleton("");
            case Letter(char symbol) -> Collections.singleton(Character.toString(symbol));
            case Repetition(RegexNode child) -> {
                Set<String> childWords = getAllWords(child);
                if (childWords.equals(Collections.singleton("")))
                    yield childWords;
                else
                    throw new RuntimeException("Infinite language");
            }
        };
    }

    private static Set<String> combine(Set<String> s1, Set<String> s2) {
        Set<String> result = new HashSet<>();
        for (String w1 : s1) {
            for (String w2 : s2) {
                result.add(w1 + w2);
            }
        }
        return result;
    }
}
