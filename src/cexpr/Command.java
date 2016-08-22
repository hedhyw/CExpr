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

import com.sun.istack.internal.Nullable;

/**
 *
 * @author Maxim Krivchun
 */
public class Command {

    public final CMD cmd;
    public final CmdVal val0, val1;
    public final int reg_res;
    public final String function_name;

    public Command(CMD cmd, CmdVal val0,
            CmdVal val1,
            @Nullable String function_name,
            int result_register) {
        this.cmd = cmd;
        this.val0 = val0;
        this.val1 = val1;
        this.function_name = function_name;
        reg_res = result_register;
    }

    @Override
    public String toString() {
        if (cmd == CMD.CALL) {
            return String.format("r%d := %s %s", reg_res,
                    function_name, val0.toString());
        }
        return String.format("r%d := %s %s, %s", reg_res,
                cmd.name(), val0.toString(), val1.toString());
    }

    enum CMD {
        ADD,
        SUB,
        MUL,
        DIV,
        POW,
        MOD,
        CALL
    }
}
