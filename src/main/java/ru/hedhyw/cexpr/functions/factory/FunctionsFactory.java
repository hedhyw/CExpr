package ru.hedhyw.cexpr.functions.factory;

import java.util.HashMap;

import ru.hedhyw.cexpr.functions.model.*;
import ru.hedhyw.cexpr.functions.model.trigonometric.*;

public class FunctionsFactory implements IFunctionFactory {

    private static HashMap<String, IFunction> functions;

    public FunctionsFactory() {
        functions = new HashMap<String, IFunction>();
        addFunction(new SinFunction());
        addFunction(new CosFunction());
        addFunction(new TanFunction());
        addFunction(new AsinFunction());
        addFunction(new AcosFunction());
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
