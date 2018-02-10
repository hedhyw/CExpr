package ru.hedhyw.cexpr.model.command;

public class RegValue implements ICommandValue {

  private final int value;

  public RegValue(Integer value) {
    this.value = value;
  }

  public Integer getValue() {
    return value;
  }

  public String toString() {
    return String.valueOf(value);
  }

  public CMD_TYPE getType() {
    return CMD_TYPE.REG;
  }

}
