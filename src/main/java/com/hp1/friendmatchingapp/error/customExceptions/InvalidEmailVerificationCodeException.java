package com.hp1.friendmatchingapp.error.customExceptions;

import com.hp1.friendmatchingapp.error.ErrorCode;
import lombok.Getter;

@Getter
public class InvalidEmailVerificationCodeException extends RuntimeException {
    private ErrorCode errorCode;
    public InvalidEmailVerificationCodeException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
