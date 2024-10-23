package com.tmp.xmash.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class XmashTimeCreator {

    // UTC로 현재 시간을 LocalDateTime으로 반환하는 메서드
    public static LocalDateTime getCurrentTimeUTC() {
        return LocalDateTime.now(ZoneOffset.UTC); // 현재 UTC 시간을 LocalDateTime으로 반환
    }

}
