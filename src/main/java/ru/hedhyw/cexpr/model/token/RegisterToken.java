package ru.hedhyw.cexpr.model.token;

public class RegisterToken implements IToken {

  private final int value;

  public RegisterToken(int value) {
    this.value = value;
  }

  public Integer getValue() {
    return value;
  }

  public TOK_TYPE getType() {
    return TOK_TYPE.REG;
  }

}
