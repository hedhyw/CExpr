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

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Maxim Krivchun
 */
// Reverse Polish notation
public class Lexical {

    private final String code;
    private final Functions functions;
    private int i; // position in code

    private static final List<Character> OPERATORS
            = Arrays.asList('(', ')', '^', '*', '/', '+', '-', '%');

    private static final List<Character> IGNORING
            = Arrays.asList('\n', '\t', ' ');

    private static final char DECIMAL_MARK = '.';
    private static final char IMAGINARY = 'i';

    public Lexical(String code, Functions functions) {
        this.code = code;
        this.functions = functions;
        i = 0;
    }

    private ExprToken operator_token() {
        return new ExprToken(ExprToken.TYPE.OPERATOR, code.charAt(i++));
    }

    private ExprToken number_token() throws CompileError {
        double val = 0;
        boolean marked = false,
                imaginary = false;
        int mark_i = 0; // 123,53 <- mark_i = 2;    
        for (char chr; i < code.length(); i++) {
            chr = code.charAt(i);
            if (Character.isDigit(chr)) {
                val *= 10;
                val += Character.getNumericValue(chr);
                if (marked) {
                    mark_i++;
                }
            } else if (chr == DECIMAL_MARK) {
                if (marked) {
                    throw new CompileError("Unexpected symbol: " + chr);
                }
                marked = true;
            } else if (chr == IMAGINARY) {
                imaginary = true;
            } else {
                break;
            }
        }
        val /= Math.pow(10, mark_i);
        return new ExprToken((imaginary
                ? ExprToken.TYPE.NUM_IM
                : ExprToken.TYPE.NUM_RE),
                val);
    }

    private ExprToken identifier_token() {
        StringBuilder str = new StringBuilder();
        for (char chr; i < code.length(); i++) {
            chr = code.charAt(i);
            if (Character.isLetterOrDigit(chr)) {
                str.append(chr);
            } else {
                break;
            }
        }
        String val = str.toString();
        return new ExprToken((functions.containsKey(val)
                ? ExprToken.TYPE.FUNCTION
                : ExprToken.TYPE.IDENTIFIER), val);
    }

    public ExprToken next_token() throws CompileError {
        for (char chr; i < code.length(); i++) {
            chr = code.charAt(i);
            if (IGNORING.contains(chr)) {
                continue;
            } else if (OPERATORS.contains(chr)) {
                return operator_token();
            } else if (Character.isDigit(chr)) {
                return number_token();
            } else if (Character.isLetter(chr)) {
                return identifier_token();
            } else {
                throw new CompileError("Unexpected symbol: " + chr);
            }
        }
        return new ExprToken(ExprToken.TYPE.END, null);
    }
}
