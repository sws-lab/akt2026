package toylangs.parm.ast;

public record ParmVar(String name) implements ParmNode {
    @Override
    public String toString() {
        return "var(" +
                "\"" + name + "\"" +
                ")";
    }
}
