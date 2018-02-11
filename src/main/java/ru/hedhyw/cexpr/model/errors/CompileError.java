package ru.hedhyw.cexpr.model.errors;

public class CompileError extends Error {

  static final long serialVersionUID = 19782;

  public CompileError(String msg) {
    super(msg);
  }
}
