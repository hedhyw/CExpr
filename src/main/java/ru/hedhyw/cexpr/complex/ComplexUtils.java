package ru.hedhyw.cexpr.complex;

import ru.hedhyw.cexpr.complex.model.Complex;

public class ComplexUtils {

  public static final Complex ZERO = new Complex(0, 0);
  public static final Complex PI = new Complex(Math.PI, 0);
  public static final Complex E = new Complex(Math.E, 0);

  public static Complex add(Complex c1, Complex c2) {
    return new Complex(
      c1.getReal() + c2.getReal(),
      c1.getImaginary() + c2.getImaginary());
  }

  public static Complex sub(Complex c1, Complex c2) {
    return new Complex(
      c1.getReal() - c2.getReal(),
      c1.getImaginary() - c2.getImaginary());
  }

  public static Complex mul(Complex c1, Complex c2) {
    return new Complex(
      c1.getReal() * c2.getReal() - c1.getImaginary() * c2.getImaginary(),
      c1.getReal() * c2.getImaginary() + c1.getImaginary() * c2.getReal());
  }

  public static Complex div(Complex c1, Complex c2) {
    double a = c1.getReal();
    double b = c1.getImaginary();
    double c = c2.getReal();
    double d = c2.getImaginary();
    double denominator = c * c + d * d;
    return new Complex(
      (a * c + b * d) / denominator,
      (b * c - a * d) / denominator);
  }

  public static Complex sin(Complex c) {
    return new Complex(
      Math.sin(c.getReal()) * Math.cosh(c.getImaginary()),
      Math.cos(c.getReal()) * Math.sinh(c.getImaginary()));
  }

  public static Complex cos(Complex c) {
    return new Complex(
      Math.cos(c.getReal()) * Math.cosh(c.getImaginary()),
      -Math.sin(c.getReal()) * Math.sinh(c.getImaginary()));
  }

  public static Complex tan(Complex c) {
    return div(sin(c), cos(c));
  }

  public static Complex ctan(Complex c) {
    return div(cos(c), sin(c));
  }

  public static Complex acos(Complex c) {
    double a = Math.sqrt(Math.pow(1 + c.getReal(), 2) +
      c.getImaginary() * c.getImaginary());
    double b = Math.sqrt(Math.pow(1 - c.getReal(), 2) +
      c.getImaginary() * c.getImaginary());
    double B = (a + b) / 2.0;
    return new Complex(Math.acos((a - b) / 2.0),
        -Math.log(B + Math.sqrt(B * B - 1)));
  }

  public static Complex asin(Complex c) {
    double a = Math.sqrt(Math.pow(1 + c.getReal(), 2) +
      c.getImaginary() * c.getImaginary());
    double b = Math.sqrt(Math.pow(1 - c.getReal(), 2) +
      c.getImaginary() * c.getImaginary());
    double B = (a + b) / 2.0;
    return new Complex(Math.asin((a - b) / 2.0),
      Math.log(B + Math.sqrt(B * B - 1)));
  }

  public static Complex atan(Complex c) {
    double r2 = Math.pow(c.getReal(), 2);
    return new Complex(0.5 * Math.atan2(2 * c.getReal(),
      1 - r2 - Math.pow(c.getImaginary(), 2)),
      0.25 * Math.log((r2 + Math.pow(1 + c.getImaginary(), 2)) /
      (r2 + Math.pow(1 - c.getImaginary(), 2))));
  }

  public static Complex pow(Complex c, double num) {
    double phase = Math.atan2(c.getImaginary(), c.getReal());
    double z = Math.pow(abs(c), num);
    return new Complex(
      z * Math.cos(num * phase),
      z * Math.sin(num * phase));
  }

  public static Complex exp(Complex c) {
    double k = Math.exp(c.getReal());
    return new Complex(
      k * Math.cos(c.getImaginary()),
      k * Math.sin(c.getImaginary()));
  }

  public static Complex conjugate(Complex c) {
    return new Complex(c.getReal(), -c.getImaginary());
  }

  public static Complex ln(Complex c, double n) {
    return div(ln(c), new Complex(Math.log(n), 0));
  }

  public static Complex ln(Complex c) {
    return new Complex(Math.log(abs(c)), arg(c));
  }

  public static Complex log10(Complex c) {
    return ln(c, 10);
  }

  public static Complex sqrt(Complex c) {
    return pow(c, 0.5);
  }

  public static Complex round(Complex c) {
    return new Complex(Math.round(c.getReal()),
      Math.round(c.getImaginary()));
  }

  public static Complex random(Complex c) {
    return new Complex(
      Math.random() * c.getReal(),
      Math.random() * c.getImaginary());
  }

  public static Complex sinh(Complex c) {
    return new Complex(Math.sinh(c.getReal()) * Math.cos(c.getImaginary()),
      Math.cosh(c.getReal()) * Math.sin(c.getImaginary()));
  }

  public static Complex cosh(Complex c) {
    return new Complex(Math.cosh(c.getReal()) * Math.cos(c.getImaginary()),
      Math.sinh(c.getReal()) * Math.sin(c.getImaginary()));
  }

  public static Complex tanh(Complex c) {
    return div(sinh(c), cosh(c));
  }

  public static Complex ctanh(Complex c) {
    return div(cosh(c), sinh(c));
  }

  public static double abs(Complex c) {
    return Math.sqrt(
      c.getImaginary() * c.getImaginary() + c.getReal() * c.getReal());
  }

  public static Double arg(Complex c) {
    if (c.getReal() == 0) {
      return Math.PI / 2;
    }
    return Math.atan2(c.getImaginary(), c.getReal());
  }

  public static boolean equals(Complex c1, Complex c2) {
    return c1.equals(c2);
  }
}
