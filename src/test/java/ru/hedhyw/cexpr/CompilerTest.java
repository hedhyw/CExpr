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
        assertEquals(compiled.execute().getReal(), 2.4, EPS);
        assertEquals(compiled.execute().getImaginary(), 3.3, EPS);
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
