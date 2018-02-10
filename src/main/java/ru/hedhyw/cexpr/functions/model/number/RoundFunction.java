package ru.hedhyw.cexpr.functions.model.number;

import ru.hedhyw.cexpr.complex.ComplexUtils;
import ru.hedhyw.cexpr.complex.model.Complex;
import ru.hedhyw.cexpr.functions.model.IFunction;

public class RoundFunction implements IFunction {

  private static final String NAME = "ROUND";
  private static final boolean optimizable = true;

  public Complex eval(Complex arg) {
    return ComplexUtils.round(arg);
  }

  public String getName() {
    return NAME;
  }

  public boolean isOptimizable() {
    return optimizable;
  }

}