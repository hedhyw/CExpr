package ru.hedhyw.cexpr.model.command;

import javax.annotation.Nullable;

public class Command {

    private final CMD command;
    private final ICommandValue val0, val1;
    private final int resultRegister;
    private final String functionName;

    public Command(CMD command,
      ICommandValue val0,
      @Nullable ICommandValue val1,
      @Nullable String functionName,
      int resultRegister) {
          this.command = command;
          this.val0 = val0;
          this.val1 = val1;
          this.functionName = functionName;
          this.resultRegister = resultRegister;
    }

    public CMD getCommand() {
      return command;
    }

    public ICommandValue getFirstValue() {
      return val0;
    }

    @Nullable
    public ICommandValue getSecondValue() {
      return val1;
    }

    @Nullable
    public String getFunctionName() {
      return functionName;
    }

    public int getResultRegister() {
      return resultRegister;
    }

    @Override
    public String toString() {
      if (command == CMD.CALL) {
        return String.format("r%d := %s %s", resultRegister,
          functionName.toUpperCase(), val0.toString());
        }
      return String.format("r%d := %s %s, %s", resultRegister,
        command.name(), val0.toString(), val1.toString());
    }

    public enum CMD {
      ADD,
      SUB,
      MUL,
      DIV,
      POW,
      MOD,
      CALL
    }
}
