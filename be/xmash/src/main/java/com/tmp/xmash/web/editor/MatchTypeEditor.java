package com.tmp.xmash.web.editor;

import java.beans.PropertyEditorSupport;

public class MatchTypeEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(text.toUpperCase());
    }

    @Override
    public String getAsText() {
        return ((String) getValue()).toLowerCase();
    }

}
