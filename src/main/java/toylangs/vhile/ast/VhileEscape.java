package toylangs.vhile.ast;

import com.google.common.base.Preconditions;

public record VhileEscape(int level) implements VhileStmt {
    public VhileEscape {
        Preconditions.checkArgument(level > 0, "level is not positive: %s", level);
    }

    @Override
    public String toString() {
        return "escape(" +
                "" + level +
                ")";
    }
}
