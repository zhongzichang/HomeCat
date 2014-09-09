package com.osgsquare.homecat.model.ws;

/**
 * Created by zzc on 9/8/14.
 */
public class Message {

    public static final int TEXT = 1;
    public static final int IMAGE = 2;
    public static final int AUDIO = 3;

    private int type;
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
