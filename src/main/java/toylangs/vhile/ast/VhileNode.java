package toylangs.vhile.ast;

import toylangs.AbstractNode;

import java.util.List;

public sealed interface VhileNode extends AbstractNode permits VhileExpr, VhileStmt {

    static VhileNum num(int value) {
        return new VhileNum(value);
    }

    static VhileVar var(String name) {
        return new VhileVar(name);
    }

    static VhileBinOp add(VhileExpr left, VhileExpr right) {
        return new VhileBinOp(VhileBinOp.Op.Add, left, right);
    }

    static VhileBinOp mul(VhileExpr left, VhileExpr right) {
        return new VhileBinOp(VhileBinOp.Op.Mul, left, right);
    }

    static VhileBinOp eq(VhileExpr left, VhileExpr right) {
        return new VhileBinOp(VhileBinOp.Op.Eq, left, right);
    }

    static VhileBinOp neq(VhileExpr left, VhileExpr right) {
        return new VhileBinOp(VhileBinOp.Op.Neq, left, right);
    }

    static VhileAssign assign(String name, VhileExpr expr) {
        return new VhileAssign(name, expr);
    }

    static VhileBlock block(List<VhileStmt> stmts) {
        return new VhileBlock(stmts);
    }

    static VhileBlock block(VhileStmt... stmts) {
        return block(List.of(stmts));
    }

    static VhileLoop loop(VhileExpr condition, VhileStmt body) {
        return new VhileLoop(condition, body);
    }

    static VhileEscape escape(int level) {
        return new VhileEscape(level);
    }
}
