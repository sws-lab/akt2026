package toylangs.hulk;

import toylangs.hulk.ast.*;

import java.util.HashSet;
import java.util.Set;

public class HulkMaster {
    private final Set<Character> definedVars = new HashSet<>();

    public static boolean isValidHulkNode(HulkNode node) {
        HulkMaster hulkMaster = new HulkMaster();
        return hulkMaster.validHulkNode(node);
    }

    private boolean validHulkNode(HulkNode node) {
        throw new UnsupportedOperationException();
    }

    public static HulkNode processEmptyLiterals(HulkNode node) {
        throw new UnsupportedOperationException();
    }
}
