package ru.hedhyw.cexpr.model.token;

public interface IToken {

    public Object getValue();
    public TOK_TYPE getType();

    public enum TOK_TYPE {
        NUM_RE,
        NUM_IM,
        IDENTIFIER,
        OPERATOR,
        REG,
        FUNCTION,
        END
    }

}
