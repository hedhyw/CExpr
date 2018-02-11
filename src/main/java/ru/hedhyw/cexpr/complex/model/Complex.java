package ru.hedhyw.cexpr.complex.model;

public class Complex {

  public static final double EPS = 1e-8;
  private final double re, im;

  public Complex(double re, double im) {
    this.re = re;
    this.im = im;
  }

  public double getImaginary() {
    return im;
  }

  public double getReal() {
    return re;
  }

  public boolean equals(Complex c){
    double dIm = Math.abs(c.getImaginary() - im);
    double dRe = Math.abs(c.getReal() - re);
    return dIm < EPS && dRe < EPS;
  }

  public boolean hasReal() {
    return re != 0;
  }

  public boolean hasImaginary() {
    return im != 0;
  }

  @Override
  public String toString() {
    if (!hasImaginary()) {
      return String.valueOf(re);
    } else if (!hasReal()) {
      return String.valueOf(im) + 'i';
    } else {
      return String.format("%f%s%fi",
        re, (im < 0 ? "" : "+"), im);
    }
  }

}
