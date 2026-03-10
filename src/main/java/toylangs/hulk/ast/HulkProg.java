package toylangs.hulk.ast;

import java.util.List;
import java.util.stream.Collectors;

// Iga AST algab selle tipuga. Programm koosneb lausete listist.
public record HulkProg(List<HulkStmt> statements) implements HulkNode {
    @Override
    public String prettyPrint() {
        return statements.stream().map(HulkStmt::prettyPrint).collect(Collectors.joining("\n"));
    }

    @Override
    public String toString() {
        return "prog(" +
                "" + statements.toString().replaceAll("[\\[\\]{}]", "") +
                ")";
    }
}
