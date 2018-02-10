package ru.hedhyw.cexpr;

import javax.annotation.Nullable;

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
