package com.hp1.friendmatchingapp.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 에러코드 정리
@AllArgsConstructor
@Getter
public enum ErrorCode {
    NOT_FOUND(404, "COMMON-ERR-404", "ID not found"),
    INTER_SERVER_ERROR(500, "COMMON-ERR-500", "Internal server error"),
    Method_Argument_Not_Valid(400, "COMMON-ERR-400", "Invalid method argument"),
    USER_NOT_FOUND(401, "COMMON-ERR-401", "User not found"),
    INVALID_PASSWORD(401, "COMMON-ERR-401", "Invalid password"),
    DUPLICATE_USER(409, "COMMON-ERR-409", "Username is already exists"),
    DUPLICATE_EMAIL(409, "COMMON-ERR-409", "This email is already registered. Please use a different email address."),
    INVALID_EMAIL_VERIFICATION_CODE(401, "COMMON-ERR-401", "Invaild email verification code."),
    EMAIL_CODE_TIMEOUT(410, "COMMON-ERR-410", "Email verification code expired"),
    EMAIL_VERIFICATION_REQUEST_NOT_FOUND(400, "COMMON-ERR-400","This email has not requested a verification code."),
    EMPTY_FILE(400, "COMMON-ERR-400", "File is empty."),
    MISSING_FILE_EXTENSION(400, "COMMON-ERR-400", "The file is missing an extension. Please provide a valid file with an extension."),
    INVALID_FILE_EXTENSION(400, "COMMON-ERR-400", "The file extension is not valid. Please upload a file with a valid extension (e.g., .jpg, .png, gif, jpeg)"),
    IO_EXCEPTION_ON_IMAGE_UPLOAD(500,"COMMON-ERR-500","An error occurred while uploading the image.");

    private int status;
    private String errorCode;
    private String message;
}
