package toylangs.parm.ast;

public record ParmPlus(ParmNode leftExpr, ParmNode rightExpr) implements ParmNode {
    @Override
    public String toString() {
        return "plus(" +
                "" + leftExpr +
                ", " + rightExpr +
                ")";
    }
}
