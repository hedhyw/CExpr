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

import java.util.HashMap;

/**
 *
 * @author Maxim Krivchun
 */
public class Functions extends HashMap<String, Functions.function> {

    public interface function {

        public Complex eval(Complex arg);
    }

    public Functions() {
        put("sin", new function() {
            @Override
            public Complex eval(Complex arg) {
                return ComplexUtils.sin(arg);
            }

        });
        put("cos", new function() {
            @Override
            public Complex eval(Complex arg) {
                return ComplexUtils.cos(arg);
            }

        });
        put("tan", new function() {
            @Override
            public Complex eval(Complex arg) {
                return ComplexUtils.tan(arg);
            }

        });
        put("ctan", new function() {
            @Override
            public Complex eval(Complex arg) {
                return ComplexUtils.ctan(arg);
            }

        });
        put("re", new function() {
            @Override
            public Complex eval(Complex arg) {
                return new Complex(arg.re, 0);
            }

        });
        put("im", new function() {
            @Override
            public Complex eval(Complex arg) {
                return new Complex(arg.im, 0);
            }

        });
        put("abs", new function() {
            @Override
            public Complex eval(Complex arg) {
                return new Complex(ComplexUtils.abs(arg), 0);
            }
        });
        put("con", new function() {
            @Override
            public Complex eval(Complex arg) {
                return ComplexUtils.conjugate(arg);
            }
        });
        put("asin", new function() {
            @Override
            public Complex eval(Complex arg) {
                return ComplexUtils.asin(arg);
            }
        });
        put("acos", new function() {
            @Override
            public Complex eval(Complex arg) {
                return ComplexUtils.acos(arg);
            }
        });
        put("sqrt", new function() {
            @Override
            public Complex eval(Complex arg) {
                return ComplexUtils.sqrt(arg);
            }
        });
        put("atan", new function() {
            @Override
            public Complex eval(Complex arg) {
                return ComplexUtils.atan(arg);
            }
        });
        put("exp", new function() {
            @Override
            public Complex eval(Complex arg) {
                return ComplexUtils.exp(arg);
            }
        });
        put("ln", new function() {
            @Override
            public Complex eval(Complex arg) {
                return ComplexUtils.ln(arg);
            }
        });
        put("log10", new function() {
            @Override
            public Complex eval(Complex arg) {
                return ComplexUtils.log10(arg);
            }
        });
        put("arg", new function() {
            @Override
            public Complex eval(Complex arg) {
                return new Complex(ComplexUtils.arg(arg), 0);
            }
        });
        put("rnd", new function() {
            @Override
            public Complex eval(Complex arg) {
                Complex rnd = ComplexUtils.random(arg);
                return new Complex(rnd.re * arg.re, rnd.im * arg.im);
            }
        });
        put("round", new function() {
            @Override
            public Complex eval(Complex arg) {
                return ComplexUtils.round(arg);
            }
        });
        put("inv", new function() {
            @Override
            public Complex eval(Complex arg) {
                return new Complex(arg.im, arg.re);
            }
        });
        put("sinh", new function() {
            @Override
            public Complex eval(Complex arg) {
                return ComplexUtils.sinh(arg);
            }
        });
        put("cosh", new function() {
            @Override
            public Complex eval(Complex arg) {
                return ComplexUtils.cosh(arg);
            }
        });
        put("tanh", new function() {
            @Override
            public Complex eval(Complex arg) {
                return ComplexUtils.tanh(arg);
            }
        });
        put("ctanh", new function() {
            @Override
            public Complex eval(Complex arg) {
                return ComplexUtils.ctanh(arg);
            }
        });
    }

}
