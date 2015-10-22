package com.borba.biblioteca_furb;

public class LoginInvalidoException extends Exception {

    public LoginInvalidoException() {
        super();
    }

    public LoginInvalidoException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public LoginInvalidoException(String detailMessage) {
        super(detailMessage);
    }

    public LoginInvalidoException(Throwable throwable) {
        super(throwable);
    }

    private static final long serialVersionUID = 1L;
}
