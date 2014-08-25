package com.osgsquare.homecat.net;

import org.springframework.http.ContentCodingType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * Created by zzc on 8/23/14.
 */
public class RestHelper {


    private static HttpHeaders defaultHttpHeaders = null;

    public static HttpHeaders getDefaultHttpHeaders(){
        if( defaultHttpHeaders == null) {
            HttpHeaders reqHeaders = new HttpHeaders();
            reqHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            reqHeaders.setAccept(Collections.singletonList(new MediaType("application", "json")));
            reqHeaders.setAcceptEncoding(ContentCodingType.GZIP);
            //reqHeaders.set("Cookie", cookie);
        }
        return defaultHttpHeaders;
    }

    public static RestTemplate createStatefulTemplate(){
        return new StatefullRestTemplate();
    }

    public static RestTemplate createTemplate(){
        return new RestTemplate();
    }

}
