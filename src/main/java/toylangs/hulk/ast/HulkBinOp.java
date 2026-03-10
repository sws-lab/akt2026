package toylangs.hulk.ast;

// Esindab tehet kahe hulga vahel. Lubatud on:
// '+'  ühend
// '&'  ühisosa
// '-'  vahe
public record HulkBinOp(Character op, HulkExpr left, HulkExpr right) implements HulkExpr {

    public HulkBinOp {
        if (op != '+' && op != '&' && op != '-') {
            throw new IllegalArgumentException(op.toString());
        }
    }

    @Override
    public String toString() {
        return "binop(" +
                "'" + op + "'" +
                ", " + left +
                ", " + right +
                ")";
    }

    @Override
    public String prettyPrint() {
        return "(" + left.prettyPrint() + op.toString() + right.prettyPrint() + ")";
    }
}
