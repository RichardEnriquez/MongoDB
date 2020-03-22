package com.lucaxiang.exceptions;

public enum ErrorCode {

    ERROR_1("Username is empty"),
    ERROR_2("Password is empty");

    String error = null;
    ErrorCode(String error)
    {
       this.error = error;
    }
    @Override
    public String toString() {
        return error;
    }
}
