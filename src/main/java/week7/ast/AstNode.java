package week7.ast;

import java.util.List;

/**
 * AKTK abstraktse süntaksipuu tippude ülemklass.
 */
public sealed interface AstNode permits Expression, FunctionParameter, Statement {

    /**
     * Tagastab kõik tipu alamad.
     * Lisaks teistele AstNode'idele võib sisaldada ka muud tüüpi väärtusi ja {@code null}-e.
     */
    List<Object> getChildren();
}
