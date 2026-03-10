package week4.baselangs.expr;

import org.junit.Test;
import week4.baselangs.expr.ast.ExprNode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static week4.baselangs.expr.ExprEvaluator.*;
import static week4.baselangs.expr.ast.ExprNode.*;

public class ExprEvaluatorTest {

    public static final ExprNode expr1 = div(add(num(5), add(num(3), neg(num(2)))), num(2));
    public static final ExprNode expr2 = add(add(num(5), div(num(3), neg(num(2)))), num(2));
    public static final ExprNode expr3 = div(div(num(8), num(2)), num(2));
    public static final ExprNode expr4 = div(num(8), div(num(2), num(2)));
    public static final ExprNode expr5 = add(neg(expr4), add(expr2, expr3));

    private static Set<Integer> set(Integer... elements) {
        return new HashSet<>(Arrays.asList(elements));
    }

    @Test
    public void testEvalNodeMethod() {
        assertEquals(10, num(10).eval());
        assertEquals(4, add(num(2), num(2)).eval());
        assertEquals(3, expr1.eval());
        assertEquals(6, expr2.eval());
        assertEquals(2, expr3.eval());
        assertEquals(8, expr4.eval());
        assertEquals(0, expr5.eval());
    }

    @Test
    public void testEvalInstanceof() {
        assertEquals(10, evalInstanceof(num(10)));
        assertEquals(4, evalInstanceof(add(num(2), num(2))));
        assertEquals(3, evalInstanceof(expr1));
        assertEquals(6, evalInstanceof(expr2));
        assertEquals(2, evalInstanceof(expr3));
        assertEquals(8, evalInstanceof(expr4));
        assertEquals(0, evalInstanceof(expr5));
    }

    @Test
    public void testEval() {
        assertEquals(10, eval(num(10)));
        assertEquals(4, eval(add(num(2), num(2))));
        assertEquals(3, eval(expr1));
        assertEquals(6, eval(expr2));
        assertEquals(2, eval(expr3));
        assertEquals(8, eval(expr4));
        assertEquals(0, eval(expr5));
    }

    @Test
    public void testGetAllValues() {
        assertEquals(set(10), getAllValues(num(10)));
        assertEquals(set(2,8), getAllValues(add(num(2), num(8))));
        assertEquals(set(2, 3, 5, 8), getAllValues(expr5));
    }

    @Test
    public void testGetAllValuesImperative() {
        assertEquals(set(10), getAllValuesImperative(num(10)));
        assertEquals(set(2,8), getAllValuesImperative(add(num(2), num(8))));
        assertEquals(set(2, 3, 5, 8), getAllValuesImperative(expr5));
    }
}
