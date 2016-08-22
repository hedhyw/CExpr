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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Maxim Krivchun
 */
public class Compiler {

    private Parser parser;

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
     * function adds default constants to variables of compiled code
     */
    private Compiled putConstants(Compiled compiled) {
        compiled.put("PI", ComplexUtils.PI);
        compiled.put("E", ComplexUtils.E);
        return compiled;
    }

    /**
     * @param code the text of expression
     * @throws CompileError parser & compiler error
     * @return HashMap with variables
     */
    public Compiled compile(String code) throws CompileError {
        return compile(code, new Functions()); // includes only default functions
    }

    /**
     * @param code the text of expression
     * @param functions HashMap with functions
     * @throws CompileError parser & compiler error
     * @return HashMap with variables
     */
    public Compiled compile(String code, Functions functions) throws CompileError {
        parser = new Parser(code, functions);
        List<Command> cmds = new ArrayList<>();
        List<ExprToken> list = parser.get();
        List<ExprToken> new_list = new ArrayList<>();
        int num_args = 0;
        int reg = 0;
        int last_size;
        while (list.size() != 1) {
            last_size = list.size();
            Iterator<ExprToken> it = list.iterator();
            ExprToken tok;
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
                    if (num_args >= 2) {
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
                    } else {
                        new_list.add(tok);
                    }
                    num_args = 0;
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
        return putConstants(new Compiled(cmds, functions));
    }
}
