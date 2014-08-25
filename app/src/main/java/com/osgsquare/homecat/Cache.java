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

        cookieBuffer.setLength(0);
        for ( Map.Entry<String, String> entry : cookieTable.entrySet()) {
            cookieBuffer.append(entry.getKey());
            cookieBuffer.append("=");
            cookieBuffer.append(entry.getValue());
            cookieBuffer.append(";");
        }
        if(cookieBuffer.length() > 0)
            cookieBuffer.deleteCharAt(cookieBuffer.length()-1);
        return cookieBuffer.toString();

    }

    public static void SetCookie() {

    }

}
