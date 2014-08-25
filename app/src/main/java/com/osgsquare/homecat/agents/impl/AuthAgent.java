package com.osgsquare.homecat.agents.impl;

import com.google.inject.Inject;
import com.osgsquare.homecat.Config;
import com.osgsquare.homecat.agents.IAuthAgent;
import com.osgsquare.homecat.model.AuthCheckResult;
import com.osgsquare.homecat.model.LoginResult;
import com.osgsquare.homecat.net.RestHelper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Created by zhongzichang on 8/21/14.
 */
public class AuthAgent implements IAuthAgent {

    @Inject RestTemplate restTemplate;

    @Override
    public boolean check() {

        final String url = Config.BASE_URL + "/check";
        HttpEntity reqEntity = RestHelper.createEntity();
        RestTemplate restTemplate = RestHelper.createStatefulTemplate();
        ResponseEntity<AuthCheckResult> response = restTemplate.exchange(url, HttpMethod.GET, reqEntity, AuthCheckResult.class);
        AuthCheckResult result = response.getBody();
        return result.logined;

    }

    @Override
    public boolean login(String mobile, String password){

        final String url = Config.BASE_URL + "/login";

        //create the request body
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        body.add("mobile", mobile);
        body.add("password", password);
        HttpEntity reqEntity = RestHelper.createEntity();;
        RestTemplate restTemplate = RestHelper.createStatefulTemplate();
        ResponseEntity<LoginResult> response = restTemplate.exchange(url, HttpMethod.POST, reqEntity, LoginResult.class);
        LoginResult result = response.getBody();
        return result.logined;
    }


}
