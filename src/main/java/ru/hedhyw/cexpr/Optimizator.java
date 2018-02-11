package ru.hedhyw.cexpr;

import java.util.List;
import java.util.ListIterator;

import ru.hedhyw.cexpr.complex.model.Complex;
import ru.hedhyw.cexpr.functions.factory.IFunctionFactory;
import ru.hedhyw.cexpr.functions.model.IFunction;
import ru.hedhyw.cexpr.model.command.Command;
import ru.hedhyw.cexpr.model.command.ICommandValue;
import ru.hedhyw.cexpr.model.command.NumValue;
import ru.hedhyw.cexpr.model.command.RegValue;
import ru.hedhyw.cexpr.model.command.Command.CMD;
import ru.hedhyw.cexpr.model.command.ICommandValue.CMD_TYPE;

public class Optimizator {

  private List<Command> cmds;
  private IFunctionFactory functionFactory;

  public Optimizator(List<Command> cmds, IFunctionFactory functionFactory) {
    this.cmds = cmds;
    this.functionFactory = functionFactory;
  }

  public List<Command> optimize() {
    if (cmds.size() <= 1) {
      return cmds;
    }
    int optimized;
    ListIterator<Command> it;
    ICommandValue newVal = null;
    do {
      optimized = 0;
      it = cmds.listIterator();
      while (it.hasNext()) {
        Command cmd = it.next();
        boolean val0IsNum = cmd.getFirstValue().getType() == CMD_TYPE.NUM;
        boolean val1IsNum = (cmd.getSecondValue() != null &&
          cmd.getSecondValue().getType() == CMD_TYPE.NUM);
        boolean isFunctionWithNum = (cmd.getCommand() == CMD.CALL && val0IsNum);
        if (!(isFunctionWithNum || (val0IsNum && val1IsNum))) continue;

        if (cmd.getCommand() == CMD.CALL) {
          IFunction func = functionFactory.getFunction(cmd.getFunctionName());
          if (!func.isOptimizable()) continue;
        }
        Complex res = cmd.evalCommand(null, null, functionFactory);

        int regResult = cmd.getResultRegister();
        ++optimized;
        it.remove();
        newVal = new NumValue(res);
        while (it.hasNext()) {
          cmd = it.next();
          if (cmd.getFirstValue().getType() == CMD_TYPE.REG &&
            ((RegValue) cmd.getFirstValue()).getValue() == regResult) {
              it.set(new Command(cmd.getCommand(),
                newVal, cmd.getSecondValue(),
                cmd.getFunctionName(), cmd.getResultRegister()));
          }
          if (cmd.getSecondValue() != null &&
            cmd.getSecondValue().getType() == CMD_TYPE.REG &&
            ((RegValue) cmd.getSecondValue()).getValue() == regResult) {
              it.set(new Command(cmd.getCommand(), cmd.getFirstValue(),
                newVal, cmd.getFunctionName(), cmd.getResultRegister()));
          }
        }
      }
    } while (optimized != 0);
    if (cmds.size() == 0) {
      Command cmd = new Command(Command.CMD.CALL, newVal, null, "set", 1);
      cmds.add(cmd);
    }
    return cmds;
  }

}
