package com.osgsquare.homecat.rest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

/**
 * Created by zhongzichang on 8/20/14.
 */
public class RequestParams {

    public String url;
    public HttpMethod method;
    public HttpEntity entity;
    public String cookie;
}
