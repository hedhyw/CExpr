package ru.hedhyw.cexpr.model.command;

import ru.hedhyw.cexpr.complex.model.Complex;

public class NumValue implements ICommandValue {

  private final Complex value;

  public NumValue(Complex value) {
    this.value = value;
  }

  public Complex getValue() {
    return value;
  }

  public String toString() {
    return value.toString();
  }

  public CMD_TYPE getType() {
    return CMD_TYPE.NUM;
  }

}
