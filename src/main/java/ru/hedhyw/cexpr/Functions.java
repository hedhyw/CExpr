package ru.hedhyw.cexpr;

import java.util.HashMap;

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
