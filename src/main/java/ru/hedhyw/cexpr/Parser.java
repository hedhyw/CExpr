package ru.hedhyw.cexpr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import ru.hedhyw.cexpr.functions.factory.IFunctionFactory;
import ru.hedhyw.cexpr.model.compile.CompileError;

public class Parser {

    private Lexical lex;
    private Stack<ExprToken> stk;
    private List<ExprToken> out;

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

    public Parser(String str, IFunctionFactory functionFactory, Constants constants) {
        lex = new Lexical(str, functionFactory, constants);
        stk = new Stack<>();
        out = new ArrayList<>();
    }

    public List<ExprToken> get() throws CompileError {
        ExprToken tok;
        boolean unary = true;
        do {
            tok = lex.next_token();
            switch (tok.type) {
                case NUM_RE:
                case NUM_IM:
                case IDENTIFIER:
                    out.add(tok);
                    unary = false;
                    break;
                case FUNCTION:
                case OPERATOR:
                    char operator = (tok.type == ExprToken.TYPE.FUNCTION
                            ? 'f' : (Character) tok.val);
                    int priority = OPERATOR_PRIORITY.get(operator);
                    if (unary && operator == '-')
                        tok = new ExprToken(ExprToken.TYPE.OPERATOR, 'm'); // unary minus
                    if (unary && operator == '+')
                        tok = new ExprToken(ExprToken.TYPE.OPERATOR, 'p'); // unary plus
                    ExprToken top;
                    unary = true;
                    if (operator == ')') {
                        unary = false;
                        while (!stk.empty()) {
                            top = stk.pop();
                            if (top.type == ExprToken.TYPE.FUNCTION
                                    || (char) top.val != '(') {
                                out.add(top);
                            } else {
                                break;
                            }
                        };
                    } else if (operator == '(') {
                        stk.add(tok);
                    } else if (stk.empty()) {
                        stk.add(tok);
                    } else {
                        int top_priority;
                        while (!stk.empty()) {
                            top = stk.peek();
                            top_priority = OPERATOR_PRIORITY
                                    .get(top.type == ExprToken.TYPE.FUNCTION
                                            ? 'f' : (char) top.val);
                            if (top.type == ExprToken.TYPE.FUNCTION
                                    || top_priority >= priority) {
                                out.add(top);
                                stk.pop();
                            } else {
                                break;
                            }
                        }
                        stk.add(tok);
                    }
                    break;
            }
        } while (tok.type != ExprToken.TYPE.END);
        while (!stk.empty()) {
            out.add(stk.pop());
        }

        // empty input
        if (out.size() == 0){
            out.add(new ExprToken(ExprToken.TYPE.NUM_RE, 0.0));
        }
        // single number/variable
        if (out.size() == 1 && (out.get(0).type == ExprToken.TYPE.NUM_IM
                || out.get(0).type == ExprToken.TYPE.NUM_RE
                || out.get(0).type == ExprToken.TYPE.IDENTIFIER)) {
            out.add(new ExprToken(ExprToken.TYPE.NUM_RE, 0.0));
            out.add(new ExprToken(ExprToken.TYPE.OPERATOR, '+'));
        }

        return out;
    }
}
