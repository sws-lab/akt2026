package toylangs.vhile.ast;

public sealed interface VhileStmt extends VhileNode permits VhileAssign, VhileBlock, VhileEscape, VhileLoop {

}
