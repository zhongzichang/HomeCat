package com.osgsquare.homecat.agents.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.osgsquare.homecat.Config;
import com.osgsquare.homecat.agents.IAuthAgent;
import com.osgsquare.homecat.model.AuthCheckResult;
import com.osgsquare.homecat.model.Result;
import com.osgsquare.homecat.net.RestErrorResource;
import com.osgsquare.homecat.net.RestHelper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import roboguice.util.Ln;

/**
 * Created by zhongzichang on 8/21/14.
 */
public class AuthAgent implements IAuthAgent {

    @Inject
    RestTemplate restTemplate;
    @Inject
    ObjectMapper objectMapper;

    @Override
    public boolean check() throws Exception {

        final String url = Config.BASE_URL + "/check";
        HttpEntity reqEntity = RestHelper.createEntity();
        //RestTemplate restTemplate = RestHelper.createStatefulTemplate();

        try {
            ResponseEntity<AuthCheckResult> response = restTemplate.exchange(url, HttpMethod.GET, reqEntity, AuthCheckResult.class);
            AuthCheckResult result = response.getBody();
            return result.logined;
        } catch (HttpStatusCodeException e){
            String errorPayload = e.getResponseBodyAsString();
            RestErrorResource error = objectMapper.readValue(errorPayload, RestErrorResource.class);
            throw new RestClientException("[" + error.getCode() + "] " + error.getMessage());
        }

    }

    @Override
    public boolean login(String mobile, String password) throws Exception {

        final String url = Config.BASE_URL + "/login";

        //create the request body
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        body.add("mobile", mobile);
        body.add("password", password);
        Ln.d(this, "mobile:"+mobile+";password:" + password);
        HttpEntity reqEntity = RestHelper.createEntity(body);

        //RestTemplate restTemplate = RestHelper.createStatefulTemplate();

        try {
            ResponseEntity<Result> response = restTemplate.exchange(url, HttpMethod.POST, reqEntity, Result.class);
            Result result = response.getBody();
            return result.success;
        } catch (HttpStatusCodeException e){
            String errorPayload = e.getResponseBodyAsString();
            RestErrorResource error = objectMapper.readValue(errorPayload, RestErrorResource.class);
            throw new RestClientException("[" + error.getCode() + "] " + error.getMessage());
        }
    }


}
