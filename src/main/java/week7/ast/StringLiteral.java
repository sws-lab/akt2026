package week7.ast;

import org.apache.commons.text.StringEscapeUtils;

import java.util.List;

/**
 * Sõneliteraal.
 */
public record StringLiteral(String value) implements Expression {

    @Override
    public List<Object> getChildren() {
        return List.of(value);
    }

    @Override
    public String toString() {
        return "StringLiteral(" +
                (value == null ? null : "\"" + StringEscapeUtils.escapeJava(value) + "\"") +
                ")";
    }
}
