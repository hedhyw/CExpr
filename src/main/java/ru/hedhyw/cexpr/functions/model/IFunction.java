package ru.hedhyw.cexpr.functions.model;

import ru.hedhyw.cexpr.complex.model.Complex;

public interface IFunction {

  public Complex eval(Complex arg);
  public String getName();
  public boolean isOptimizable();

}