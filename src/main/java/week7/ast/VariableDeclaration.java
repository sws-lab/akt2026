package week7.ast;

import java.util.Arrays;
import java.util.List;

/**
 * Muutuja deklaratsioon.
 */
public final class VariableDeclaration implements VariableBinding, Statement {

    private final String variableName;
    /**
     * Muutuja tüüp. Puudumise korral {@code null}.
     */
    private String type;
    /**
     * Algväärtusavaldis. Puudumise korral {@code null}.
     */
    private final Expression initializer;

    public VariableDeclaration(String variableName, String type, Expression initializer) {
        this.variableName = variableName;
        this.type = type;
        this.initializer = initializer;
    }

    public VariableDeclaration(String variableName, Expression initializer) {
        this(variableName, null, initializer);
    }

    @Override
    public String variableName() {
        return variableName;
    }

    @Override
    public String type() {
        return type;
    }

    public Expression getInitializer() {
        return initializer;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public List<Object> getChildren() {
        return Arrays.asList(variableName, type, initializer);
    }

    @Override
    public String toString() {
        return "VariableDeclaration(" +
                "\"" + variableName + "\"" +
                (type == null ? ", " + null : ", \"" + type + "\"") +
                ", " + initializer +
                ")";
    }
}
