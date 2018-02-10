package ru.hedhyw.cexpr.functions.model.number;

import ru.hedhyw.cexpr.complex.ComplexUtils;
import ru.hedhyw.cexpr.complex.model.Complex;
import ru.hedhyw.cexpr.functions.model.IFunction;

public class AbsFunction implements IFunction {

  private static final String NAME = "ABS";
  private static final boolean optimizable = true;

  public Complex eval(Complex arg) {
    return new Complex(ComplexUtils.abs(arg), 0);
  }

  public String getName() {
    return NAME;
  }

  public boolean isOptimizable() {
    return optimizable;
  }

}