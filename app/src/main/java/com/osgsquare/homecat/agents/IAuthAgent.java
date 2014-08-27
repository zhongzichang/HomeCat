package com.osgsquare.homecat.agents;

/**
 * Created by zhongzichang on 8/21/14.
 */
public interface IAuthAgent {
    boolean check() throws  Exception;
    boolean login(String mobile, String password) throws Exception;
}
