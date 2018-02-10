package ru.hedhyw.cexpr;

import java.util.List;
import java.util.ListIterator;

import ru.hedhyw.cexpr.complex.ComplexUtils;
import ru.hedhyw.cexpr.complex.model.Complex;
import ru.hedhyw.cexpr.functions.factory.IFunctionFactory;
import ru.hedhyw.cexpr.functions.model.IFunction;
import ru.hedhyw.cexpr.model.command.Command;
import ru.hedhyw.cexpr.model.command.ICommandValue;
import ru.hedhyw.cexpr.model.command.NumValue;
import ru.hedhyw.cexpr.model.command.RegValue;
import ru.hedhyw.cexpr.model.command.ICommandValue.CMD_TYPE;
import ru.hedhyw.cexpr.model.compile.CompileError;

public class Optimizator {

    private List<Command> cmds;
    private IFunctionFactory functionFactory;

    public Optimizator(List<Command> cmds, IFunctionFactory functionFactory) {
        this.cmds = cmds;
        this.functionFactory = functionFactory;
    }

    public List<Command> optimize() {
        if (cmds.size() <= 1) {
            return cmds;
        }
        int optimized;
        ListIterator<Command> it;
        ICommandValue newVal = null;
        do {
            optimized = 0;
            it = cmds.listIterator();
            while (it.hasNext()) {
                Command cmd = it.next();
                boolean val0num = (cmd.getFirstValue().getType() == CMD_TYPE.NUM),
                        val1num = (cmd.getSecondValue() != null
                        && cmd.getSecondValue().getType() == CMD_TYPE.NUM);
                if (!((cmd.getCommand() == Command.CMD.CALL && val0num)
                        || (val0num && val1num))) {
                    continue;
                }
                Complex val0 = ((NumValue) cmd.getFirstValue()).getValue(), val1 = null;
                if (val1num) {
                    val1 = ((NumValue) cmd.getSecondValue()).getValue();
                }
                Complex res = ComplexUtils.ZERO;
                switch (cmd.getCommand()) {
                    case ADD:
                        res = ComplexUtils.add(val0, val1);
                        break;
                    case SUB:
                        res = ComplexUtils.sub(val0, val1);
                        break;
                    case MUL:
                        res = ComplexUtils.mul(val0, val1);
                        break;
                    case DIV:
                        if (!val1.hasImaginary() && !val1.hasReal()) {
                            throw new CompileError("/, Divided by zero");
                        }
                        res = ComplexUtils.div(val0, val1);
                        break;
                    case POW:
                        if (val1.hasImaginary()) {
                            throw new CompileError(
                                "^, Exponent has imaginary unit");
                        }
                        res = ComplexUtils.pow(val0, val1.getReal());
                        break;
                    case MOD:
                        if (val0.hasImaginary() || val1.hasImaginary()) {
                            throw new CompileError(
                                "%, Numbers have imaginary units");
                        }
                        res = new Complex(val0.getReal() % val1.getReal(), 0);
                        break;
                    case CALL:
                        IFunction func = functionFactory.getFunction(
                            cmd.getFunctionName());
                        if (!func.isOptimizable()) {
                            continue;
                        }
                        res = func.eval(val0);
                        break;
                }
                int reg_res = cmd.getResultRegister();
                ++optimized;
                it.remove();
                newVal = new NumValue(res);
                while (it.hasNext()) {
                    cmd = it.next();
                    if (cmd.getFirstValue().getType() == CMD_TYPE.REG &&
                        ((RegValue) cmd.getFirstValue()).getValue() == reg_res) {
                            it.set(new Command(cmd.getCommand(),
                                newVal, cmd.getSecondValue(),
                                cmd.getFunctionName(), cmd.getResultRegister()));
                    }
                    if (cmd.getSecondValue() != null &&
                        cmd.getSecondValue().getType() == CMD_TYPE.REG &&
                        ((RegValue) cmd.getSecondValue()).getValue() == reg_res) {
                            it.set(new Command(cmd.getCommand(), cmd.getFirstValue(),
                                newVal, cmd.getFunctionName(), cmd.getResultRegister()));
                    }
                }
            }
        } while (optimized != 0);
        if (cmds.size() == 0) cmds.add(
                new Command(Command.CMD.CALL,newVal,null,"set",1));
        return cmds;
    }
}
