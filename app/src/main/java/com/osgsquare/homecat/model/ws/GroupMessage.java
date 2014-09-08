package com.osgsquare.homecat.model.ws;

/**
 * Created by zzc on 9/8/14.
 */
public class GroupMessage {

    private int groupId;
    private Content content;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
}
