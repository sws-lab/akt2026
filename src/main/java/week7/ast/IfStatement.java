package week7.ast;

import java.util.Arrays;
import java.util.List;

/**
 * if-lause.
 */
public record IfStatement(Expression condition, Block thenBranch, Block elseBranch) implements Statement {

    @Override
    public List<Object> getChildren() {
        return Arrays.asList(condition, thenBranch, elseBranch);
    }

    @Override
    public String toString() {
        return "IfStatement(" +
                condition +
                ", " + thenBranch +
                ", " + elseBranch +
                ")";
    }
}
