package com.osgsquare.homecat.model.ws;

/**
 * Created by zzc on 9/8/14.
 */
public class Message {

    public static final int TEXT = 1;
    public static final int AUDIO = 2;

    private int sourceId;
    private int targetId;
    private int type;
    private String content;

    public Message(){}

    public Message(int targetId, int type, String content) {
        this.targetId = targetId;
        this.type = type;
        this.content = content;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
