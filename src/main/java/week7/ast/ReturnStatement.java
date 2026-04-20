package week7.ast;

import java.util.Collections;
import java.util.List;

/**
 * Tagastuslause.
 */
public final class ReturnStatement implements Statement {

    private final Expression expression;

    /**
     * Sidumine AKTK funktsiooni definitsiooniga, millest tagastatakse.
     * Vajalik alles 9. kodutöös!
     */
    private FunctionDefinition functionBinding = null;

    public ReturnStatement(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    public FunctionDefinition getFunctionBinding() {
        return functionBinding;
    }

    public void setFunctionBinding(FunctionDefinition functionBinding) {
        this.functionBinding = functionBinding;
    }

    @Override
    public List<Object> getChildren() {
        return Collections.singletonList(expression);
    }

    @Override
    public String toString() {
        return "ReturnStatement(" +
                expression +
                (functionBinding == null ? "" : ", " + functionBinding) +
                ")";
    }
}
