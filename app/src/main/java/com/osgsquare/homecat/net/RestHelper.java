package com.osgsquare.homecat.net;

import org.springframework.http.ContentCodingType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * Created by zzc on 8/23/14.
 */
public class RestHelper {


    private static HttpHeaders defaultHttpHeaders = null;

    public static HttpHeaders getDefaultHttpHeaders() {
        if (defaultHttpHeaders == null) {
            HttpHeaders reqHeaders = new HttpHeaders();
            reqHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            reqHeaders.setAccept(Collections.singletonList(new MediaType("application", "json")));
            reqHeaders.setAcceptEncoding(ContentCodingType.GZIP);
        }
        return defaultHttpHeaders;
    }

    public static HttpEntity createEntity(){
        return new HttpEntity(RestHelper.getDefaultHttpHeaders());
    }

    public static HttpEntity createEntity(MultiValueMap body){
        return new HttpEntity(body, RestHelper.getDefaultHttpHeaders());
    }


    public static RestTemplate createTemplate() {
        return new RestTemplate();
    }

    public static boolean isError(HttpStatus status) {
        HttpStatus.Series series = status.series();
        return (HttpStatus.Series.CLIENT_ERROR.equals(series)
                || HttpStatus.Series.SERVER_ERROR.equals(series));
    }


}
