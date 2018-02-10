package ru.hedhyw.cexpr.functions.model.number;

import ru.hedhyw.cexpr.complex.model.Complex;
import ru.hedhyw.cexpr.functions.model.IFunction;

public class InvFunction implements IFunction {

  private static final String NAME = "INV";
  private static final boolean optimizable = true;

  public Complex eval(Complex arg) {
    return new Complex(arg.getImaginary(), arg.getReal());
  }

  public String getName() {
    return NAME;
  }

  public boolean isOptimizable() {
    return optimizable;
  }

}