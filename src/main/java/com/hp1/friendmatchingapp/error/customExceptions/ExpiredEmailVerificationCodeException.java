package com.hp1.friendmatchingapp.error.customExceptions;

import com.hp1.friendmatchingapp.error.ErrorCode;

public class ExpiredEmailVerificationCodeException extends RuntimeException{
    private ErrorCode errorCode;
    public ExpiredEmailVerificationCodeException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
