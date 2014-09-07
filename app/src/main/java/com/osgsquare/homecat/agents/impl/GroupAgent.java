package com.osgsquare.homecat.agents.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.osgsquare.homecat.Config;
import com.osgsquare.homecat.agents.IGroupAgent;
import com.osgsquare.homecat.model.Group;
import com.osgsquare.homecat.net.RestErrorResource;
import com.osgsquare.homecat.net.RestHelper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhongzichang on 8/21/14.
 */
public class GroupAgent implements IGroupAgent {

    @Inject
    RestTemplate restTemplate;
    @Inject
    ObjectMapper objectMapper;

    public List<Group> all() throws Exception {

        final String url = Config.BASE_URL + "/groups";

        HttpEntity reqEntity = RestHelper.createEntity();

        try {
            ResponseEntity<Group[]> response = restTemplate.exchange(url, HttpMethod.GET, reqEntity, Group[].class);
            Group[] result = response.getBody();
            List<Group> list = new ArrayList<Group>();
            for(Group g : result){
                list.add(g);
            }
            return list;
        } catch (HttpStatusCodeException e){
            String errorPayload = e.getResponseBodyAsString();
            RestErrorResource error = objectMapper.readValue(errorPayload, RestErrorResource.class);
            throw new RestClientException("[" + error.getCode() + "] " + error.getMessage());
        }
    }
}
