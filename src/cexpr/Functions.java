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

        public boolean optimize();

        public Complex eval(Complex arg);
    }

    public Functions() {
        put("sin", new function() {
            @Override
            public Complex eval(Complex arg) {
                return ComplexUtils.sin(arg);
            }

            @Override
            public boolean optimize() {
                return true;
            }

        });
        put("cos", new function() {
            @Override
            public Complex eval(Complex arg) {
                return ComplexUtils.cos(arg);
            }

            @Override
            public boolean optimize() {
                return true;
            }

        });
        put("tan", new function() {
            @Override
            public Complex eval(Complex arg) {
                return ComplexUtils.tan(arg);
            }

            @Override
            public boolean optimize() {
                return true;
            }

        });
        put("ctan", new function() {
            @Override
            public Complex eval(Complex arg) {
                return ComplexUtils.ctan(arg);
            }

            @Override
            public boolean optimize() {
                return true;
            }

        });
        put("re", new function() {
            @Override
            public Complex eval(Complex arg) {
                return new Complex(arg.re, 0);
            }

            @Override
            public boolean optimize() {
                return true;
            }

        });
        put("im", new function() {
            @Override
            public Complex eval(Complex arg) {
                return new Complex(arg.im, 0);
            }

            @Override
            public boolean optimize() {
                return true;
            }

        });
        put("abs", new function() {
            @Override
            public Complex eval(Complex arg) {
                return new Complex(ComplexUtils.abs(arg), 0);
            }

            @Override
            public boolean optimize() {
                return true;
            }
        });
        put("con", new function() {
            @Override
            public Complex eval(Complex arg) {
                return ComplexUtils.conjugate(arg);
            }

            @Override
            public boolean optimize() {
                return true;
            }
        });
        put("asin", new function() {
            @Override
            public Complex eval(Complex arg) {
                return ComplexUtils.asin(arg);
            }

            @Override
            public boolean optimize() {
                return true;
            }
        });
        put("acos", new function() {
            @Override
            public Complex eval(Complex arg) {
                return ComplexUtils.acos(arg);
            }

            @Override
            public boolean optimize() {
                return true;
            }
        });
        put("sqrt", new function() {
            @Override
            public Complex eval(Complex arg) {
                return ComplexUtils.sqrt(arg);
            }

            @Override
            public boolean optimize() {
                return true;
            }
        });
        put("atan", new function() {
            @Override
            public Complex eval(Complex arg) {
                return ComplexUtils.atan(arg);
            }

            @Override
            public boolean optimize() {
                return true;
            }
        });
        put("exp", new function() {
            @Override
            public Complex eval(Complex arg) {
                return ComplexUtils.exp(arg);
            }

            @Override
            public boolean optimize() {
                return true;
            }
        });
        put("ln", new function() {
            @Override
            public Complex eval(Complex arg) {
                return ComplexUtils.ln(arg);
            }

            @Override
            public boolean optimize() {
                return true;
            }
        });
        put("log10", new function() {
            @Override
            public Complex eval(Complex arg) {
                return ComplexUtils.log10(arg);
            }

            @Override
            public boolean optimize() {
                return true;
            }
        });
        put("arg", new function() {
            @Override
            public Complex eval(Complex arg) {
                return new Complex(ComplexUtils.arg(arg), 0);
            }

            @Override
            public boolean optimize() {
                return true;
            }
        });
        put("rnd", new function() {
            @Override
            public Complex eval(Complex arg) {
                Complex rnd = ComplexUtils.random(arg);
                return new Complex(rnd.re * arg.re, rnd.im * arg.im);
            }

            @Override
            public boolean optimize() {
                return false;
            }
        });
        put("round", new function() {
            @Override
            public Complex eval(Complex arg) {
                return ComplexUtils.round(arg);
            }

            @Override
            public boolean optimize() {
                return true;
            }
        });
        put("inv", new function() {
            @Override
            public Complex eval(Complex arg) {
                return new Complex(arg.im, arg.re);
            }

            @Override
            public boolean optimize() {
                return true;
            }
        });
        put("sinh", new function() {
            @Override
            public Complex eval(Complex arg) {
                return ComplexUtils.sinh(arg);
            }

            @Override
            public boolean optimize() {
                return true;
            }
        });
        put("cosh", new function() {
            @Override
            public Complex eval(Complex arg) {
                return ComplexUtils.cosh(arg);
            }

            @Override
            public boolean optimize() {
                return true;
            }
        });
        put("tanh", new function() {
            @Override
            public Complex eval(Complex arg) {
                return ComplexUtils.tanh(arg);
            }

            @Override
            public boolean optimize() {
                return true;
            }
        });
        put("ctanh", new function() {
            @Override
            public Complex eval(Complex arg) {
                return ComplexUtils.ctanh(arg);
            }

            @Override
            public boolean optimize() {
                return true;
            }
        });
        put("set", new function() {
            @Override
            public Complex eval(Complex arg) {
                return arg;
            }

            @Override
            public boolean optimize() {
                return false;
            }
        });
    }

}
