package toylangs.hulk.ast;

// Avaldise abstraktne ülemklass.
public sealed interface HulkExpr extends HulkNode permits HulkBinOp, HulkLit, HulkVar {
}
