package ru.hedhyw.cexpr;

import org.junit.Test;

import ru.hedhyw.cexpr.complex.ComplexUtils;
import ru.hedhyw.cexpr.complex.model.Complex;

import static org.junit.Assert.*;

public class CompilerTest {

    private static final double EPS = 1e-8;

    @Test public void testComplexNumber() {
        Compiler compiler = new Compiler();
        Compiled compiled = compiler.compile("2.4+3.3i");
        Complex res = compiled.execute();
        assertEquals(2.4, res.getReal(), EPS);
        assertEquals(3.3, res.getImaginary(), EPS);
    }

    @Test public void testOperators() {
        Compiler compiler = new Compiler();
        Compiled compiled = compiler.compile("10*22+10/(2^3)+11%10-1");
        Complex res = compiled.execute();
        assertEquals(10*22+10.0/(2*2*2)+11%10-1, res.getReal(), EPS);
        assertEquals(0, res.getImaginary(), EPS);

        compiled = compiler.compile("(2+2i)*4+(2+2i)^2-(16+16i)/2.0");
        res = compiled.execute();
        assertEquals(0, res.getReal(), EPS);
        assertEquals(8, res.getImaginary(), EPS);
    }

    @Test public void testTrigonometricFunctions() {
        Compiler compiler = new Compiler();
        Compiled compiled = compiler.compile("cos(acos(1+2i))");
        Complex result = compiled.execute();
        assertEquals(1.0, result.getReal(), EPS);
        assertEquals(2.0, result.getImaginary(), EPS);

        compiled = compiler.compile("sin(asin(1+2i))");
        result = compiled.execute();
        assertEquals(1.0, result.getReal(), EPS);
        assertEquals(2.0, result.getImaginary(), EPS);

        compiled = compiler.compile("tan(atan(1+2i))");
        result = compiled.execute();
        assertEquals(1.0, result.getReal(), EPS);
        assertEquals(2.0, result.getImaginary(), EPS);

        compiled = compiler.compile("1/ctan(1+2i)");
        result = compiled.execute();
        Complex expected = ComplexUtils.tan(new Complex(1.0, 2.0));
        assertEquals(expected.getReal(), result.getReal(), EPS);
        assertEquals(expected.getImaginary(), result.getImaginary(), EPS);
    }

}
