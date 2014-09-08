package com.osgsquare.homecat.model.ws;

import java.util.Arrays;

/**
 * Created by zzc on 9/8/14.
 */
public class Content {

    private int type;
    private byte[] data;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Message{" +
                ", type=" + type +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
