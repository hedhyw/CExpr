package ru.hedhyw.cexpr.model.token;

import ru.hedhyw.cexpr.model.command.Command.CMD;

public class OperatorToken implements IToken {

  private final char value;

  public OperatorToken(char value) {
    this.value = value;
  }

  public Character getValue() {
    return this.value;
  }

  public CMD getCommand() {
    switch (this.value) {
      case '*':
        return CMD.MUL;
      case '/':
        return CMD.DIV;
      case '-':
        return CMD.SUB;
      case '+':
        return CMD.ADD;
      case '^':
        return CMD.POW;
      case '%':
        return CMD.MOD;
      default:
        return CMD.INVALID;
    }
  }

  public TOK_TYPE getType() {
    return TOK_TYPE.OPERATOR;
  }

}
