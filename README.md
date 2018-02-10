# CExpr 0.92
Math Complex Expression Compiler

# How to use?
Complete example: **CExpr/src/main/java/App.java**

```java
/**
  * priority (asc):
  *   +, -
  *   *, /, %
  *   unary+, unary-
  *   ^
  *   function
  *
  * default functions:
  *   sqrt, exp, ln, log10
  *   sin, cos, tan, ctan
  *   asin, acos, atan
  *   sinh, cosh, tanh, ctanh
  *   arg, abs, con, inv, round, rnd, im, re
  *
  * default constants:
  *   PI, E
  *
  * numbers:
  *   1+3i == Complex(1, 3)
  *   1    == Complex(1, 0)
  *
  * notice:
  *   2^-2 - invalid; 2^(-2) - valid
  *   -z^2 == -(z^2)
  */
String code = "-(cos(z)^2)+(1+2i)*3";
Compiled compiled = compiler.compile(code); // can throw CompileError
compiled.put("z", new Complex(0.5, 2)); // puts custom variable `z`
// for custom functions or constants, see: /cexpr/CExpr.java
Complex complex = compiled.execute(); // result here, can throw ExecuteError
System.out.printf("Result: " + complex.toString());
// Result: -4.877351+17.481837i
```
