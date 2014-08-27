package com.osgsquare.homecat.net;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

import roboguice.util.Ln;

/**
 * Created by zhongzichang on 8/27/14.
 */
public class RestResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        Ln.e("Response error: {} {}", response.getStatusCode(), response.getStatusText());
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return RestHelper.isError(response.getStatusCode());
    }
}
