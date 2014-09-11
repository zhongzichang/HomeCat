package com.osgsquare.homecat.model.ws;

/**
 * Created by zzc on 9/12/14.
 */
public class ContentMessage {

    private int targetType;
    private int targetId;
    private Content content;

    public int getTargetType() {
        return targetType;
    }

    public void setTargetType(int targetType) {
        this.targetType = targetType;
    }

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
}
