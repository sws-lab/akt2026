package week7.ast;

import java.util.Collections;
import java.util.List;

/**
 * Avaldislause.
 * <p>
 * Kasutatakse eeldatavasti väärtustamisel kõrvalefektiga avaldiste jaoks, mille väärtus pole oluline.
 * Näiteks 'print("tere")'.
 */
public record ExpressionStatement(Expression expression) implements Statement {

    @Override
    public List<Object> getChildren() {
        return Collections.singletonList(expression);
    }

    @Override
    public String toString() {
        return "ExpressionStatement(" +
                expression +
                ")";
    }
}
