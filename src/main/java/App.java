
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.hedhyw.cexpr.Compiled;
import ru.hedhyw.cexpr.Compiler;
import ru.hedhyw.cexpr.model.Constants;
import ru.hedhyw.cexpr.complex.ComplexUtils;
import ru.hedhyw.cexpr.complex.model.Complex;
import ru.hedhyw.cexpr.functions.factory.FunctionsFactory;
import ru.hedhyw.cexpr.functions.factory.IFunctionFactory;
import ru.hedhyw.cexpr.functions.model.IFunction;
import ru.hedhyw.cexpr.model.errors.CompileError;

public class App {

  public static void main(String[] args) {
    try {
      Compiler compiler = new Compiler();

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
        IFunctionFactory functionFactory = new FunctionsFactory();
        IFunction myFunction = new IFunction(){

          @Override
          public boolean isOptimizable() {
            return false;
          }

          @Override
          public String getName() {
            return "addPi";
          }

          @Override
          public Complex eval(Complex arg) {
            return ComplexUtils.add(ComplexUtils.PI, arg);
          }

        };
        functionFactory.addFunction(myFunction);
        compiler.setFunctionsFactory(functionFactory);
        Compiled compiled = compiler.compile(code);
        Complex complex = compiled.execute();
        System.out.printf("Example #5: " + complex.toString() + '\n');
        // Example #5: 3.141593+1.000000i
      }

      { // getting code
        // compiled.toString()
        String code = "cos(PI)+2*(cos(z)+(z+1))-sin(E)";
        Compiled compiled = compiler.compile(code);
        System.out.printf("Example #6:\n" + compiled.toString());
        compiled.put("z", new Complex(0.5, 2));
        Complex complex = compiled.execute();
        System.out.printf(complex.toString() + '\n');
        /**
         * Example #6:
         * r2 := COS z
         * r3 := ADD z, 1.0
         * r5 := ADD r2, r3
         * r6 := MUL 2.0, r5
         * r7 := ADD -1.0, r6
         * r8 := SUB r7, 0.41078129050290885
         * 8.192493+0.522381i
         */
      }
    } catch (CompileError ex) {
      Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}