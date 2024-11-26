package com.tmp.xmash.web.editor;

import java.beans.PropertyEditorSupport;

public class MatchTypeEditor extends PropertyEditorSupport {

    /**
     * Request에서 사용됨: 클라이언트로부터 받은 문자열을 대문자로 변환하여 설정
     * "single"이라는 문자열이 들어오면 "SINGLE"로 변환됩니다.
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(text.toUpperCase());
    }
}
