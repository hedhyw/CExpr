package ru.hedhyw.cexpr;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import ru.hedhyw.cexpr.model.errors.ExecuteError;
import ru.hedhyw.cexpr.complex.model.Complex;
import ru.hedhyw.cexpr.functions.factory.IFunctionFactory;
import ru.hedhyw.cexpr.model.command.Command;

public class Compiled extends HashMap<String, Complex> {

  static final long serialVersionUID = 19784;

  private List<Command> cmds;
  private HashMap<Integer, Complex> regs;
  private IFunctionFactory functionFactory;

  public Compiled(List<Command> cmds, IFunctionFactory functionFactory) {
    super();
    regs = new HashMap<>();
    this.cmds = cmds;
    this.functionFactory = functionFactory;
  }

  public Complex execute() throws ExecuteError {
    Iterator<Command> it = cmds.iterator();
    Complex reg = null;
    Command cmd;
    while (it.hasNext()) {
      cmd = it.next();
      reg = cmd.evalCommand(regs, this, functionFactory);
      regs.put(cmd.getResultRegister(), reg);
    }
    return reg;
  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    Iterator<Command> it = cmds.iterator();
    while (it.hasNext()) {
      str.append(it.next().toString());
      str.append('\n');
    }
    return str.toString();
  }

}
