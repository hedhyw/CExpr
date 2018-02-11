package ru.hedhyw.cexpr.functions.factory;

import java.util.HashMap;

import ru.hedhyw.cexpr.functions.model.IFunction;
import ru.hedhyw.cexpr.functions.model.SqrtFunction;
import ru.hedhyw.cexpr.functions.model.exponent.*;
import ru.hedhyw.cexpr.functions.model.hyperbolic.*;
import ru.hedhyw.cexpr.functions.model.number.*;
import ru.hedhyw.cexpr.functions.model.special.*;
import ru.hedhyw.cexpr.functions.model.trigonometric.*;

public class FunctionsFactory implements IFunctionFactory {

  private static HashMap<String, IFunction> functions;

  public FunctionsFactory() {
    functions = new HashMap<String, IFunction>();

    addFunction(new SqrtFunction());

    addFunction(new ExpFunction());
    addFunction(new LnFunction());
    addFunction(new Log10Function());

    addFunction(new CoshFunction());
    addFunction(new CtanhFunction());
    addFunction(new SinhFunction());
    addFunction(new TanhFunction());

    addFunction(new AbsFunction());
    addFunction(new ArgFunction());
    addFunction(new ConFunction());
    addFunction(new ImFunction());
    addFunction(new InvFunction());
    addFunction(new ReFunction());
    addFunction(new RoundFunction());

    addFunction(new RndFunction());
    addFunction(new SetFunction());

    addFunction(new AcosFunction());
    addFunction(new AsinFunction());
    addFunction(new AtanFunction());
    addFunction(new CosFunction());
    addFunction(new CtanFunction());
    addFunction(new SinFunction());
    addFunction(new TanFunction());
  }

  public void addFunction(IFunction function) {
    functions.put(function.getName().toUpperCase(), function);
  }

  public IFunction getFunction(String name) {
    return functions.get(name.toUpperCase());
  }

  public boolean hasFunction(String name) {
    return functions.containsKey(name.toUpperCase());
  }

}
