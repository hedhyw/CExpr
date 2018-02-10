package ru.hedhyw.cexpr.functions.model.special;

import ru.hedhyw.cexpr.complex.model.Complex;
import ru.hedhyw.cexpr.functions.model.IFunction;

public class SetFunction implements IFunction {

  private static final String NAME = "SET";
  private static final boolean optimizable = false;

  public Complex eval(Complex arg) {
    return arg;
  }

  public String getName() {
    return NAME;
  }

  public boolean isOptimizable() {
    return optimizable;
  }

}