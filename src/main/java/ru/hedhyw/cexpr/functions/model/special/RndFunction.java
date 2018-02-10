package ru.hedhyw.cexpr.functions.model.special;

import ru.hedhyw.cexpr.complex.ComplexUtils;
import ru.hedhyw.cexpr.complex.model.Complex;
import ru.hedhyw.cexpr.functions.model.IFunction;

public class RndFunction implements IFunction {

  private static final String NAME = "RND";
  private static final boolean optimizable = false;

  public Complex eval(Complex arg) {
    Complex rnd = ComplexUtils.random(arg);
    return new Complex(
      rnd.getReal() * arg.getReal(),
      rnd.getImaginary() * arg.getImaginary());
  }

  public String getName() {
    return NAME;
  }

  public boolean isOptimizable() {
    return optimizable;
  }

}