package com.osgsquare.homecat.net;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.springframework.http.ContentCodingType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.EOFException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Collections;
import java.util.List;

import roboguice.util.Ln;

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

    public static RestTemplate createStatefulTemplate() {
        RestTemplate restTemplate = new StatefullRestTemplate();
        restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        return restTemplate;
    }

    public static RestTemplate createTemplate() {
        return new RestTemplate();
    }

    public static boolean isError(HttpStatus status) {
        HttpStatus.Series series = status.series();
        return (HttpStatus.Series.CLIENT_ERROR.equals(series)
                || HttpStatus.Series.SERVER_ERROR.equals(series));
    }

    public static void storeCookies(CookieStore cs, ObjectOutput output) throws Exception {

        List<Cookie> cookies = cs.getCookies();
        for(Cookie cookie : cookies) {
            Ln.i("Store cookie === " + cookie.getName() + ":" + cookie.getValue());
            output.writeObject(new SerializableCookie((BasicClientCookie)cookie));
        }
    }

    public static void restoreCookies(CookieStore cs, ObjectInput input) throws Exception {

        try {
            SerializableCookie cookie = null;
            while ((cookie = (SerializableCookie) input.readObject()) != null) {
                Ln.i("Restore cookie === " + cookie.getName() + ":" + cookie.getValue());
                cs.addCookie((cookie.toBasicClientCookie()));
            }
        } catch(EOFException e){
            // ignore
        }
    }

}
