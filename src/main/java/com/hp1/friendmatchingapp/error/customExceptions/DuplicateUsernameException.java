package com.hp1.friendmatchingapp.error.customExceptions;

import com.hp1.friendmatchingapp.error.ErrorCode;
import lombok.Getter;

@Getter
public class DuplicateUsernameException extends RuntimeException{
    private ErrorCode errorCode;
    public DuplicateUsernameException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}