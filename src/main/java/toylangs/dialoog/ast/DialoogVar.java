package toylangs.dialoog.ast;

public record DialoogVar(String name) implements DialoogNode {
    @Override
    public String toString() {
        return "var(" +
                "\"" + name + "\"" +
                ")";
    }
}
