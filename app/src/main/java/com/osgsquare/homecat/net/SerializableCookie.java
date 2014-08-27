package com.osgsquare.homecat.net;

import org.apache.http.impl.cookie.BasicClientCookie;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhongzichang on 8/27/14.
 */
public class SerializableCookie implements Serializable {

    private static final String EXPIRES_KEY = "expires";

    private static final long serialVersionUID = 1L;

    private String domain;
    private String value;
    private String name;
    private String comment;
    private Date expiryDate;
    private String path;

    private String expiryDateString;

    public SerializableCookie(BasicClientCookie cookie){
        this.domain = cookie.getDomain();
        this.value =  cookie.getValue();
        this.name = cookie.getName();
        this.comment = cookie.getComment();
        this.expiryDate = cookie.getExpiryDate();
        this.path = cookie.getPath();
        this.expiryDateString = cookie.getAttribute(EXPIRES_KEY);
    }

    public BasicClientCookie toBasicClientCookie() {
        BasicClientCookie basicClientCookie = new BasicClientCookie(this.getName(), this.getValue());
        basicClientCookie.setDomain(this.getDomain());
        basicClientCookie.setComment(this.getComment());
        basicClientCookie.setExpiryDate(this.getExpiryDate());
        basicClientCookie.setPath(this.getPath());
        basicClientCookie.setAttribute(BasicClientCookie.DOMAIN_ATTR, this.getDomain());
        basicClientCookie.setAttribute(BasicClientCookie.PATH_ATTR, this.getPath());
        basicClientCookie.setAttribute(EXPIRES_KEY, this.getExpiryDateString());
        return basicClientCookie;
    }

    public String getDomain() {
        return domain;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public String getPath() {
        return path;
    }

    public String getExpiryDateString() {
        return expiryDateString;
    }

}
