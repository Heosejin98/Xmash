package com.tmp.xmash.exption;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class BadRequestException extends RuntimeException{
    public BadRequestException(String message) {
        super(message);
        log.error(message);
    }

    public BadRequestException() {
        super("입력 값을 확인하세요");
        log.error("입력 값을 확인하세요");
    }
}
