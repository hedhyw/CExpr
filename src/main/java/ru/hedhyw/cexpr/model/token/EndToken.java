package ru.hedhyw.cexpr.model.token;

public class EndToken implements IToken {

  public EndToken() {
  }

  public Character getValue() {
    return 0;
  }

  public TOK_TYPE getType() {
    return TOK_TYPE.END;
  }

}
