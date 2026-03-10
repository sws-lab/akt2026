package toylangs.hulk.ast;

// Tingimus esindab alamhulgaks olemist.
// Koosneb kahest avaldisest: sebset (alamhulk) ja superset (ülemhulk).
// On tõene siis, kui iga alamhulga element kuulub ka ülemhulkga.
public record HulkCond(HulkExpr subset, HulkExpr superset) implements HulkNode {
    @Override
    public String prettyPrint() {
        return subset.prettyPrint() + " subset " + superset.prettyPrint();
    }

    @Override
    public String toString() {
        return "cond(" +
                "" + subset +
                ", " + superset +
                ")";
    }
}
