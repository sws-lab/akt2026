package toylangs.dialoog;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import toylangs.dialoog.ast.*;

import java.util.*;

import static org.junit.Assert.*;
import static toylangs.dialoog.ast.DialoogNode.*;
import static toylangs.dialoog.DialoogMaster.Type.TBool;
import static toylangs.dialoog.DialoogMaster.Type.TInt;
import static toylangs.dialoog.DialoogMaster.symbex;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DialoogMasterTest {

    @Test
    public void test01_typecheck_basic() {
        checkTypecheck(TInt, prog(decls(), il(2)));
        checkTypecheck(TBool, prog(decls(), bl(false)));
        checkTypecheck(TInt, prog(decls(iv("x")), var("x")));
        checkTypecheck(TBool, prog(decls(bv("x")), var("x")));

        checkTypecheck(TInt, prog(decls(bv("x")), il(1))); // deklareeritud muutuja tüüp avaldisest erinev
    }

    @Test
    public void test02_typecheck_ops() {
        checkTypecheck(TInt, prog(decls(), neg(il(1))));
        checkTypecheck(null, prog(decls(), not(il(1))));

        checkTypecheck(null, prog(decls(), neg(bl(true))));
        checkTypecheck(TBool, prog(decls(), not(bl(true))));


        checkTypecheck(TInt, prog(decls(), add(il(1), il(2))));
        checkTypecheck(TInt, prog(decls(), sub(il(1), il(2))));
        checkTypecheck(null, prog(decls(), and(il(1), il(2))));
        checkTypecheck(null, prog(decls(), or(il(1), il(2))));

        checkTypecheck(null, prog(decls(), add(bl(false), bl(true))));
        checkTypecheck(null, prog(decls(), sub(bl(false), bl(true))));
        checkTypecheck(TBool, prog(decls(), and(bl(false), bl(true))));
        checkTypecheck(TBool, prog(decls(), or(bl(false), bl(true))));

        checkTypecheck(null, prog(decls(), add(il(1), bl(false))));
        checkTypecheck(null, prog(decls(), eq(il(1), bl(false))));
        checkTypecheck(TBool, prog(decls(), eq(bl(false), bl(true))));
        checkTypecheck(TBool, prog(decls(), eq(il(1), il(5))));
    }

    @Test
    public void test03_typecheck_ifte() {
        checkTypecheck(TInt, prog(decls(), ifte(bl(true), il(1), il(2))));
        checkTypecheck(null, prog(decls(), ifte(il(5), il(1), il(2))));

        checkTypecheck(TBool, prog(decls(), ifte(bl(true), bl(false), bl(true))));
        checkTypecheck(null, prog(decls(), ifte(bl(true), il(1), bl(true))));
    }


    private void checkTypecheck(DialoogMaster.Type expectedType, DialoogProg prog) {
        try {
            DialoogMaster.Type actualType = DialoogMaster.typecheck(prog);
            if (expectedType != null)
                assertEquals(expectedType, actualType);
            else
                fail("Programm on vigane, aga typecheck ei visanud erindit");
        } catch (Exception e) {
            if (expectedType != null) {
                e.printStackTrace();
                fail("Programm ei ole vigane, aga typecheck viskas erindi: " + e);
            }
        }
    }


    @Test
    public void test04_symbex_basic() {
        checkSymbex(prog(decls(), add(il(1), il(2))));
        checkSymbex(prog(decls(iv("x")), add(var("x"), il(2))));
        checkSymbex(prog(decls(iv("x")), add(var("error"), il(2))));
    }

    @Test
    public void test05_symbex_ifte() {
        checkSymbex(prog(decls(iv("x")), ifte(eq(var("x"), il(10)), var("error"), var("x"))));
        checkSymbex(prog(decls(iv("x")), ifte(eq(var("x"), il(10)), var("x"), var("error"))));
    }

    @Test
    public void test06_symbex_multiple_ifte() {
        checkSymbex(prog(decls(iv("x")), ifte(eq(var("x"), il(10)), ifte(eq(var("x"), il(11)), var("error"), il(4)), il(5))));
        checkSymbex(prog(decls(iv("x")), ifte(eq(var("x"), il(10)), ifte(eq(var("x"), il(11)), il(4), var("error")), il(5))));

        checkSymbex(prog(decls(iv("x")), add(ifte(eq(var("x"), il(10)), var("error"), il(5)), ifte(eq(var("x"), il(10)), il(5), var("error")))));
    }

    @Test
    public void test07_symbex_multiple_var() {
        checkSymbex(prog(decls(bv("a"), iv("x"), iv("y")), ifte(or(var("a"), not(eq(var("x"), var("y")))), var("error"), il(5))));
    }


    // NB! Selleks on korrektne eval vaja, mis on alusosa ülesanne...
    private void checkSymbex(DialoogProg prog) {
        List<Map<String, Object>> envs = generateEnvs(prog);
        for (Map<String, Object> env : envs) {
            Boolean expect = doesItFail(() -> DialoogEvaluator.eval(prog, env));
            DialoogProg cond = (DialoogProg) symbex(prog);
            assertEquals("Checking if the following program should fail with env " + env + ".\n" +
                            "Program: " + prog + "\n" +
                            "Your condition: " + cond + ")",
                    expect, DialoogEvaluator.eval(cond, env));
        }
    }

    private static List<Map<String, Object>> generateEnvs(DialoogNode prog) {
        List<Object> ivals = new ArrayList<>(Arrays.asList(42, 77, 300));
        List<Object> bvals = Arrays.asList(true, false);
        collectIvalsFromProgram(prog, ivals);

        List<DialoogDecl> decls = ((DialoogProg) prog).decls();

        List<Map<String, Object>> maps = new ArrayList<>();
        maps.add(new HashMap<>());
        for (DialoogDecl decl : decls) {
            List<Object> vals = decl.intType() ? ivals : bvals;
            List<Map<String, Object>> newmaps = new ArrayList<>();
            for (Map<String, Object> map : maps) {
                for (Object val : vals) {
                    Map<String, Object> newmap = new HashMap<>(map);
                    newmap.put(decl.name(), val);
                    newmaps.add(newmap);
                }
            }
            maps = newmaps;
        }
        return maps;
    }

    /**
     * Abimeetod meetodile generateEnvs, mis kogub programmist kokku kõik arvväärtused.
     * Asendab DialoogAstVisitor loomist.
     * @param prog programm
     * @param ivals programmi väärtuste järjend
     */
    private static void collectIvalsFromProgram(DialoogNode prog, List<Object> ivals) {
        switch (prog) {
            case DialoogLitInt(int value) -> ivals.add(value);
            case DialoogProg(List<DialoogDecl> decls, DialoogNode expr) -> {
                for (DialoogDecl decl : decls) {
                    collectIvalsFromProgram(decl, ivals);
                }
                collectIvalsFromProgram(expr, ivals);
            }
            case DialoogUnary(DialoogUnary.UnOp _, DialoogNode expr) -> collectIvalsFromProgram(expr, ivals);
            case DialoogBinary(DialoogBinary.BinOp _, DialoogNode leftExpr, DialoogNode rightExpr) -> {
                collectIvalsFromProgram(leftExpr, ivals);
                collectIvalsFromProgram(rightExpr, ivals);
            }
            case DialoogTernary(DialoogNode guardExpr, DialoogNode trueExpr, DialoogNode falseExpr) -> {
                collectIvalsFromProgram(guardExpr, ivals);
                collectIvalsFromProgram(trueExpr, ivals);
                collectIvalsFromProgram(falseExpr, ivals);
            }
            // teistes tippudes pole vaja midagi teha ega väärtuseid lisada
            case DialoogDecl _, DialoogLitBool _, DialoogVar _ -> {}
        }
    }

    private Boolean doesItFail(Runnable f) {
        try {
            f.run();
            return false;
        } catch (Exception e) {
            return true;
        }
    }
}
