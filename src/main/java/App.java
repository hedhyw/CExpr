
import java.util.logging.Level;
import java.util.logging.Logger;

import ru.hedhyw.cexpr.CompileError;
import ru.hedhyw.cexpr.Compiled;
import ru.hedhyw.cexpr.Compiler;
import ru.hedhyw.cexpr.Complex;
import ru.hedhyw.cexpr.ComplexUtils;
import ru.hedhyw.cexpr.Constants;
import ru.hedhyw.cexpr.Functions;

public class App {
    public String getGreeting() {
        return "Hello world.";
    }

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
                String code = "tan(atan(0.234+inv(PI)))";
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

            { // with custom variables & constants
                // compiled.put("NAME", (Complex) num)
                String code = "cos(z+one)";

                Constants constants = new Constants();
                constants.put("one", 1.0); // put constant
                compiler.setConstants(constants);

                Compiled compiled = compiler.compile(code);
                compiled.put("z", new Complex(0.5, 2)); // put variable
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

                    @Override
                    public boolean optimize() {
                        return true;
                    }
                });
                compiler.setFunctions(functions);
                Compiled compiled = compiler.compile(code);
                Complex complex = compiled.execute();
                System.out.printf("Example #5: " + complex.toString() + '\n');
                // Example #5: 3.141593+1.000000i
            }

            { // getting code
                // compiled.toString()
                String code = "cos(PI)+cos(z)+(z+1)*sin(E)";
                Compiled compiled = compiler.compile(code);
                System.out.printf("Example #6:\n" + compiled.toString());
                compiled.put("z", new Complex(0.5, 2));
                Complex complex = compiled.execute();
                System.out.printf(complex.toString() + '\n');
                /**
                 * Example #6:
                 * r2 := cos [z]
                 * r3 := ADD [z], 1.0
                 * r5 := ADD -1.0, r2
                 * r6 := MUL r3, 0.41078129050290885
                 * r7 := ADD r5, r6
                 * 2.917809-0.917247i
                 */
            }
        } catch (CompileError ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}