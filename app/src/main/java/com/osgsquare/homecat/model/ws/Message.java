package com.osgsquare.homecat.model.ws;

/**
 * Created by zzc on 9/8/14.
 */
public class Message {

    public static final String TEXT = "text";
    public static final String AUDIO = "audio";

    private int sourceId;
    private int targetId;
    private String type;
    private String content;

    public Message(){}

    public Message(int targetId, String type, String content) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
