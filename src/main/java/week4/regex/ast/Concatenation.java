package week4.regex.ast;

public record Concatenation(RegexNode left, RegexNode right) implements RegexNode {

    @Override
    public String toString() {
        return "(%s%s)".formatted(left, right);
    }
}
