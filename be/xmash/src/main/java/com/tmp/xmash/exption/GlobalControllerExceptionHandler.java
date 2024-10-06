package com.tmp.xmash.exption;

import com.tmp.xmash.dto.response.GlobalExceptionResponse;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.BadRequestException;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Hidden    // Swagger에서 제외(공통 예외처리는 API 문서에 노출할 필요가 없음)
@RestControllerAdvice
@Log4j2
public class GlobalControllerExceptionHandler {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    private GlobalExceptionResponse handleBadRequestException(BadRequestException e) {
        log.error("Bad Request Exception : {}", e.getMessage());
        return new GlobalExceptionResponse(e.getMessage());
    }


    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    private GlobalExceptionResponse handleAuthenticationException(AuthenticationException e) {
        log.error("Bad Request Exception : {}", e.getMessage());
        return new GlobalExceptionResponse(e.getMessage());
    }

}
