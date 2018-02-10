package ru.hedhyw.cexpr.model.errors;

public class ExecuteError extends Error {

    static final long serialVersionUID = 19783;

    public ExecuteError(String msg) {
        super(msg);
    }
}
