package toylangs.hulk.ast;

// Esindab hulga nime. Kokkuleppeliselt ladina suurtähed.
public record HulkVar(Character name) implements HulkExpr {
    @Override
    public String prettyPrint() {
        return name.toString();
    }

    @Override
    public String toString() {
        return "var(" +
                "'" + name + "'" +
                ")";
    }
}
