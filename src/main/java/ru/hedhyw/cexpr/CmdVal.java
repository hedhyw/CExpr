package ru.hedhyw.cexpr;

public class CmdVal {

    final Object val;
    final VAL_TYPE type;

    public CmdVal(Object val, VAL_TYPE type) {
        this.val = val;
        this.type = type;
    }

    @Override
    public String toString() {
        switch (type) {
            case REG:
                return 'r' + String.valueOf((int) val);
            case VAR:
                return '[' + (String) val + ']';
            case NUM:
                return ((Complex) val).toString();
        }
        return super.toString();
    }

    enum VAL_TYPE {
        REG,
        VAR,
        NUM
    }
}
