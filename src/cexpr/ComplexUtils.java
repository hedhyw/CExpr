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

    public static double abs(Complex c) {
        return Math.sqrt(c.im * c.im + c.re * c.re);
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

    public static Complex pow(Complex c, double num) {
        double phase = Math.atan2(c.im, c.re);
        double z = Math.pow(abs(c), num);
        return new Complex(
                z * Math.cos(num * phase),
                z * Math.sin(num * phase));
    }
}
