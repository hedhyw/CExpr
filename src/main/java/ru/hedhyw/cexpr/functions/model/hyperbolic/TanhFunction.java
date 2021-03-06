package ru.hedhyw.cexpr.functions.model.hyperbolic;

import ru.hedhyw.cexpr.complex.ComplexUtils;
import ru.hedhyw.cexpr.complex.model.Complex;
import ru.hedhyw.cexpr.functions.model.IFunction;

public class TanhFunction implements IFunction {

  private static final String NAME = "TANH";
  private static final boolean optimizable = true;

  public Complex eval(Complex arg) {
    return ComplexUtils.tanh(arg);
  }

  public String getName() {
    return NAME;
  }

  public boolean isOptimizable() {
    return optimizable;
  }

}