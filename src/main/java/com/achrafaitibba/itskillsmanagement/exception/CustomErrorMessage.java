package com.achrafaitibba.itskillsmanagement.exception;

public enum CustomErrorMessage {
    ACCOUNT_ALREADY_EXIST("The username you provided already used"),
    ACCOUNT_NOT_EXIST("The username you provided doesn't exist"),
    PASSWORD_INCORRECT("The password you entered is incorrect"),
    ;
    private final String msg;
    CustomErrorMessage(String msg){
        this.msg = msg;
    }
    public String getMessage(){
        return this.msg;
    }
}
