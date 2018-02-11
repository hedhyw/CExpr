package ru.hedhyw.cexpr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import ru.hedhyw.cexpr.model.Constants;
import ru.hedhyw.cexpr.model.errors.CompileError;
import ru.hedhyw.cexpr.model.token.IToken;
import ru.hedhyw.cexpr.model.token.IToken.TOK_TYPE;
import ru.hedhyw.cexpr.model.token.OperatorToken;
import ru.hedhyw.cexpr.model.token.RealNumberToken;

public class Parser {

  private Lexical lex;
  private Stack<IToken> stk;
  private List<IToken> out;

  private static final HashMap<Character, Integer> OPERATOR_PRIORITY // for stack
    = new HashMap<Character, Integer>() {
      {
        put('f', 5); // function
        put('(', 0); // for stack
        put(')', 0);
        put('^', 4);
        put('p', 3); // unary plus
        put('m', 3); // unary minus
        put('*', 2);
        put('%', 2);
        put('/', 2);
        put('+', 1);
        put('-', 1);
      }
    };

  public Parser(
    Lexical lex,
    String str,
    Constants constants) {
      this.lex = lex;
      stk = new Stack<>();
      out = new ArrayList<>();
  }

  private void handleOperatorAndFunction(IToken tok, boolean unary) {
    boolean isFunction = tok.getType() == TOK_TYPE.FUNCTION;
    char operator = isFunction ?
        'f' : ((OperatorToken) tok).getValue();
    int priority = OPERATOR_PRIORITY.get(operator);
    if (unary) {
      if (operator == '-') { // unary minus
        tok = new OperatorToken('m');
      } else if (operator == '+') { // unary plus
        tok = new OperatorToken('p');
      }
    }
    IToken top;
    unary = true;
    if (operator == ')') {
      unary = false;
      while (!stk.empty()) {
        top = stk.pop();
        if (top.getType() == TOK_TYPE.FUNCTION ||
          ((OperatorToken) top).getValue() != '(') {
            out.add(top);
        } else {
          break;
        }
      };
    } else if (operator == '(' || stk.empty()) {
      stk.add(tok);
    } else {
      int topPriority;
      while (!stk.empty()) {
        top = stk.peek();
        isFunction = top.getType() == TOK_TYPE.FUNCTION;
        topPriority = OPERATOR_PRIORITY
          .get(isFunction ? 'f' : (char) top.getValue());
        if (isFunction || topPriority >= priority) {
          out.add(top);
          stk.pop();
        } else {
          break;
        }
      }
      stk.add(tok);
    }
  }

  public List<IToken> get() throws CompileError {
    IToken tok;
    boolean unary = true;
    do {
      tok = lex.nextToken();
      switch (tok.getType()) {
        case NUM_RE:
        case NUM_IM:
        case IDENTIFIER:
          out.add(tok);
          unary = false;
          break;
        case FUNCTION:
        case OPERATOR:
          handleOperatorAndFunction(tok, unary);
          break;
        default:
          break;
      }
    } while (tok.getType() != IToken.TOK_TYPE.END);
    while (!stk.empty()) {
      out.add(stk.pop());
    }
    if (out.isEmpty()) { // empty input
      out.add(new RealNumberToken(0.0));
    }
    else if (out.size() == 1) { // single number/variable
      if (out.get(0).getType() == IToken.TOK_TYPE.NUM_IM ||
        out.get(0).getType() == IToken.TOK_TYPE.NUM_RE ||
        out.get(0).getType() == IToken.TOK_TYPE.IDENTIFIER) {
          out.add(new RealNumberToken(0.0));
          out.add(new OperatorToken('+'));
      }
    }
    return out;
  }

}
