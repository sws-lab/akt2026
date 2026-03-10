package toylangs.bolog.ast;

public record BologVar(String name) implements BologNode {
    @Override
    public String toString() {
        return "var(" +
                "\"" + name + "\"" +
                ")";
    }
}
