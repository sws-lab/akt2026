package week7.ast;

import java.util.ArrayList;
import java.util.List;

/**
 * Lausete jada.
 * <p>
 * Kasutatakse nii terve programmi kui ka loogelistes sulgudes olevate lausete jadade tähistamiseks.
 */
public record Block(List<Statement> statements) implements Statement {

    @Override
    public List<Object> getChildren() {
        return new ArrayList<>(statements);
    }

    @Override
    public String toString() {
        return "Block(" +
                statements.toString().replaceAll("[\\[\\]{}]", "") +
                ")";
    }
}
