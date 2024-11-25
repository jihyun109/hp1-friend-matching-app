package com.hp1.friendmatchingapp.error.customExceptions;

import com.hp1.friendmatchingapp.error.ErrorCode;

public class EmailVerificationRequestNotFoundException extends RuntimeException{
    private ErrorCode errorCode;
    public EmailVerificationRequestNotFoundException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
