package toylangs.hulk.ast;

// Lause esindab hulgale mingi väärtuse omistamist.
// Koosneb hulga nimest (Character), avaldisest ning tingimusest. Tingimus (cond) võib olla null.
// Lauset ei täideta kui tingimus on väär.
public record HulkStmt(Character name, HulkExpr expr, HulkCond cond) implements HulkNode {
    @Override
    public String prettyPrint() {
        String result = name.toString() + " := " + expr.prettyPrint();
        if (cond != null)
            result += " | " + cond.prettyPrint();
        return result;
    }

    @Override
    public String toString() {
        return "stmt(" +
                "'" + name + "'" +
                ", " + expr +
                ", " + cond +
                ")";
    }
}
