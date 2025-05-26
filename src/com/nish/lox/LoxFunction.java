package com.nish.lox;

import java.util.List;

class LoxFunction implements LoxCallable {
    private final Stmt.Function declaration;
    private final Environment closure;
    private final boolean isInit;

    LoxFunction(Stmt.Function declaration, Environment closure, Boolean isInit) {
        this.declaration = declaration;
        this.closure = closure;
        this.isInit = isInit;
    }

    public Object call(Interpreter interpreter, List<Object> arguments) {
        Environment environment = new Environment(closure);

        // map parameters with argument values
        for (int i = 0; i < declaration.params.size(); i++) {
            environment.define(declaration.params.get(i).lexeme, arguments.get(i));
        }
        try {
            interpreter.executeBlock(declaration.body, environment);
        } catch (Return returvnalue) {
            if (isInit)
                return closure.getAt(0, "this");

            return returvnalue.value;
        }

        if (isInit)
            return closure.getAt(0, "this");

        return null;
    }

    LoxFunction bind(LoxInstance instance) {
        Environment environment = new Environment(closure);
        environment.define("this", instance);
        return new LoxFunction(declaration, environment, isInit);
    }

    @Override
    public int arity() {
        return declaration.params.size();
    }

    @Override
    public String toString() {
        return "<fn " + declaration.name.lexeme + ">";
    }
}
