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

/**
 *
 * @author Maxim Krivchun
 */
public class ComplexUtils {

    public static final Complex ZERO = new Complex(0, 0);
    public static final Complex PI = new Complex(Math.PI, 0);
    public static final Complex E = new Complex(Math.E, 0);

    public static Complex add(Complex c1, Complex c2) {
        return new Complex(c1.re + c2.re, c1.im + c2.im);
    }

    public static Complex sub(Complex c1, Complex c2) {
        return new Complex(c1.re - c2.re, c1.im - c2.im);
    }

    public static Complex mul(Complex c1, Complex c2) {
        return new Complex(
                c1.re * c2.re - c1.im * c2.im,
                c1.re * c2.im + c1.im * c2.re);
    }

    public static Complex div(Complex c1, Complex c2) {
        double d = c2.re * c2.re + c2.im * c2.im;
        return new Complex((c1.re * c2.re + c1.im * c2.im) / d,
                (c1.im * c2.re - c1.re * c2.im) / d);
    }

    public static Complex sin(Complex c) {
        return new Complex(
                Math.sin(c.re) * Math.cosh(c.im),
                Math.cos(c.re) * Math.sinh(c.im));
    }

    public static Complex cos(Complex c) {
        return new Complex(
                Math.cos(c.re) * Math.cosh(c.im),
                -Math.sin(c.re) * Math.sinh(c.im));
    }

    public static Complex tan(Complex c) {
        return div(sin(c), cos(c));
    }

    public static Complex ctan(Complex c) {
        return div(cos(c), sin(c));
    }

    public static Complex acos(Complex c) {
        double a = Math.sqrt(Math.pow(1 + c.re, 2) + c.im * c.im);
        double b = Math.sqrt(Math.pow(1 - c.re, 2) + c.im * c.im);
        double B = (a + b) / 2.0;
        return new Complex(Math.acos((a - b) / 2.0),
                -Math.log(B + Math.sqrt(B * B - 1)));
    }

    public static Complex asin(Complex c) {
        double a = Math.sqrt(Math.pow(1 + c.re, 2) + c.im * c.im);
        double b = Math.sqrt(Math.pow(1 - c.re, 2) + c.im * c.im);
        double B = (a + b) / 2.0;
        return new Complex(Math.asin((a - b) / 2.0),
                Math.log(B + Math.sqrt(B * B - 1)));
    }

    public static Complex atan(Complex c) {
        double r2 = Math.pow(c.re, 2);
        return new Complex(0.5 * Math.atan2(2 * c.re, 1 - r2 - Math.pow(c.im, 2)),
                0.25 * Math.log((r2 + Math.pow(1 + c.im, 2)) / (r2 + Math.pow(1 - c.im, 2))));
    }

    public static Complex pow(Complex c, double num) {
        double phase = Math.atan2(c.im, c.re);
        double z = Math.pow(abs(c), num);
        return new Complex(
                z * Math.cos(num * phase),
                z * Math.sin(num * phase));
    }

    public static Complex exp(Complex c) {
        double k = Math.exp(c.re);
        return new Complex(k * Math.cos(c.im), k * Math.sin(c.im));
    }

    public static Complex conjugate(Complex c) {
        return new Complex(c.re, -c.im);
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
        return new Complex(Math.round(c.re),
                Math.round(c.im));
    }
    
    public static Complex random(Complex c) {
        return new Complex(Math.random(),Math.random());
    }

    public static double abs(Complex c) {
        return Math.sqrt(c.im * c.im + c.re * c.re);
    }

    public static Double arg(Complex c) {
        if (c.re == 0) {
            return Math.PI / 2;
        }
        return Math.atan2(c.im, c.re);
    }

    public static boolean equals(Complex c1, Complex c2) {
        return c1.equals(c2);
    }
}
