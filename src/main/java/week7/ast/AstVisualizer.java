package week7.ast;

import guru.nidi.graphviz.attribute.*;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.*;
import org.apache.commons.text.StringEscapeUtils;
import week7.AktkAst;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static guru.nidi.graphviz.attribute.Records.*;
import static guru.nidi.graphviz.model.Compass.*;
import static guru.nidi.graphviz.model.Factory.*;

public class AstVisualizer {

    static void main() throws IOException {
        String program = Files.readString(Paths.get("inputs", "sample.aktk"));
        AstNode ast = AktkAst.createAst(program);
        renderPngFile(ast, Paths.get("graphs", "aktk.png"));
    }

    private int nextNodeId = 0;

    private Node makeNode(String rec) {
        return node(Integer.toString(nextNodeId++)).with(Records.of(rec));
    }

    private Link withLabel(Node node, String label) {
        return to(node).with(Label.of(label));
    }

    private Node visit(Object object) {
        // also handles non-AstNode elements and nulls
        return switch (object) {
            case AstNode node -> visit(node);
            case String s -> makeNode(rec("\"" + StringEscapeUtils.escapeJava(s) + "\""));
            case null, default -> makeNode(rec(Objects.toString(object)));
        };
    }

    private Link visitLabel(Object object, String label) {
        return withLabel(visit(object), label);
    }

    private List<LinkTarget> visitListLabel(List<?> objects, String label) {
        List<LinkTarget> targets = new ArrayList<>();
        for (int i = 0; i < objects.size(); i++) {
            Object object = objects.get(i);
            targets.add(visitLabel(object, "%s[%d]".formatted(label, i)));
        }
        return targets;
    }

    private Node makeNodeLink(AstNode node, List<LinkTarget> linkTargets) {
        List<String> linkRecs = new ArrayList<>();
        List<LinkTarget> linkRecLinkTargets = new ArrayList<>();
        for (LinkTarget linkTarget : linkTargets) {
            Link link = linkTarget.linkTo();
            String linkLabel = Objects.requireNonNull(link.attrs().get("label")).toString();
            @SuppressWarnings("UnnecessaryLocalVariable") String linkRecTag = linkLabel;
            linkRecs.add(rec(linkRecTag, linkLabel));
            linkRecLinkTargets.add(between(port(linkRecTag, SOUTH), link.to()));
        }
        return makeNode(turn(rec(node.getClass().getSimpleName()), turn(linkRecs.toArray(new String[0])))).link(linkRecLinkTargets);
    }

    private Node makeNodeLink(AstNode node, LinkTarget... linkTargets) {
        return makeNodeLink(node, Arrays.asList(linkTargets));
    }

    private Node visit(AstNode node) {
        return switch (node) {
            case Assignment assignment -> makeNodeLink(assignment,
                    visitLabel(assignment.getVariableName(), "variableName"),
                    visitLabel(assignment.getExpression(), "expression")
            );
            case Block block -> makeNodeLink(block,
                    visitListLabel(block.statements(), "statements")
            );
            case ExpressionStatement expressionStatement -> makeNodeLink(expressionStatement,
                    visitLabel(expressionStatement.expression(), "expression")
            );
            case FunctionCall functionCall -> {
                List<LinkTarget> targets = new ArrayList<>();
                targets.add(visitLabel(functionCall.getFunctionName(), "functionName"));
                targets.addAll(visitListLabel(functionCall.getArguments(), "arguments"));
                yield makeNodeLink(functionCall, targets);
            }
            case FunctionDefinition functionDefinition -> {
                List<LinkTarget> targets = new ArrayList<>();
                targets.add(visitLabel(functionDefinition.name(), "name"));
                List<FunctionParameter> parameters = functionDefinition.params();
                for (int i = 0; i < parameters.size(); i++) {
                    FunctionParameter parameter = parameters.get(i);
                    Node parameterNode = makeNodeLink(parameter,
                            visitLabel(parameter.variableName(), "variableName"),
                            visitLabel(parameter.type(), "type")
                    );
                    targets.add(withLabel(parameterNode, "parameters[%d]".formatted(i)));
                }
                targets.add(visitLabel(functionDefinition.returnType(), "returnType"));
                targets.add(visitLabel(functionDefinition.body(), "body"));
                yield makeNodeLink(functionDefinition, targets);
            }
            case FunctionParameter _ ->
                    throw new UnsupportedOperationException("FunctionParameter cannot be handled on its own; handled in FunctionDefinition.");
            case IntegerLiteral integerLiteral -> makeNodeLink(integerLiteral,
                    visitLabel(integerLiteral.value(), "value")
            );
            case IfStatement ifStatement -> makeNodeLink(ifStatement,
                    visitLabel(ifStatement.condition(), "condition"),
                    visitLabel(ifStatement.thenBranch(), "thenBranch"),
                    visitLabel(ifStatement.elseBranch(), "elseBranch")
            );
            case ReturnStatement returnStatement -> makeNodeLink(returnStatement,
                    visitLabel(returnStatement.getExpression(), "expression")
            );
            case StringLiteral stringLiteral -> makeNodeLink(stringLiteral,
                    visitLabel(stringLiteral.value(), "value")
            );
            case Variable variable -> makeNodeLink(variable,
                    visitLabel(variable.getName(), "name")
            );
            case VariableDeclaration variableDeclaration -> makeNodeLink(variableDeclaration,
                    visitLabel(variableDeclaration.variableName(), "variableName"),
                    visitLabel(variableDeclaration.type(), "type"),
                    visitLabel(variableDeclaration.getInitializer(), "initializer")
            );
            case WhileStatement whileStatement -> makeNodeLink(whileStatement,
                    visitLabel(whileStatement.condition(), "condition"),
                    visitLabel(whileStatement.body(), "body")
            );
        };
    }

    public static void renderPngFile(AstNode node, Path path) throws IOException {
        Node rootNode = new AstVisualizer().visit(node);
        Graph graph = graph("aktk").directed().with(rootNode).graphAttr().with("ordering", "out");
        //System.out.println(Graphviz.fromGraph(graph).render(Format.DOT));
        Graphviz.fromGraph(graph).scale(2).render(Format.PNG).toFile(path.toFile().getCanonicalFile());
    }
}
