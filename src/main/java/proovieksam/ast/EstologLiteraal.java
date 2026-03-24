package proovieksam.ast;

public record EstologLiteraal(boolean value) implements EstologNode {
    @Override
    public String getNodeLabel() {
        return "lit";
    }

    @Override
    public String toString() {
        return "lit(" +
                "" + value +
                ")";
    }
}
