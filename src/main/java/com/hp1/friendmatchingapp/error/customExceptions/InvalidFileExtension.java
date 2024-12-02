package com.hp1.friendmatchingapp.error.customExceptions;

import com.hp1.friendmatchingapp.error.ErrorCode;

public class InvalidFileExtension extends RuntimeException{
    private ErrorCode errorCode;
    public InvalidFileExtension(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
