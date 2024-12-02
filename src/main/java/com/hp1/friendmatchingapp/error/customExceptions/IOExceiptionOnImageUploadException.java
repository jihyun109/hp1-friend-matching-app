package com.hp1.friendmatchingapp.error.customExceptions;

import com.hp1.friendmatchingapp.error.ErrorCode;

public class IOExceiptionOnImageUploadException extends RuntimeException{
    private ErrorCode errorCode;
    public IOExceiptionOnImageUploadException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
