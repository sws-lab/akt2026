package week7.ast;

import java.util.Arrays;
import java.util.List;

/**
 * Funktsiooni parameeter funktiooni definitsioonis.
 *
 * @see FunctionDefinition
 */
public record FunctionParameter(String variableName, String type) implements VariableBinding, AstNode {
    @Override
    public List<Object> getChildren() {
        return Arrays.asList(variableName, type);
    }

    @Override
    public String toString() {
        return "FunctionParameter(" +
                "\"" + variableName + "\"" +
                ", \"" + type + "\"" +
                ")";
    }
}
