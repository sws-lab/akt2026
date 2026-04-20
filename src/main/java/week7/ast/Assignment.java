package week7.ast;

import java.util.Arrays;
import java.util.List;

/**
 * Omistuslause.
 */
public final class Assignment implements Statement {

    private final String variableName;
    private final Expression expression;

    /**
     * Sidumine omistatava muutujaga.
     * Vajalik alles 9. kodutöös!
     */
    private VariableBinding binding = null;

    public Assignment(String variableName, Expression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    public String getVariableName() {
        return variableName;
    }

    public Expression getExpression() {
        return expression;
    }

    public VariableBinding getBinding() {
        return binding;
    }

    public void setBinding(VariableBinding binding) {
        this.binding = binding;
    }

    @Override
    public List<Object> getChildren() {
        return Arrays.asList(variableName, expression);
    }

    @Override
    public String toString() {
        return "Assignment(" +
                "\"" + variableName + "\"" +
                ", " + expression +
                ")";
    }
}
