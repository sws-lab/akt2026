package week4.baselangs.expr;

import week4.baselangs.expr.ast.*;

import java.util.HashSet;
import java.util.Set;

public class ExprVisitorEvaluator {

    // Nüüd proovime ExprEvaluator-i meetodeid kirjutada külastaja disainimustri (visitor design pattern) abil.
    // Üldiselt meie AST-idel visitor-e pole - Expr keel on erand.
    // Siiski, tutvume ka visitor-i kasutamisega juba nüüd, sest hiljem ANTLR-i juures on see möödapääsmatu.

    /**
     * Väärtustab avaldise.
     */
    public static int eval(ExprNode node) {
        // Hakka siin null-i asemele kirjutama "new ExprVisitor<Integer>()" ning lase IntelliJ-l see lõpuni kirjutada ja meetodid genereerida.
        // Seejärel asenda genereeritud implementatsioonid õigetega.
        ExprVisitor<Integer> visitor = null;

        // NB! Vaata, et kasutad rekursiivsete eval kutsete asemel visit kutseid.
        //     Vastasel juhul hiilid visitorist mööda ja ikka kasutad tavalist rekursiooni.

        // Olles defineerinud uue (anonüümse) visitor klassi, käivitame selle lõpuks antud node argumendi peal.
        return node.accept(visitor);
    }

    // Võrdle seda eval meetodit ExprEvaluator-i eval meetodiga.


    /**
     * Kogub kokku kõik tippudes esinevad arvud.
     */
    public static Set<Integer> getAllValues(ExprNode node) {
        ExprVisitor<Set<Integer>> visitor = null;
        return node.accept(visitor);
    }


    /**
     * Kogub kokku kõik tippudes esinevad arvud.
     * Väldib koodi kordusi kasutades BaseVisitor-i agregeerimist.
     */
    public static Set<Integer> getAllValuesAggregate(ExprNode node) {

        ExprVisitor<Set<Integer>> visitor = new ExprVisitor.BaseVisitor<>() {
            // Vaata BaseVisitor-i definitsiooni ja implementeeri siin kaks meetodit.
        };

        return node.accept(visitor);
    }

    // Pane tähele, et BaseVisitor-ile analoogilist lähenemist ExprEvaluator-is polnud.
    // Seega, kuigi visitor-id võivad tihti olla pigem kohmakad, võib neil mõnikord siiski olla eeliseid.


    /**
     * Kogub kokku kõik tippudes esinevad arvud.
     * Väldib koodi kordusi kasutades BaseVisitor-i ja imperatiivset stiili.
     */
    public static Set<Integer> getAllValuesImperative(ExprNode node) {
        Set<Integer> result = new HashSet<>();

        ExprVisitor<Void> visitor = new ExprVisitor.BaseVisitor<>() {
            @Override
            protected Void visit(ExprNum num) {
                result.add(num.value());
                return null; // Geneerikus kasutatav Void (mitte void!) tüüp nõuab tagastust.
            }
        };

        node.accept(visitor);
        return result;
    }

    // Võrdle seda getAllValuesImperative meetodit ExprEvaluator-i getAllValuesImperative meetodiga.
}
