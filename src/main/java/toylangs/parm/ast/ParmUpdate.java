package toylangs.parm.ast;

public record ParmUpdate(String vname, ParmNode value) implements ParmNode {
    @Override
    public String getNodeLabel() {
        return "up";
    }

    @Override
    public String toString() {
        return "up(" +
                "\"" + vname + "\"" +
                ", " + value +
                ")";
    }
}
