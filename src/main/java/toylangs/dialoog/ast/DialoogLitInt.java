package toylangs.dialoog.ast;

public record DialoogLitInt(int value) implements DialoogNode {
    @Override
    public String getNodeLabel() {
        return "il";
    }

    @Override
    public String toString() {
        return "il(" +
                "" + value +
                ")";
    }
}
