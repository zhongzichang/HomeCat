package com.osgsquare.homecat.model.ws;

/**
 * Created by zzc on 9/12/14.
 */
public class WsMessage {

    public static final int CONTENT = 1;
    public static final int COMMAND = 2;

    private int type;
    private Object body;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
