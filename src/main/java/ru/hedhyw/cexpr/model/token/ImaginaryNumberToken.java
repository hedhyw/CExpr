package ru.hedhyw.cexpr.model.token;

public class ImaginaryNumberToken implements IToken {

  private final double value;

  public ImaginaryNumberToken(double value) {
    this.value = value;
  }

  public Double getValue() {
    return value;
  }

  public TOK_TYPE getType() {
    return TOK_TYPE.NUM_IM;
  }

}
