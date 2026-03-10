package week4.regex.ast;

public record Repetition(RegexNode child) implements RegexNode {

    @Override
    public String toString() {
        return "(%s*)".formatted(child);
    }
}
