package ru.hedhyw.cexpr;

import java.util.Arrays;
import java.util.List;

import ru.hedhyw.cexpr.functions.factory.IFunctionFactory;
import ru.hedhyw.cexpr.model.errors.CompileError;
import ru.hedhyw.cexpr.model.Constants;
import ru.hedhyw.cexpr.model.token.*;

// Reverse Polish notation
public class Lexical {

    private final String code;
    private final IFunctionFactory functionsFactory;
    private Constants constants;
    private int i; // position in code

    private static final List<Character> OPERATORS
            = Arrays.asList('(', ')', '^', '*', '/', '+', '-', '%');

    private static final List<Character> IGNORING
            = Arrays.asList('\n', '\t', ' ');

    private static final char DECIMAL_MARK = '.';
    private static final char IMAGINARY = 'i';

    public Lexical(
        String code,
        IFunctionFactory functionsFactory,
        Constants constants) {
            this.code = code;
            this.functionsFactory = functionsFactory;
            this.constants = constants;
            i = 0;
    }

    private IToken operatorToken() {
        return new OperatorToken(code.charAt(i++));
    }

    private IToken numberToken() throws CompileError {
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
        if (imaginary) return new ImaginaryNumberToken(val);
        return new RealNumberToken(val);
    }

    private IToken identifierToken() {
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
            return new RealNumberToken(constants.get(val));
        }
        if (functionsFactory.hasFunction(val)) return new FunctionToken(val);
        return new IdentifierToken(val);
    }

    public IToken nextToken() throws CompileError {
        for (char chr; i < code.length(); i++) {
            chr = code.charAt(i);
            if (IGNORING.contains(chr)) {
                continue;
            } else if (OPERATORS.contains(chr)) {
                return operatorToken();
            } else if (Character.isDigit(chr)) {
                return numberToken();
            } else if (Character.isLetter(chr)  || chr == '_') {
                return identifierToken();
            } else {
                throw new CompileError("Unexpected symbol: " + chr);
            }
        }
        return new EndToken();
    }
}
