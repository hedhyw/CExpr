package ru.hedhyw.cexpr;

import org.junit.Test;

import ru.hedhyw.cexpr.complex.ComplexUtils;
import ru.hedhyw.cexpr.complex.model.Complex;

import static org.junit.Assert.*;

import java.util.HashSet;

public class CompilerTest {

  private static final double EPS = 1e-8;
  private static final int TEST_RND_ITERATIONS_COUNT = 100;
  private static final double UNIQUE_RND = 0.9; // at least 90% of elements are unique

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

  @Test public void testNumberFunctions() {
    Compiler compiler = new Compiler();
    Compiled compiled = compiler.compile("abs(2+3i)");
    Complex res = compiled.execute();
    assertEquals(Math.sqrt(2 * 2 + 3 * 3), res.getReal(), EPS);
    assertEquals(0, res.getImaginary(), EPS);

    compiled = compiler.compile("arg(3+3i)");
    res = compiled.execute();
    assertEquals(Math.PI / 4.0, res.getReal(), EPS);
    assertEquals(0, res.getImaginary(), EPS);

    compiled = compiler.compile("con(3+3i)");
    res = compiled.execute();
    assertEquals(3, res.getReal(), EPS);
    assertEquals(-3, res.getImaginary(), EPS);

    compiled = compiler.compile("Im(3+4i)");
    res = compiled.execute();
    assertEquals(4, res.getReal(), EPS);
    assertEquals(0, res.getImaginary(), EPS);

    compiled = compiler.compile("Inv(4+5i)");
    res = compiled.execute();
    assertEquals(5, res.getReal(), EPS);
    assertEquals(4, res.getImaginary(), EPS);

    compiled = compiler.compile("Re(3+4i)");
    res = compiled.execute();
    assertEquals(3, res.getReal(), EPS);
    assertEquals(0, res.getImaginary(), EPS);

    compiled = compiler.compile("Round(2.5+2.3i)");
    res = compiled.execute();
    assertEquals(3, res.getReal(), EPS);
    assertEquals(2, res.getImaginary(), EPS);
  }

  @Test public void testSpecialFunctions() {
    Compiler compiler = new Compiler();
    Compiled compiled = compiler.compile("set(1.1+2.0i)");
    Complex result = compiled.execute();
    assertEquals(1.1, result.getReal(), EPS);
    assertEquals(2.0, result.getImaginary(), EPS);

    compiled = compiler.compile("rnd(10-5i)");
    HashSet<Double> rePart = new HashSet<Double>();
    HashSet<Double> imPart = new HashSet<Double>();
    for (int i = 0; i < TEST_RND_ITERATIONS_COUNT; ++i) {
      result = compiled.execute();
      assertTrue(result.getReal() <= 10);
      assertTrue(result.getReal() >= 0);
      assertTrue(result.getImaginary() >= -5);
      assertTrue(result.getImaginary() <= 0);
      rePart.add(result.getReal());
      imPart.add(result.getImaginary());
    }
    assertTrue(rePart.size() > TEST_RND_ITERATIONS_COUNT * UNIQUE_RND);
    assertTrue(imPart.size() > TEST_RND_ITERATIONS_COUNT * UNIQUE_RND);
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
