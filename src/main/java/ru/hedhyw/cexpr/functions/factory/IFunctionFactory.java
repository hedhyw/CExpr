package ru.hedhyw.cexpr.functions.factory;

import ru.hedhyw.cexpr.functions.model.IFunction;

public interface IFunctionFactory {

  public IFunction getFunction(String name);
  public boolean hasFunction(String name);
  public void addFunction(IFunction function);

}