package ru.hedhyw.cexpr.functions.model.trigonometric;

import ru.hedhyw.cexpr.complex.ComplexUtils;
import ru.hedhyw.cexpr.complex.model.Complex;
import ru.hedhyw.cexpr.functions.model.IFunction;

public class AsinFunction implements IFunction {

  private static final String NAME = "ASIN";
  private static final boolean optimizable = true;

  public Complex eval(Complex arg) {
      return ComplexUtils.asin(arg);
  }

  public String getName() {
    return NAME;
  }

  public boolean isOptimizable() {
    return optimizable;
  }

}