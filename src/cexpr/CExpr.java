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

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maxim Krivchun
 */
public class CExpr {

    /**
     * Example
     *
     * @param args the command line arguments
     *
     */
    public static void main(String[] args) {
        Compiler compiler = new Compiler();
        try {
            { // all numbers are complex
                String code = "-cos(z)^2+(1+2i)*3";
                Compiled compiled = compiler.compile(code);
                compiled.put("z", new Complex(0.5, 2));
                Complex complex = compiled.execute();
                System.out.printf("Example #1: " + complex.toString() + '\n');
                // Example #1: -4.877351+17.481837i
            }

            { // default functions
                /**
                 * Description:
                 *  im(2+3i)  = 0+3i
                 *  re(4+5i)  = 4+0i
                 *  inv(1+6i) = 6+1i
                 *  con(7+8i) = 7-8i
                 *  rnd(5+4i) = random()*5+random()*4
                 *  round(5.5+4.123i) = 6+4i
                 * 
                 * List:
                 *  sqrt, exp, ln, log10
                 *  sin, cos, tan, ctan
                 *  asin, acos, atan
                 *  sinh, cosh, tanh, ctanh
                 *  arg, abs, con, inv, round, rnd, im, re
                 */
                String code = "tan(atan(0.234+PI*1i))";
                Compiled compiled = compiler.compile(code);
                compiled.put("z", new Complex(0.5, 2));
                Complex complex = compiled.execute();
                System.out.printf("Example #2: " + complex.toString() + '\n');
                // Example #2: 0.234000+3.141593i
            }

            { // using constants
                // PI & E
                String code = "(PI+E^2)*2";
                Compiled compiled = compiler.compile(code);
                compiled.put("z", new Complex(0.5, 2));
                Complex complex = compiled.execute();
                System.out.printf("Example #3: " + complex.toString() + '\n');
                // Example #3: 21.061297505040883
            }

            { // with custom variables
                // compiled.put("NAME", (Complex) num)
                String code = "cos(z+1)";
                Compiled compiled = compiler.compile(code);
                compiled.put("z", new Complex(0.5, 2));
                Complex complex = compiled.execute();
                System.out.printf("Example #4: " + complex.toString() + '\n');
                // Example #4: 0.266127-3.617775i
            }

            { // with custom functions
                // functions.put("NAME", (Functions.function) listener)
                String code = "addPi(1i)";
                Functions functions = new Functions();
                functions.put("addPi", new Functions.function() {
                    @Override
                    public Complex eval(Complex arg) {
                        return ComplexUtils.add(ComplexUtils.PI, arg);
                    }
                });
                Compiled compiled = compiler.compile(code, functions);
                Complex complex = compiled.execute();
                System.out.printf("Example #5: " + complex.toString() + '\n');
                // Example #5: 3.141593+1.000000i
            }

            { // getting code
                // compiled.toString()
                String code = "cos(PI)+(2+1)*sin(E)";
                Functions functions = new Functions();
                Compiled compiled = compiler.compile(code, functions);
                System.out.printf("Example #6:\n" + compiled.toString());
                Complex complex = compiled.execute();
                System.out.printf(complex.toString() + '\n');
                /**
                 * Example #6:
                 * r1 := cos [PI]
                 * r2 := ADD 2.0, 1.0
                 * r3 := sin [E]
                 * r4 := MUL r2, r3
                 * r5 := ADD r1, r4
                 * 0.23234387150872648
                 */
            }
        } catch (CompileError ex) {
            Logger.getLogger(CExpr.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
