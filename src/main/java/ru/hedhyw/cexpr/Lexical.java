package ru.hedhyw.cexpr;

import java.util.Arrays;
import java.util.List;

// Reverse Polish notation
public class Lexical {

    private final String code;
    private final Functions functions;
    private Constants constants;
    private int i; // position in code

    private static final List<Character> OPERATORS
            = Arrays.asList('(', ')', '^', '*', '/', '+', '-', '%');

    private static final List<Character> IGNORING
            = Arrays.asList('\n', '\t', ' ');

    private static final char DECIMAL_MARK = '.';
    private static final char IMAGINARY = 'i';

    public Lexical(String code, Functions functions, Constants constants) {
        this.code = code;
        this.functions = functions;
        this.constants = constants;
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
            if (Character.isLetterOrDigit(chr) || chr == '_') {
                str.append(chr);
            } else {
                break;
            }
        }
        String val = str.toString();
        if (constants.containsKey(val)){
            return new ExprToken(
                    ExprToken.TYPE.NUM_RE, constants.get(val));
        }
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
            } else if (Character.isLetter(chr)  || chr == '_') {
                return identifier_token();
            } else {
                throw new CompileError("Unexpected symbol: " + chr);
            }
        }
        return new ExprToken(ExprToken.TYPE.END, null);
    }
}
