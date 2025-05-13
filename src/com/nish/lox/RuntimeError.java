package com.nish.lox;

class RuntimeError extends RuntimeException {
    final Token token;

    RuntimeError(Token token, String message) {
        super();
        this.token = token;
    }
}
