package ru.hedhyw.cexpr.model.command;

import ru.hedhyw.cexpr.complex.model.Complex;
import ru.hedhyw.cexpr.ExprToken;

public interface ICommandValue {
    public Object getValue();
    public String toString();

    public CMD_TYPE getType();

    public enum CMD_TYPE {
        NUM,
        REG,
        VAR,
    }

    public static ICommandValue of(ExprToken token) {
        switch (token.type) {
            case NUM_RE:
                return new NumValue(new Complex((double) token.val, 0));
            case NUM_IM:
                return new NumValue(new Complex(0, (double) token.val));
            case IDENTIFIER:
                return new VarValue((String) token.val);
            case REG:
                return new RegValue((Integer) token.val);
            default:
                return null;
        }
    }

}
