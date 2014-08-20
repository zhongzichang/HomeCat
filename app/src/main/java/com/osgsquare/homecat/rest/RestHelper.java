package com.osgsquare.homecat.rest;

import android.content.Context;
import android.content.SharedPreferences;

import org.springframework.http.ContentCodingType;
import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import java.util.Collections;


/**
 * Created by zhongzichang on 8/20/14.
 */
public class RestHelper {

    public  final String PREFS_PRIVATE_DATA = "private.data";
    public final String PREFS_KEY_USER_COOKIE = "cookie";

    public static HttpEntity NewEntity(RequestParams params) {

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        requestHeaders.setAccept(Collections.singletonList(new MediaType("application", "json")));
        requestHeaders.setAcceptEncoding(ContentCodingType.GZIP);
        requestHeaders.set("Cookie", params.cookie);
        HttpEntity requestEntity = new HttpEntity(requestHeaders);
        return requestEntity;
    }


}
