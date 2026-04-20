package week7.ast;

import java.util.ArrayList;
import java.util.List;

/**
 * Funktsiooni definitsioon.
 */
public record FunctionDefinition(String name, List<FunctionParameter> params, String returnType, Block body) implements Statement {

    /**
     * returnType - tagastustüüp. Puudumise korral {@code null}.
     */

    public FunctionDefinition(String name, List<FunctionParameter> params, Block body) {
        this(name, params, null, body);
    }

    @Override
    public List<Object> getChildren() {
        ArrayList<Object> children = new ArrayList<>();
        children.add(name);
        children.addAll(params);
        children.add(returnType);
        children.add(body);
        return children;
    }

    @Override
    public String toString() {
        return "FunctionDefinition(" +
                "\"" + name + "\"" +
                (params.isEmpty() ? "" : ", ") + params.toString().replaceAll("[\\[\\]{}]", "") +
                (returnType == null ? ", " + null : ", \"" + returnType + "\"") +
                ", " + body +
                ")";
    }
}
