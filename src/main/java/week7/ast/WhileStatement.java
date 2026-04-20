package week7.ast;

import java.util.Arrays;
import java.util.List;

/**
 * while-lause.
 */
public record WhileStatement(Expression condition, Block body) implements Statement {

    @Override
    public List<Object> getChildren() {
        return Arrays.asList(condition, body);
    }

    @Override
    public String toString() {
        return "WhileStatement(" +
                condition +
                ", " + body +
                ")";
    }
}
