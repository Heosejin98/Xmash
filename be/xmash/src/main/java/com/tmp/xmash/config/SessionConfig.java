package com.tmp.xmash.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

@Configuration
public class SessionConfig {


    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        // 원하는 도메인으로 설정
        serializer.setDomainName("localhost.com");

        // 쿠키 이름 설정 (기본은 JSESSIONID)
//        serializer.setCookieName("USER_TOKEN");

        return serializer;
    }
}