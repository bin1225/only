package com.project.only.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PageErrorCode implements ErrorCode{

    LIMIT_TIME_PASSED(HttpStatus.BAD_REQUEST, "Limit time for update passed")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
