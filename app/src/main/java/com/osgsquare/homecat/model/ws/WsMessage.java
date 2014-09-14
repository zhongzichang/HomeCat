package com.osgsquare.homecat.model.ws;

/**
 * Created by zzc on 9/12/14.
 */
public class WsMessage {


    private String type;
    private Object body;

    public WsMessage(Object body) {
        this.body = body;
        this.type = body.getClass().getSimpleName();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
