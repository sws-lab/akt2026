package toylangs.vhile.ast;

import java.util.List;

public record VhileBlock(List<VhileStmt> stmts) implements VhileStmt {

    @Override
    public String toString() {
        return "block(" +
                "" + stmts.toString().replaceAll("[\\[\\]{}]", "") +
                ")";
    }
}
