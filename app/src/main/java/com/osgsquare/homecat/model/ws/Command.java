package com.osgsquare.homecat.model.ws;

/**
 * Created by zzc on 9/12/14.
 */
public abstract class Command {

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
