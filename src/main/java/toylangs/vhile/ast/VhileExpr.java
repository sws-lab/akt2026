package toylangs.vhile.ast;

public sealed interface VhileExpr extends VhileNode permits VhileBinOp, VhileNum, VhileVar {

}
