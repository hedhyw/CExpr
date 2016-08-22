# CExpr 0.1
Math Complex Expression Compiler

# How to use?
Complete example: **/cexpr/CExpr.java**

```java
// 1 + 2i == Complex(1, 2i); 3 == Complex(3, 0)
// -cos(z) - invalid; -1*cos(z) - valid
// operators: +, -, *, /, %, ^
// default functions: sin, cos, tan, ctan
// default constants: PI, E
String code = "-1*cos(z)^2+(1+2i)*3";
Compiled compiled = compiler.compile(code); // can throw CompileError
compiled.put("z", new Complex(0.5, 2)); // puts custom variable `z`
// for custom functions, see: /cexpr/CExpr.java
Complex complex = compiled.execute(); // result here
System.out.printf("Result: " + complex.toString());
// Result: -4.877351+17.481837i
```
