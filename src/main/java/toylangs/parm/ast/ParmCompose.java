package toylangs.parm.ast;

public record ParmCompose(ParmNode fst, ParmNode snd, boolean parallel) implements ParmNode {
    @Override
    public String getNodeLabel() {
        return parallel ? "par" : "seq";
    }

    @Override
    public String toString() {
        return (parallel ? "par" : "seq") + "(" +
                "" + fst +
                ", " + snd +
                ")";
    }
}
