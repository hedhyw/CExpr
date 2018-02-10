package ru.hedhyw.cexpr.model.command;

import ru.hedhyw.cexpr.complex.model.Complex;
import ru.hedhyw.cexpr.model.token.IToken;
import ru.hedhyw.cexpr.model.token.IdentifierToken;
import ru.hedhyw.cexpr.model.token.ImaginaryNumberToken;
import ru.hedhyw.cexpr.model.token.RealNumberToken;
import ru.hedhyw.cexpr.model.token.RegisterToken;


public interface ICommandValue {
    public Object getValue();
    public String toString();

    public CMD_TYPE getType();

    public enum CMD_TYPE {
        NUM,
        REG,
        VAR,
    }

    public static ICommandValue of(IToken token) {
      switch (token.getType()) {
        case NUM_RE:
          Double reVal = ((RealNumberToken) token).getValue();
          return new NumValue(new Complex(reVal, 0));
        case NUM_IM:
          Double imVal = ((ImaginaryNumberToken) token).getValue();
          return new NumValue(new Complex(0, imVal));
        case IDENTIFIER:
          String varName = ((IdentifierToken) token).getValue();
          return new VarValue(varName);
        case REG:
          Integer register = ((RegisterToken) token).getValue();
          return new RegValue(register);
        default:
            return null;
      }
    }

}
