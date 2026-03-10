package toylangs.imp.ast;

public record ImpAssign(char name, ImpNode expr) implements ImpNode {
    @Override
    public String toString() {
        return "assign(" +
                "'" + name + "'" +
                ", " + expr +
                ")";
    }
}
