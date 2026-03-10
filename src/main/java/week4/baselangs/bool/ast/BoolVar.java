package week4.baselangs.bool.ast;

public record BoolVar(char name) implements BoolNode {
    @Override
    public String toString() {
        return "var(" +
                "'" + name + "'" +
                ")";
    }
}
