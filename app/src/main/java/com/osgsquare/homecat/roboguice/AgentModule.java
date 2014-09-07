package com.osgsquare.homecat.roboguice;

import com.google.inject.AbstractModule;
import com.osgsquare.homecat.agents.IAuthAgent;
import com.osgsquare.homecat.agents.IGroupAgent;
import com.osgsquare.homecat.agents.impl.AuthAgent;
import com.osgsquare.homecat.agents.impl.GroupAgent;
import com.osgsquare.homecat.net.RestHelper;

import org.springframework.web.client.RestTemplate;

/**
 * Created by zhongzichang on 8/25/14.
 */
public class AgentModule extends AbstractModule {


    @Override
    public void configure(){
        bind(IAuthAgent.class).to(AuthAgent.class);
        bind(IGroupAgent.class).to(GroupAgent.class);
        bind(RestTemplate.class).toInstance(RestHelper.createStatefulTemplate());

    }
}
