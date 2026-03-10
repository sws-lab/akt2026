package week4.regex.ast;

public record Epsilon() implements RegexNode {

    @Override
    public String toString() {
        return "ε";
    }
}
