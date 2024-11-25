package com.hp1.friendmatchingapp.error.customExceptions;

import com.hp1.friendmatchingapp.error.ErrorCode;
import lombok.Getter;

@Getter
public class InvalidPasswordException extends RuntimeException{
    private ErrorCode errorCode;
    public InvalidPasswordException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
