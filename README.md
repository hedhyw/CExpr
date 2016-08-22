# CExpr 0.8
Math Complex Expression Compiler

# How to use?
Complete example: **/cexpr/CExpr.java**

```java
/**
  * 1 + 2i == Complex(1, 2i); 3 == Complex(3, 0)
  * -cos(z), i - invalid; -1*cos(z), 1i - valid
  * operators:
  *   +, -, *, /, %, ^
  * default functions:
  *   sqrt, exp, ln, log10
  *   sin, cos, tan, ctan
  *   asin, acos, atan
  *   arg, abs, con, inv, round, rnd, im, re
  * default constants:
  *   PI, E
  */
String code = "-1*cos(z)^2+(1+2i)*3";
Compiled compiled = compiler.compile(code); // can throw CompileError
compiled.put("z", new Complex(0.5, 2)); // puts custom variable `z`
// for custom functions, see: /cexpr/CExpr.java
Complex complex = compiled.execute(); // result here
System.out.printf("Result: " + complex.toString());
// Result: -4.877351+17.481837i
```
