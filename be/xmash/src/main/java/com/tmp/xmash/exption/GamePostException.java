package com.tmp.xmash.exption;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class GamePostException extends RuntimeException {
    public GamePostException(String message) {
        super(message);
        log.error(message);
    }
}
