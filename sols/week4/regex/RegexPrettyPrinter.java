package week4.regex;

import week4.regex.ast.*;

public class RegexPrettyPrinter {

    // Ülesanne on ilusasti (see tähendab võimalikult väheste sulgudega) väljastada regulaaravaldised.
    // Vaata aritmeetiliste avaldiste (expr) ilutrükki klassis ExprMaster.
    public static String pretty(RegexNode regex) {
        int priority = priorityOf(regex);
        return switch (regex) {
            case Letter(char symbol) -> Character.toString(symbol);
            case Epsilon _ -> "ε";
            case Alternation(RegexNode left, RegexNode right) -> pretty(left, priority) + '|' + pretty(right, priority);
            case Concatenation(RegexNode left, RegexNode right) -> pretty(left, priority) + pretty(right, priority);
            case Repetition(RegexNode child) -> pretty(child, priority + 1) + '*'; // <-- (a*)* ümber lisasulud.
        };
    }

    private static String pretty(RegexNode regex, int contextPriority) {
        if (priorityOf(regex) < contextPriority)
            return '(' + pretty(regex) + ')';
        else
            return pretty(regex);
    }

    private static int priorityOf(RegexNode regex) {
        return switch (regex) {
            case Alternation _ -> 1;
            case Concatenation _ -> 2;
            case Repetition _ -> 3;
            case Epsilon _, Letter _ -> 4;
        };
    }
}
