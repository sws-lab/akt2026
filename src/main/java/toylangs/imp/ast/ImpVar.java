package toylangs.imp.ast;

public record ImpVar(char name) implements ImpNode {
    @Override
    public String toString() {
        return "var(" +
                "'" + name + "'" +
                ")";
    }
}
