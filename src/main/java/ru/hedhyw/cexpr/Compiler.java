package ru.hedhyw.cexpr;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ru.hedhyw.cexpr.complex.model.Complex;
import ru.hedhyw.cexpr.functions.factory.FunctionsFactory;
import ru.hedhyw.cexpr.functions.factory.IFunctionFactory;
import ru.hedhyw.cexpr.model.Constants;
import ru.hedhyw.cexpr.model.command.Command;
import ru.hedhyw.cexpr.model.command.ICommandValue;
import ru.hedhyw.cexpr.model.command.NumValue;
import ru.hedhyw.cexpr.model.errors.CompileError;
import ru.hedhyw.cexpr.model.token.FunctionToken;
import ru.hedhyw.cexpr.model.token.IToken;
import ru.hedhyw.cexpr.model.token.OperatorToken;
import ru.hedhyw.cexpr.model.token.RegisterToken;

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
        List<IToken> list = parser.get();
        List<IToken> newList = new ArrayList<>();
        int argsCount;
        int reg = 0;
        int lastSize;
        while (list.size() != 1) {
            lastSize = list.size();
            Iterator<IToken> it = list.iterator();
            IToken tok;
            argsCount = 0;
            while (it.hasNext()) {
                tok = it.next();
                if (tok.getType() == IToken.TOK_TYPE.NUM_IM
                        || tok.getType() == IToken.TOK_TYPE.NUM_RE
                        || tok.getType() == IToken.TOK_TYPE.REG
                        || tok.getType() == IToken.TOK_TYPE.IDENTIFIER) {
                    argsCount++;
                    newList.add(tok);
                } else if (tok.getType() == IToken.TOK_TYPE.FUNCTION) {
                    String function = ((FunctionToken) tok).getValue();
                    if (argsCount >= 1) {
                        IToken tok0 = newList.remove(newList.size() - 1);
                        ICommandValue val0 = ICommandValue.of(tok0);
                        cmds.add(new Command(Command.CMD.CALL, val0,
                            null, function, ++reg));
                        newList.add(new RegisterToken(reg));
                    } else {
                        newList.add(tok);
                    }
                    argsCount = 0;
                } else {
                    OperatorToken opTok = (OperatorToken) tok;
                    char operator = opTok.getValue();
                    if (argsCount >= 2 && !(operator == 'm' || operator == 'p')) {
                        IToken tok1 = newList.remove(newList.size() - 1);
                        IToken tok0 = newList.remove(newList.size() - 1);
                        ICommandValue val0 = ICommandValue.of(tok0);
                        ICommandValue val1 = ICommandValue.of(tok1);

                        Command.CMD type = opTok.getCommand();
                        cmds.add(new Command(type, val0,
                                val1, Character.toString(operator), ++reg));
                        newList.add(new RegisterToken(reg));
                        argsCount--;
                    } else if (argsCount >= 1 && operator == 'm') { // unary `-`
                        IToken tok0 = newList.remove(newList.size() - 1);
                        ICommandValue val0 = ICommandValue.of(tok0);
                        ICommandValue val1 = new NumValue(new Complex(-1,0));
                        cmds.add(new Command(Command.CMD.MUL, val0,
                                val1, "*", ++reg));
                        newList.add(new RegisterToken(reg));
                    } else if (argsCount >= 1 && operator == 'p') { // unary `+`
                        IToken tok0 = newList.remove(newList.size() - 1);
                        ICommandValue val0 = ICommandValue.of(tok0);
                        ICommandValue val1 = new NumValue(new Complex(0,0));
                        cmds.add(new Command(Command.CMD.ADD, val0,
                                val1, "+", ++reg));
                        newList.add(new RegisterToken(reg));
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
