package ru.hedhyw.cexpr.functions.model.trigonometric;

import ru.hedhyw.cexpr.complex.ComplexUtils;
import ru.hedhyw.cexpr.complex.model.Complex;
import ru.hedhyw.cexpr.functions.model.IFunction;

public class SinFunction implements IFunction {

  private static final String NAME = "SIN";
  private static final boolean optimizable = true;

  public Complex eval(Complex arg) {
      return ComplexUtils.sin(arg);
  }

  public String getName() {
    return NAME;
  }

  public boolean isOptimizable() {
    return optimizable;
  }

}