package com.osgsquare.homecat.agents;

import com.osgsquare.homecat.model.Group;

import java.util.List;

/**
 * Created by zhongzichang on 8/21/14.
 */
public interface IGroupAgent {


    List<Group> all() throws Exception;

}
