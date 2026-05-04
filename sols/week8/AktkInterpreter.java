package week8;

import week7.AktkAst;
import week7.ast.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class AktkInterpreter {
    public static void run(String program) {
        Statement root = AktkAst.createAst(program);
        new AktkInterpreter().evalStatement(root);
    }

    // Spetsiaalne muutujanimi, mida kasutame funktsiooni tagastusväärtuse hoidmiseks keskkonnas
    private static final String FUNCTION_RETURN_NAME = "__return__";

    // Avaldise väärtus võib olla nii täisarv (Integer) kui ka String
    // seega kasutame üldist Object tüüpi.
    private final Environment<Object> env = new Environment<>();

    // Lausete korral ei tagasta midagi.
    private void evalStatement(Statement statement) {
        switch (statement) {
            case Assignment assignment -> {
                String name = assignment.getVariableName();
                Object value = evalExpression(assignment.getExpression());
                env.assign(name, value);
            }
            case Block(List<Statement> statements) -> {
                env.enterBlock();
                for (Statement stmt : statements) {
                    evalStatement(stmt);
                }
                env.exitBlock();
            }
            case ExpressionStatement(Expression expression) -> evalExpression(expression); // Eraldiseisval avaldisel on ka väärtus, aga ignoreerime seda
            case FunctionDefinition functionDefinition -> env.declareAssign(functionDefinition.name(), functionDefinition);
            case IfStatement(Expression condition, Block thenBranch, Block elseBranch) -> {
                if (isAktkTrue(evalExpression(condition))) {
                    evalStatement(thenBranch);
                }
                else {
                    evalStatement(elseBranch);
                }
            }
            case ReturnStatement returnStatement -> {
                Object value = evalExpression(returnStatement.getExpression());
                env.assign(FUNCTION_RETURN_NAME, value);
            }
            case VariableDeclaration variableDeclaration -> {
                String name = variableDeclaration.variableName();
                Object value = null;
                if (variableDeclaration.getInitializer() != null) {
                    value = evalExpression(variableDeclaration.getInitializer());
                }
                env.declareAssign(name, value);
            }
            case WhileStatement(Expression condition, Block body) -> {
                while (isAktkTrue(evalExpression(condition))) {
                    evalStatement(body);
                }
            }
        }
    }

    // Avaldise väärtus võib olla nii täisarv (Integer) kui ka String
    // seega kasutame tagastustüübina üldist Object tüüpi.
    private Object evalExpression(Expression expression) {
        return switch (expression) {
            case FunctionCall functionCall -> {
                String name = functionCall.getFunctionName();

                List<Object> argValues = new ArrayList<>();
                for (Expression arg : functionCall.getArguments()) {
                    argValues.add(evalExpression(arg));
                }

                if (functionCall.isArithmeticOperation()) {
                    yield callArithmeticOperation(name, argValues);
                }
                else if (functionCall.isComparisonOperation()) {
                    // Teisendame tõevääruse AKTK jaoks täisarvuks
                    yield callComparisonOperation(name, argValues) ? 1 : 0;
                }
                else if (env.get(name) != null) {
                    yield callAktkFunction((FunctionDefinition) env.get(name), argValues);
                }
                else {
                    yield callBuiltinFunction(name, argValues);
                }
            }
            case IntegerLiteral(Integer value) -> value;
            case StringLiteral(String value) -> value;
            case Variable variable -> env.get(variable.getName());
        };
    }

    private Object callArithmeticOperation(String name, List<Object> argValues) {
        switch (argValues.size()) {
            case 1: // unaarne operaator
                Object value = argValues.getFirst();
                if (value instanceof Integer iValue) {
                    if (name.equals("-")) return -iValue;
                    throw new UnsupportedOperationException("unknown unary operator called on integer");
                }
                throw new UnsupportedOperationException("unary operator called on string");

            case 2: // binaarne operaator
                Object left = argValues.get(0);
                Object right = argValues.get(1);
                if (left instanceof Integer iLeft && right instanceof Integer iRight) {
                    return switch (name) {
                        case "+" -> iLeft + iRight;
                        case "-" -> iLeft - iRight;
                        case "*" -> iLeft * iRight;
                        case "/" -> iLeft / iRight;
                        case "%" -> iLeft % iRight;
                        default -> throw new UnsupportedOperationException("unknown binary operator called on integers");
                    };
                }
                else if (left instanceof String sLeft && right instanceof String sRight) {
                    if (name.equals("+")) return sLeft + sRight;
                    throw new UnsupportedOperationException("unknown binary operator called on strings");
                }
                else {
                    throw new RuntimeException("binary operator called on mismatching types");
                }

            default:
                throw new UnsupportedOperationException("arithmetic operator called with invalid number of arguments");
        }
    }

    private boolean callComparisonOperation(String name, List<Object> argValues) {
        if (argValues.size() != 2) {
            throw new UnsupportedOperationException("comparison operator called with invalid number of arguments");
        }

        Object left = argValues.get(0);
        Object right = argValues.get(1);

        int compare;
        if (left instanceof Integer l && right instanceof Integer r) {
            compare = Integer.compare(l, r);
        }
        else if (left instanceof String l && right instanceof String r) {
            compare = l.compareTo(r);
        }
        else {
            throw new RuntimeException("comparison operator called on mismatching types");
        }

        return switch (name) {
            case "==" -> compare == 0;
            case "!=" -> compare != 0;
            case "<" -> compare < 0;
            case "<=" -> compare <= 0;
            case ">" -> compare > 0;
            case ">=" -> compare >= 0;
            default -> throw new UnsupportedOperationException("unknown comparison operator called");
        };
    }

    private Object callAktkFunction(FunctionDefinition functionDefinition, List<Object> argValues) {
        env.enterBlock();

        if (argValues.size() != functionDefinition.params().size()) {
            throw new RuntimeException("function called with invalid number of arguments");
        }
        for (int i = 0; i < argValues.size(); i++) {
            env.declareAssign(functionDefinition.params().get(i).variableName(), argValues.get(i));
        }
        env.declare(FUNCTION_RETURN_NAME);

        evalStatement(functionDefinition.body());

        Object returnValue = env.get(FUNCTION_RETURN_NAME);
        env.exitBlock();
        return returnValue;
    }

    private Object callBuiltinFunction(String name, List<Object> argValues) {
        // Leia argumendite tüübid, et reflection õige meetodi üles leiaks
        Class<?>[] argClasses = new Class[argValues.size()];
        for (int i = 0; i < argValues.size(); i++) {
            argClasses[i] = argValues.get(i).getClass();
        }

        try {
            Method method = AktkInterpreterBuiltins.class.getDeclaredMethod(name, argClasses);
            return method.invoke(null, argValues.toArray());
        }
        catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isAktkTrue(Object object) {
        return object instanceof Integer i && i != 0;
    }
}
