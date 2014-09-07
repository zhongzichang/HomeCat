package com.osgsquare.homecat.net;

import com.google.inject.Inject;

import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * Created by zzc on 8/23/14.
 */
public class StatefullRestTemplate extends RestTemplate {

    private final HttpClient httpClient;
    private final HttpContext httpContext;
    private final StatefullHttpComponentsClientHttpRequestFactory statefullHttpComponentsClientHttpRequestFactory;
    @Inject
    private CookieStore cookieStore;


    public StatefullRestTemplate()
    {
        super();
        HttpParams params = new BasicHttpParams();
        HttpClientParams.setRedirecting(params, false);
        httpClient = new DefaultHttpClient(params);
        httpContext = new BasicHttpContext();
        httpContext.setAttribute(ClientContext.COOKIE_STORE, getCookieStore());
        statefullHttpComponentsClientHttpRequestFactory =
                new StatefullHttpComponentsClientHttpRequestFactory(httpClient, httpContext);
        setRequestFactory(statefullHttpComponentsClientHttpRequestFactory);

        getMessageConverters().add(new FormHttpMessageConverter());
        getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        getMessageConverters().add(new StringHttpMessageConverter());

    }

    public HttpClient getHttpClient()
    {
        return httpClient;
    }

    public CookieStore getCookieStore()
    {
        return cookieStore;
    }

    public HttpContext getHttpContext()
    {
        return httpContext;
    }

    public StatefullHttpComponentsClientHttpRequestFactory getStatefulHttpClientRequestFactory()
    {
        return statefullHttpComponentsClientHttpRequestFactory;
    }

    public class StatefullHttpComponentsClientHttpRequestFactory extends HttpComponentsClientHttpRequestFactory
    {
        private final HttpContext httpContext;

        public StatefullHttpComponentsClientHttpRequestFactory(HttpClient httpClient, HttpContext httpContext)
        {
            super(httpClient);
            this.httpContext = httpContext;
        }

        @Override
        protected HttpContext createHttpContext(HttpMethod httpMethod, URI uri)
        {
            return this.httpContext;
        }
    }
}
