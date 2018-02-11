package ru.hedhyw.cexpr.model.command;

import java.util.HashMap;

import javax.annotation.Nullable;

import ru.hedhyw.cexpr.complex.ComplexUtils;
import ru.hedhyw.cexpr.complex.model.Complex;
import ru.hedhyw.cexpr.functions.factory.IFunctionFactory;
import ru.hedhyw.cexpr.model.errors.ExecuteError;

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

  private Complex getValue(
    ICommandValue val,
    HashMap<Integer, Complex> registers,
    HashMap<String, Complex> variables) {
      if (val == null) return null;
      switch (val.getType()) {
        case REG:
          return registers.get(((RegValue) val).getValue());
        case VAR:
          return variables.get(((VarValue) val).getValue());
        case NUM:
          return ((NumValue) val).getValue();
        default:
          return null;
      }
  }

  public Complex evalCommand(
    HashMap<Integer, Complex> registers,
    HashMap<String, Complex> variables,
    IFunctionFactory functionFactory) {
      Complex firstValue = getValue(val0, registers, variables);
      if (firstValue == null) {
        throw new ExecuteError(
          String.format("Unknown value: %s",val0.toString()));
      }
      Complex secondValue = getValue(val1, registers, variables);
      switch (command) {
        case ADD:
          return ComplexUtils.add(firstValue, secondValue);
        case MOD:
          if (firstValue.getImaginary() != 0 ||
            secondValue.getImaginary() != 0) {
              throw new ExecuteError("%, Numbers have imaginary units");
          }
          return new Complex(firstValue.getReal() % secondValue.getReal(), 0);
        case SUB:
          return ComplexUtils.sub(firstValue, secondValue);
        case MUL:
          return ComplexUtils.mul(firstValue, secondValue);
        case DIV:
          if (secondValue.getImaginary() == 0 && secondValue.getReal() == 0) {
            throw new ExecuteError("/, Divided by zero");
          }
          return ComplexUtils.div(firstValue, secondValue);
        case POW:
          if (secondValue.getImaginary() != 0) {
            throw new ExecuteError("^, Exponent has imaginary unit");
          }
          return ComplexUtils.pow(firstValue, secondValue.getReal());
        case CALL:
          return functionFactory.getFunction(functionName).eval(firstValue);
        default:
          throw new ExecuteError("Invalid operator");
      }
  }

  public enum CMD {
    ADD,
    SUB,
    MUL,
    DIV,
    POW,
    MOD,
    CALL,
    INVALID
  }

}
