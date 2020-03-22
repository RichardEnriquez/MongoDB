package com.lucaxiang.exceptions;

public class ManagerException extends Exception {

    public ManagerException(ErrorCode code)
    {
        super(code.toString());
    }
    public ManagerException(String error)
    {
        super(error);
    }
}
