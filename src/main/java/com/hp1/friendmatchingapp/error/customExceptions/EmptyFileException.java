package com.hp1.friendmatchingapp.error.customExceptions;

import com.hp1.friendmatchingapp.error.ErrorCode;

public class EmptyFileException extends RuntimeException{
    private ErrorCode errorCode;
    public EmptyFileException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
