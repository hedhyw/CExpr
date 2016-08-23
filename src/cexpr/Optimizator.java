/*
 * The MIT License
 *
 * Copyright 2016 Maxim Krivchun.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package cexpr;

import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author Maxim Krivchun
 */
public class Optimizator {

    private List<Command> cmds;
    private Functions functions;

    public Optimizator(List<Command> cmds, Functions functions) {
        this.cmds = cmds;
        this.functions = functions;
    }

    public List<Command> optimize() {
        if (cmds.size() <= 1) {
            return cmds;
        }
        int optimized;
        ListIterator<Command> it;
        CmdVal newVal = null;
        do {
            optimized = 0;
            it = cmds.listIterator();
            while (it.hasNext()) {
                Command cmd = it.next();
                boolean val0num = (cmd.val0.type == CmdVal.VAL_TYPE.NUM),
                        val1num = (cmd.val1 != null
                        && cmd.val1.type == CmdVal.VAL_TYPE.NUM);
                if (!((cmd.cmd == Command.CMD.CALL && val0num)
                        || (val0num && val1num))) {
                    continue;
                }
                Complex val0 = (Complex) cmd.val0.val, val1 = null;
                if (val1num) {
                    val1 = (Complex) cmd.val1.val;
                }
                Complex res = ComplexUtils.ZERO;
                switch (cmd.cmd) {
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
                        if (val1.im == 0 && val1.re == 0) {
                            throw new CompileError("/, Divided by zero");
                        }
                        res = ComplexUtils.div(val0, val1);
                        break;
                    case POW:
                        if (val1.im != 0) {
                            throw new CompileError("^, Exponent has imaginary unit");
                        }
                        res = ComplexUtils.pow(val0, val1.re);
                        break;
                    case MOD:
                        if (val0.im != 0 || val1.im != 0) {
                            throw new CompileError("%, Numbers have imaginary units");
                        }
                        res = new Complex(val0.re % val1.re, 0);
                        break;
                    case CALL:
                        Functions.function func = functions.get(cmd.function_name);
                        if (!func.optimize()) {
                            continue;
                        }
                        res = functions.get(cmd.function_name).eval(val0);
                        break;
                }
                int reg_res = cmd.reg_res;
                ++optimized;
                it.remove();
                newVal = new CmdVal(res, CmdVal.VAL_TYPE.NUM);
                while (it.hasNext()) {
                    cmd = it.next();
                    if (cmd.val0.type == CmdVal.VAL_TYPE.REG
                            && (int) cmd.val0.val == reg_res) {
                        it.set(new Command(cmd.cmd, newVal, cmd.val1,
                                cmd.function_name, cmd.reg_res));
                    }
                    if (cmd.val1 != null && cmd.val1.type == CmdVal.VAL_TYPE.REG
                            && (int) cmd.val1.val == reg_res) {
                        it.set(new Command(cmd.cmd, cmd.val0, newVal,
                                cmd.function_name, cmd.reg_res));
                    }
                }
            }
        } while (optimized != 0);
        if (cmds.size() == 0) cmds.add(
                new Command(Command.CMD.CALL,newVal,null,"set",1));
        return cmds;
    }
}
