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
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author Maxim Krivchun
 */
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

    public Parser(String str, Functions functions, Constants constants) {
        lex = new Lexical(str, functions, constants);
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
