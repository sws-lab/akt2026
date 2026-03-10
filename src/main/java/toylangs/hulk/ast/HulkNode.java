package toylangs.hulk.ast;

import toylangs.AbstractNode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// See on ASTi tippude abstraktne ülemklass.
// Siin olevate staatiliste meetodite abil saad ehitada ASTi.
public sealed interface HulkNode extends AbstractNode permits HulkCond, HulkProg, HulkStmt, HulkExpr {
    static HulkProg prog(List<HulkStmt> stmts) {
        return new HulkProg(stmts);
    }

    static HulkProg prog(HulkStmt... stmts) {
        return prog(Arrays.asList(stmts));
    }

    static HulkStmt stmt(Character name, HulkExpr expr, HulkCond cond) {
        return new HulkStmt(name, expr, cond);
    }

    static HulkCond cond(HulkExpr subset, HulkExpr superset) {
        return new HulkCond(subset, superset);
    }

    static HulkVar var(Character name) {
        return new HulkVar(name);
    }

    static HulkLit lit(Character... elements) {
        return new HulkLit(new HashSet<>(Arrays.asList(elements)));
    }

    static HulkLit lit(Set<Character> elements) {
        return new HulkLit(elements);
    }

    static HulkBinOp binop(Character op, HulkExpr left, HulkExpr right) {
        return new HulkBinOp(op, left, right);
    }

    String prettyPrint();
}
