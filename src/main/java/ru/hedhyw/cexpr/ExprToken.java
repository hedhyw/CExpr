package ru.hedhyw.cexpr;

import javax.annotation.Nullable;

public class ExprToken {

    public final TYPE type;
    public final Object val;
    private static final int OPERATORS_PRIORITY[]
            = {4, 4, 3, 2, 2, 1, 1, 0, 0};

    public ExprToken(TYPE type, @Nullable Object val) {
        this.type = type;
        this.val = val;
    }

    enum TYPE {
        NUM_RE,
        NUM_IM,
        IDENTIFIER,
        OPERATOR,
        REG,
        FUNCTION,
        END
    }
}
