package week4.baselangs.expr;

import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.Node;
import week4.baselangs.expr.ast.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static guru.nidi.graphviz.model.Factory.graph;
import static guru.nidi.graphviz.model.Factory.node;
import static week4.baselangs.expr.ast.ExprNode.*;

public class ExprVisualizer {

    static void main() throws IOException {
        ExprNode expr = div(add(num(5), add(num(3), neg(num(2)))), num(2));
        renderPngFile(expr, Paths.get("graphs", "expr.png"));
    }


    private int nextNodeId = 0;

    private Node makeNode(String label) {
        return node(Integer.toString(nextNodeId++)).with(Label.of(label));
    }

    private Node visit(ExprNode node) {
        return switch (node) {
            case ExprNum(int value) -> makeNode(Integer.toString(value));
            case ExprNeg(ExprNode expr) -> makeNode("-").link(visit(expr));
            case ExprAdd(ExprNode left, ExprNode right) -> makeNode("+").link(visit(left), visit(right));
            case ExprDiv(ExprNode numerator, ExprNode denominator) -> makeNode("/").link(visit(numerator), visit(denominator));
        };
    }

    public static void renderPngFile(ExprNode node, Path path) throws IOException {
        Node rootNode = new ExprVisualizer().visit(node);
        Graph graph = graph("expr").directed().with(rootNode).graphAttr().with("ordering", "out");
        //System.out.println(Graphviz.fromGraph(graph).render(Format.DOT));
        Graphviz.fromGraph(graph).scale(3).render(Format.PNG).toFile(path.toFile().getCanonicalFile());
    }
}
