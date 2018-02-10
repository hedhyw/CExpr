package ru.hedhyw.cexpr;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Maxim Krivchun
 */
public class Compiler {

    private Parser parser;
    private Functions functions;
    private Constants constants;

    public Compiler(){
        functions = new Functions();
        constants = new Constants();
    }

    /**
     * it gets expression token value with type
     */
    private CmdVal convertVal(ExprToken tok) {
        Object val = tok.val;
        CmdVal.VAL_TYPE type = null;
        switch (tok.type) {
            case NUM_RE:
                type = CmdVal.VAL_TYPE.NUM;
                val = new Complex((double) tok.val, 0);
                break;
            case NUM_IM:
                type = CmdVal.VAL_TYPE.NUM;
                val = new Complex(0, (double) tok.val);
                break;
            case IDENTIFIER:
                type = CmdVal.VAL_TYPE.VAR;
                break;
            case REG:
                type = CmdVal.VAL_TYPE.REG;
                break;

        }
        return new CmdVal(val, type);
    }

    /**
     * add custom functions
     * @param functions custom functions
    */
    public void setFunctions(Functions functions){
        this.functions = functions;
    }

    /**
     * add custom constants
     * @param constants custom constants
    */
    public void setConstants(Constants constants){
        this.constants = constants;
    }

    /**
     * it compiles expression
     * @param code the text of expression
     * @param functions HashMap with functions
     * @throws CompileError parser & compiler error
     * @return HashMap with variables
     */
    public Compiled compile(String code) throws CompileError {
        parser = new Parser(code, functions, constants);
        List<Command> cmds = new ArrayList<>();
        List<ExprToken> list = parser.get();
        List<ExprToken> new_list = new ArrayList<>();
        int num_args;
        int reg = 0;
        int last_size;
        while (list.size() != 1) {
            last_size = list.size();
            Iterator<ExprToken> it = list.iterator();
            ExprToken tok;
            num_args = 0;
            while (it.hasNext()) {
                tok = it.next();
                if (tok.type == ExprToken.TYPE.NUM_IM
                        || tok.type == ExprToken.TYPE.NUM_RE
                        || tok.type == ExprToken.TYPE.REG
                        || tok.type == ExprToken.TYPE.IDENTIFIER) {
                    num_args++;
                    new_list.add(tok);
                } else if (tok.type == ExprToken.TYPE.FUNCTION) {
                    String function = (String) tok.val;
                    if (num_args >= 1) {
                        ExprToken tok0 = new_list.remove(new_list.size() - 1);
                        CmdVal val0 = convertVal(tok0);
                        cmds.add(new Command(Command.CMD.CALL, val0, null, function, ++reg));
                        new_list.add(new ExprToken(ExprToken.TYPE.REG, reg));
                    } else {
                        new_list.add(tok);
                    }
                    num_args = 0;
                } else { // ExprToken.TYPE.OPERATOR
                    char operator = (char) tok.val;
                    if (num_args >= 2 && !(operator == 'm' || operator == 'p')) {
                        ExprToken tok1 = new_list.remove(new_list.size() - 1);
                        ExprToken tok0 = new_list.remove(new_list.size() - 1);
                        CmdVal val0 = convertVal(tok0), val1 = convertVal(tok1);

                        Command.CMD type = null;
                        switch (operator) {
                            case '*':
                                type = Command.CMD.MUL;
                                break;
                            case '/':
                                type = Command.CMD.DIV;
                                break;
                            case '-':
                                type = Command.CMD.SUB;
                                break;
                            case '+':
                                type = Command.CMD.ADD;
                                break;
                            case '^':
                                type = Command.CMD.POW;
                                break;
                            case '%':
                                type = Command.CMD.MOD;
                                break;
                        }
                        cmds.add(new Command(type, val0,
                                val1, Character.toString(operator), ++reg));
                        new_list.add(new ExprToken(ExprToken.TYPE.REG, reg));
                        num_args--;
                    } else if (num_args >= 1 && operator == 'm') { // unary `-`
                        ExprToken tok0 = new_list.remove(new_list.size() - 1);
                        CmdVal val0 = convertVal(tok0),
                                val1 = new CmdVal(new Complex(-1,0),
                                        CmdVal.VAL_TYPE.NUM);
                        cmds.add(new Command(Command.CMD.MUL, val0,
                                val1, "*", ++reg));
                        new_list.add(new ExprToken(ExprToken.TYPE.REG, reg));
                    } else if (num_args >= 1 && operator == 'p') { // unary `+`
                        ExprToken tok0 = new_list.remove(new_list.size() - 1);
                        CmdVal val0 = convertVal(tok0),
                                val1 = new CmdVal(new Complex(0,0),
                                        CmdVal.VAL_TYPE.NUM);
                        cmds.add(new Command(Command.CMD.ADD, val0,
                                val1, "+", ++reg));
                        new_list.add(new ExprToken(ExprToken.TYPE.REG, reg));
                    } else {
                        new_list.add(tok);
                        num_args = 0;
                    }
                }
            }

            list.clear();
            it = new_list.iterator();
            while (it.hasNext()) {
                list.add(it.next());
            }
            new_list.clear();

            if (last_size == list.size()) {
                throw new CompileError("Syntax error");
            }
        }
        Optimizator optimizator = new Optimizator(cmds, functions);
        return new Compiled(optimizator.optimize(), functions);
    }
}
