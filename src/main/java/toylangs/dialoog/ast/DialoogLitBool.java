package toylangs.dialoog.ast;

public record DialoogLitBool(boolean value) implements DialoogNode {
    @Override
    public String getNodeLabel() {
        return "bl";
    }

    @Override
    public String toString() {
        return "bl(" +
                "" + value +
                ")";
    }
}
