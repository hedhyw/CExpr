package ru.hedhyw.cexpr.model.token;

public class FunctionToken implements IToken {

  private final String value;

  public FunctionToken(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public TOK_TYPE getType() {
    return TOK_TYPE.FUNCTION;
  }

}
