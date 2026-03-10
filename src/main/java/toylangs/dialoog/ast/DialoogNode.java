package toylangs.dialoog.ast;

import toylangs.AbstractNode;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static toylangs.dialoog.ast.DialoogBinary.BinOp.*;
import static toylangs.dialoog.ast.DialoogUnary.UnOp.DialoogNeg;
import static toylangs.dialoog.ast.DialoogUnary.UnOp.DialoogNot;

public sealed interface DialoogNode extends AbstractNode permits DialoogBinary, DialoogDecl, DialoogLitBool, DialoogLitInt, DialoogProg, DialoogTernary, DialoogUnary, DialoogVar {

    // Programm ja muutujate deklaratsioonid
    static DialoogProg prog(List<DialoogDecl> decls, DialoogNode expr) { return new DialoogProg(decls, expr); }
    static List<DialoogDecl> decls(DialoogDecl... ds) { return Arrays.asList(ds); }
    static DialoogDecl iv(String name) { return new DialoogDecl(name, true); }
    static DialoogDecl bv(String name) { return new DialoogDecl(name, false); }

    // Literaalid ja muutuja kasutus
    static DialoogLitInt il(int value) { return new DialoogLitInt(value); }
    static DialoogLitBool bl(boolean value) { return new DialoogLitBool(value); }
    static DialoogVar var(String name) { return new DialoogVar(name); }

    // Tingimus
    static DialoogTernary ifte(DialoogNode guard, DialoogNode trueExpr, DialoogNode falseExpr) {
        return new DialoogTernary(guard, trueExpr, falseExpr);
    }

    // Operatsioonid sümboli järgi
    static DialoogBinary binop(String op, DialoogNode left, DialoogNode right) {
        return new DialoogBinary(op, left, right);
    }
    static DialoogUnary unop(String op, DialoogNode expr) {
        return new DialoogUnary(op, expr);
    }

    // Võrdlus
    static DialoogBinary eq(DialoogNode left, DialoogNode right) { return new DialoogBinary(DialoogEq, left, right); }

    // Loogika
    static DialoogUnary not(DialoogNode expr) { return new DialoogUnary(DialoogNot, expr); }
    static DialoogBinary and(DialoogNode left, DialoogNode right) { return new DialoogBinary(DialoogAnd, left, right); }
    static DialoogBinary or(DialoogNode left, DialoogNode right) { return new DialoogBinary(DialoogOr, left, right); }

    // Aritmeetika
    static DialoogUnary neg(DialoogNode expr) { return new DialoogUnary(DialoogNeg, expr); }
    static DialoogBinary add(DialoogNode left, DialoogNode right) { return new DialoogBinary(DialoogAdd, left, right); }
    static DialoogBinary sub(DialoogNode left, DialoogNode right) { return new DialoogBinary(DialoogSub, left, right); }
    static DialoogBinary mul(DialoogNode left, DialoogNode right) { return new DialoogBinary(DialoogMul, left, right); }
    static DialoogBinary div(DialoogNode left, DialoogNode right) { return new DialoogBinary(DialoogDiv, left, right); }


    static void main() throws IOException {
        DialoogNode test = prog(
                decls(iv("x"), bv("y")),
                add(var("x"), var("y"))
        );
        test.renderPngFile(Paths.get("graphs", "dialoog.png"));
        System.out.println(test);
    }
}
