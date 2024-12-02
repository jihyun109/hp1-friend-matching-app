package com.hp1.friendmatchingapp.error.customExceptions;

import com.hp1.friendmatchingapp.error.ErrorCode;

public class MissingFileExtensionException extends RuntimeException{
    private ErrorCode errorCode;
    public MissingFileExtensionException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
