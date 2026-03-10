package toylangs.bolog.ast;

public record BologLit(boolean value) implements BologNode {
    @Override
    public String getNodeLabel() {
        return "tv";
    }

    @Override
    public String toString() {
        return "tv(" +
                "" + value +
                ")";
    }
}
