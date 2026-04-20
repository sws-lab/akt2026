package week7.ast;

import java.util.List;

/**
 * Täisarvuliteraal
 */
public record IntegerLiteral(Integer value) implements Expression {

    @Override
    public List<Object> getChildren() {
        return List.of(value);
    }

    @Override
    public String toString() {
        return "IntegerLiteral(" +
                value +
                ")";
    }
}
