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


  private Lexical lexical;
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


  private void handleFunction(
    FunctionToken tok,
    StateHolder state,
    List<Command> cmds,
    List<IToken> newList) {
      String function = ((FunctionToken) tok).getValue();
      if (state.argsCount >= 1) {
        IToken tok0 = newList.remove(newList.size() - 1);
        ICommandValue val0 = ICommandValue.of(tok0);
        cmds.add(new Command(Command.CMD.CALL, val0,
            null, function, ++state.register));
        newList.add(new RegisterToken(state.register));
      } else {
        newList.add(tok);
      }
      state.argsCount = 0;
  }

  private void handleOperator(
    OperatorToken opTok,
    StateHolder state,
    List<Command> cmds,
    List<IToken> newList) {
    char operator = opTok.getValue();
      if (state.argsCount >= 2 && !(operator == 'm' || operator == 'p')) {
        IToken tok1 = newList.remove(newList.size() - 1);
        IToken tok0 = newList.remove(newList.size() - 1);
        ICommandValue val0 = ICommandValue.of(tok0);
        ICommandValue val1 = ICommandValue.of(tok1);
        Command.CMD type = opTok.getCommand();
        cmds.add(new Command(type, val0,
                val1, Character.toString(operator), ++state.register));
        newList.add(new RegisterToken(state.register));
        state.argsCount--;
      } else if (state.argsCount >= 1 && operator == 'm') { // unary `-`
        IToken tok0 = newList.remove(newList.size() - 1);
        ICommandValue val0 = ICommandValue.of(tok0);
        ICommandValue val1 = new NumValue(new Complex(-1,0));
        cmds.add(new Command(Command.CMD.MUL, val0, val1, "*", ++state.register));
        newList.add(new RegisterToken(state.register));
      } else if (state.argsCount >= 1 && operator == 'p') { // unary `+`
        IToken tok0 = newList.remove(newList.size() - 1);
        ICommandValue val0 = ICommandValue.of(tok0);
        ICommandValue val1 = new NumValue(new Complex(0,0));
        cmds.add(new Command(Command.CMD.ADD, val0,
                val1, "+", ++state.register));
        newList.add(new RegisterToken(state.register));
      } else {
        newList.add(opTok);
        state.argsCount = 0;
      }
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
    lexical = new Lexical(code, functionsFactory, constants);
    parser = new Parser(lexical, code, constants);
    List<Command> cmds = new ArrayList<>();
    List<IToken> list = parser.get();
    List<IToken> newList = new ArrayList<>();
    StateHolder state = new StateHolder(0, 0);
    int lastSize;
    while (list.size() != 1) {
      lastSize = list.size();
      Iterator<IToken> it = list.iterator();
      IToken tok;
      state.argsCount = 0;
      while (it.hasNext()) {
        tok = it.next();
        switch (tok.getType()) {
          case NUM_IM:
          case NUM_RE:
          case REG:
          case IDENTIFIER:
            state.argsCount++;
            newList.add(tok);
            break;
          case FUNCTION:
            handleFunction((FunctionToken) tok, state, cmds, newList);
            break;
          case OPERATOR:
            handleOperator((OperatorToken) tok, state, cmds, newList);
            break;
          default:
            break;
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

  private class StateHolder {

    StateHolder(int argsCount, int register) {
      this.argsCount = argsCount;
      this.register = register;
    }

    public int argsCount;
    public int register;
  }
}
