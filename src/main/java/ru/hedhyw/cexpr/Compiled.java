package ru.hedhyw.cexpr;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

public class Compiled extends HashMap<String, Complex> {

    private List<Command> cmds;
    private HashMap<Integer, Complex> regs;
    private Functions functions;

    public Compiled(List<Command> cmds, Functions functions) {
        super();
        regs = new HashMap<>();
        this.cmds = cmds;
        this.functions = functions;
    }

    private Complex getVal(@Nullable CmdVal val) {
        if (val == null) {
            return null;
        }
        switch (val.type) {
            case REG:
                return regs.get((int) val.val);
            case VAR:
                return get((String) val.val);
            case NUM:
                return (Complex) val.val;
        }
        return null;
    }

    public Complex execute() throws ExecuteError {
        Iterator<Command> it = cmds.iterator();
        Complex reg = null;
        Command cmd;
        while (it.hasNext()) {
            cmd = it.next();
            Complex val0 = getVal(cmd.val0),
                    val1 = getVal(cmd.val1);
            if (val0 == null) {
                throw new ExecuteError("Unknown value: " + (String) cmd.val0.val);
            }
            /*if (val1 == null) {
                throw new ExecuteError("Unknown value: " + (String)cmd.val1.val);
            }*/
            switch (cmd.cmd) {
                case ADD:
                    reg = ComplexUtils.add(val0, val1);
                    break;
                case MOD:
                    if (val0.im != 0 || val1.im != 0) {
                        throw new ExecuteError("%, Numbers have imaginary units");
                    }
                    reg = new Complex(val0.re % val1.re, 0);
                    break;
                case SUB:
                    reg = ComplexUtils.sub(val0, val1);
                    break;
                case MUL:
                    reg = ComplexUtils.mul(val0, val1);
                    break;
                case DIV:
                    if (val1.im == 0 && val1.re == 0) {
                        throw new ExecuteError("/, Divided by zero");
                    }
                    reg = ComplexUtils.div(val0, val1);
                    break;
                case POW:
                    if (val1.im != 0) {
                        throw new ExecuteError("^, Exponent has imaginary unit");
                    }
                    reg = ComplexUtils.pow(val0, val1.re);
                    break;
                case CALL:
                    reg = functions.get(cmd.function_name).eval(val0);
                    break;
            }
            regs.put(cmd.reg_res, reg);
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
