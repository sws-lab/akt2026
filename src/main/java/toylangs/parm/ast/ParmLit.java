package toylangs.parm.ast;

public record ParmLit(int value) implements ParmNode {
    @Override
    public String toString() {
        return "lit(" +
                "" + value +
                ")";
    }
}
