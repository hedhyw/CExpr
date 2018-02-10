package ru.hedhyw.cexpr;

public class Complex {

    final double re, im;

    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public boolean equals(Complex c){
        return (c.im == im && c.re == re);
    }

    @Override
    public String toString() {
        if (im == 0) {
            return String.valueOf(re);
        } else if (re == 0) {
            return String.valueOf(im) + 'i';
        } else {
            String sign = (im < 0 ? "" : "+");
            return String.format("%f%s%fi", re, sign, im);
        }
    }
}
