package ru.hedhyw.cexpr.functions.model;

import ru.hedhyw.cexpr.complex.ComplexUtils;
import ru.hedhyw.cexpr.complex.model.Complex;
import ru.hedhyw.cexpr.functions.model.IFunction;

public class SqrtFunction implements IFunction {

  private static final String NAME = "SQRT";
  private static final boolean optimizable = true;

  public Complex eval(Complex arg) {
    return ComplexUtils.sqrt(arg);
  }

  public String getName() {
    return NAME;
  }

  public boolean isOptimizable() {
    return optimizable;
  }

}