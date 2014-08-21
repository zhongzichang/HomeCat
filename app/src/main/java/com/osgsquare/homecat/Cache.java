package com.osgsquare.homecat;

import java.util.Hashtable;
import java.util.Map;

/**
 * Created by zhongzichang on 8/21/14.
 */
public class Cache {

    public static Hashtable<String, String> cookieTable = new Hashtable<String, String>();

    private static StringBuffer cookieBuffer = new StringBuffer();

    public static String GetCookie() {

        for ( Map.Entry<String, String> entry : cookieTable.entrySet()) {
        }
        return cookieBuffer.toString();
    }

}
