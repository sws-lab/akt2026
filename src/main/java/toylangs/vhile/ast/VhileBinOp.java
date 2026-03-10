package toylangs.vhile.ast;

import java.util.List;

public record VhileBinOp(Op op, VhileExpr left, VhileExpr right) implements VhileExpr {
    public enum Op {
        Add("add"),
        Mul("mul"),
        Eq("eq"),
        Neq("neq");

        private final String nodeInfo;

        Op(String nodeInfo) {
            this.nodeInfo = nodeInfo;
        }
    }

    @Override
    public List<Object> getChildren() {
        return List.of(left, right);
    }

    @Override
    public String getNodeLabel() {
        return op.nodeInfo;
    }

    @Override
    public String toString() {
        return op.nodeInfo + "(" +
                "" + left +
                ", " + right +
                ")";
    }
}
