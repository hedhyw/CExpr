package ru.hedhyw.cexpr.functions.model.exponent;

import ru.hedhyw.cexpr.complex.ComplexUtils;
import ru.hedhyw.cexpr.complex.model.Complex;
import ru.hedhyw.cexpr.functions.model.IFunction;

public class LnFunction implements IFunction {

  private static final String NAME = "LN";
  private static final boolean optimizable = true;

  public Complex eval(Complex arg) {
    return ComplexUtils.ln(arg);
  }

  public String getName() {
    return NAME;
  }

  public boolean isOptimizable() {
    return optimizable;
  }

}