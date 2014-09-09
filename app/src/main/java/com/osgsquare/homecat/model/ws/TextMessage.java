package com.osgsquare.homecat.model.ws;

/**
 * Created by zzc on 9/10/14.
 */
public class TextMessage extends Message {

    private String content;

    public TextMessage(int type, String content) {
        this.setType(type);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
