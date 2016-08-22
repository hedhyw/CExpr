# CExpr
Math Expression Compiler

# How to use?
Complete example: **/cexpr/CExpr.java**

```java
String code = "cos(z)^2+(1+2)*3";
Compiled compiled = compiler.compile(code); // throws CompileError
compiled.put("z", new Complex(0.5, 2)); // custom variables
Complex complex = compiled.execute(); // result here
System.out.printf("Result: " + complex.toString());
// Result: 16.877351-11.481837i
```
