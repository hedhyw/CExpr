package ru.hedhyw.cexpr;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

import ru.hedhyw.cexpr.model.execute.ExecuteError;
import ru.hedhyw.cexpr.complex.ComplexUtils;
import ru.hedhyw.cexpr.complex.model.Complex;
import ru.hedhyw.cexpr.functions.factory.IFunctionFactory;
import ru.hedhyw.cexpr.model.command.Command;
import ru.hedhyw.cexpr.model.command.ICommandValue;
import ru.hedhyw.cexpr.model.command.NumValue;
import ru.hedhyw.cexpr.model.command.RegValue;
import ru.hedhyw.cexpr.model.command.VarValue;

public class Compiled extends HashMap<String, Complex> {

    static final long serialVersionUID = 19784;

    private List<Command> cmds;
    private HashMap<Integer, Complex> regs;
    private IFunctionFactory functionsFactory;

    public Compiled(List<Command> cmds, IFunctionFactory functionsFactory) {
        super();
        regs = new HashMap<>();
        this.cmds = cmds;
        this.functionsFactory = functionsFactory;
    }

    @Nullable
    private Complex getVal(@Nullable ICommandValue val) {
        if (val == null) {
            return null;
        }
        switch (val.getType()) {
            case REG:
                return regs.get(((RegValue) val).getValue());
            case VAR:
                return get(((VarValue) val).getValue());
            case NUM:
                return ((NumValue) val).getValue();
        }
        return null;
    }

    public Complex execute() throws ExecuteError {
        Iterator<Command> it = cmds.iterator();
        Complex reg = null;
        Command cmd;
        while (it.hasNext()) {
            cmd = it.next();
            Complex val0 = getVal(cmd.getFirstValue()),
                    val1 = getVal(cmd.getSecondValue());
            if (val0 == null) {
                throw new ExecuteError(
                    String.format("Unknown value: %s",
                        cmd.getFirstValue().toString()));
            }
            switch (cmd.getCommand()) {
                case ADD:
                    reg = ComplexUtils.add(val0, val1);
                    break;
                case MOD:
                    if (val0.getImaginary() != 0 || val1.getImaginary() != 0) {
                        throw new ExecuteError(
                            "%, Numbers have imaginary units");
                    }
                    reg = new Complex(val0.getReal() % val1.getReal(), 0);
                    break;
                case SUB:
                    reg = ComplexUtils.sub(val0, val1);
                    break;
                case MUL:
                    reg = ComplexUtils.mul(val0, val1);
                    break;
                case DIV:
                    if (val1.getImaginary() == 0 && val1.getReal() == 0) {
                        throw new ExecuteError("/, Divided by zero");
                    }
                    reg = ComplexUtils.div(val0, val1);
                    break;
                case POW:
                    if (val1.getImaginary() != 0) {
                        throw new ExecuteError(
                            "^, Exponent has imaginary unit");
                    }
                    reg = ComplexUtils.pow(val0, val1.getReal());
                    break;
                case CALL:
                    reg = functionsFactory.getFunction(cmd.getFunctionName())
                        .eval(val0);
                    break;
            }
            regs.put(cmd.getResultRegister(), reg);
        }
        return reg;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        Iterator<Command> it = cmds.iterator();
        while (it.hasNext()) {
            str.append(it.next().toString());
            str.append('\n');
        }
        return str.toString();
    }

}
