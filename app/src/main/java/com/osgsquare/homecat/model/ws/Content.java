package com.osgsquare.homecat.model.ws;

/**
 * Created by zzc on 9/8/14.
 */
public class Content {

    public static final int TEXT = 1;
    public static final int IMAGE = 2;
    public static final int AUDIO = 3;

    private int type;
    private String data;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Message{" +
                ", type=" + type +
                ", data=" + data +
                '}';
    }
}
