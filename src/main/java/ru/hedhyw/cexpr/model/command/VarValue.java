package ru.hedhyw.cexpr.model.command;

public class VarValue implements ICommandValue {

  private final String value;

  public VarValue(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public String toString() {
    return this.value;
  }

  public CMD_TYPE getType() {
    return CMD_TYPE.VAR;
  }

}
