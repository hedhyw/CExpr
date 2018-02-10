package ru.hedhyw.cexpr;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ru.hedhyw.cexpr.complex.model.Complex;
import ru.hedhyw.cexpr.functions.factory.FunctionsFactory;
import ru.hedhyw.cexpr.functions.factory.IFunctionFactory;
import ru.hedhyw.cexpr.model.command.Command;
import ru.hedhyw.cexpr.model.command.ICommandValue;
import ru.hedhyw.cexpr.model.command.NumValue;
import ru.hedhyw.cexpr.model.compile.CompileError;

public class Compiler {

    private Parser parser;
    private IFunctionFactory functionsFactory;
    private Constants constants;

    public Compiler(){
        constants = new Constants();
    }

    /**
     * add custom functions
     * @param functions custom functions
    */
    public void setFunctionsFactory(IFunctionFactory functionsFactory){
        this.functionsFactory = functionsFactory;
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
        if (functionsFactory == null) functionsFactory = new FunctionsFactory();
        parser = new Parser(code, functionsFactory, constants);
        List<Command> cmds = new ArrayList<>();
        List<ExprToken> list = parser.get();
        List<ExprToken> newList = new ArrayList<>();
        int argsCount;
        int reg = 0;
        int lastSize;
        while (list.size() != 1) {
            lastSize = list.size();
            Iterator<ExprToken> it = list.iterator();
            ExprToken tok;
            argsCount = 0;
            while (it.hasNext()) {
                tok = it.next();
                if (tok.type == ExprToken.TYPE.NUM_IM
                        || tok.type == ExprToken.TYPE.NUM_RE
                        || tok.type == ExprToken.TYPE.REG
                        || tok.type == ExprToken.TYPE.IDENTIFIER) {
                    argsCount++;
                    newList.add(tok);
                } else if (tok.type == ExprToken.TYPE.FUNCTION) {
                    String function = (String) tok.val;
                    if (argsCount >= 1) {
                        ExprToken tok0 = newList.remove(newList.size() - 1);
                        ICommandValue val0 =ICommandValue.of(tok0);
                        cmds.add(new Command(Command.CMD.CALL, val0,
                            null, function, ++reg));
                        newList.add(new ExprToken(ExprToken.TYPE.REG, reg));
                    } else {
                        newList.add(tok);
                    }
                    argsCount = 0;
                } else { // ExprToken.TYPE.OPERATOR
                    char operator = (char) tok.val;
                    if (argsCount >= 2 && !(operator == 'm' || operator == 'p')) {
                        ExprToken tok1 = newList.remove(newList.size() - 1);
                        ExprToken tok0 = newList.remove(newList.size() - 1);
                        ICommandValue val0 = ICommandValue.of(tok0);
                        ICommandValue val1 = ICommandValue.of(tok1);

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
                        newList.add(new ExprToken(ExprToken.TYPE.REG, reg));
                        argsCount--;
                    } else if (argsCount >= 1 && operator == 'm') { // unary `-`
                        ExprToken tok0 = newList.remove(newList.size() - 1);
                        ICommandValue val0 = ICommandValue.of(tok0);
                        ICommandValue val1 = new NumValue(new Complex(-1,0));
                        cmds.add(new Command(Command.CMD.MUL, val0,
                                val1, "*", ++reg));
                        newList.add(new ExprToken(ExprToken.TYPE.REG, reg));
                    } else if (argsCount >= 1 && operator == 'p') { // unary `+`
                        ExprToken tok0 = newList.remove(newList.size() - 1);
                        ICommandValue val0 = ICommandValue.of(tok0);
                        ICommandValue val1 = new NumValue(new Complex(0,0));
                        cmds.add(new Command(Command.CMD.ADD, val0,
                                val1, "+", ++reg));
                        newList.add(new ExprToken(ExprToken.TYPE.REG, reg));
                    } else {
                        newList.add(tok);
                        argsCount = 0;
                    }
                }
            }

            list.clear();
            it = newList.iterator();
            while (it.hasNext()) {
                list.add(it.next());
            }
            newList.clear();

            if (lastSize == list.size()) {
                throw new CompileError("Syntax error");
            }
        }
        Optimizator optimizator = new Optimizator(cmds, functionsFactory);
        return new Compiled(optimizator.optimize(), functionsFactory);
    }
}
