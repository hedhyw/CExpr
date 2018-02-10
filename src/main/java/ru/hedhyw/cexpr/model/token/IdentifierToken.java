package ru.hedhyw.cexpr.model.token;

public class IdentifierToken implements IToken {

  private final String value;

  public IdentifierToken(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public TOK_TYPE getType() {
    return TOK_TYPE.IDENTIFIER;
  }

}
