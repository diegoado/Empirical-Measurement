package com.project.adsd.measurement.http;

import com.project.adsd.measurement.sample.Sample;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseRequest<T> {

    Sample sample;
    private CloseableHttpClient client;

    HttpRequestBase httpMethod;
    ResponseHandler<T> responseHandler;

    final String BASE_URL = "https://discovery-trip-api.herokuapp.com";

    BaseRequest(String accessToken, Sample sample) {
        this.sample = sample;
        List<Header> headers = new ArrayList<>();

        headers.add(new BasicHeader(HttpHeaders.CACHE_CONTROL, "no-cache"));
        headers.add(new BasicHeader(HttpHeaders.CONTENT_TYPE , "application/json"));
        headers.add(new BasicHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken));

        client = HttpClients.custom().setDefaultHeaders(headers).build();
    }

    public void terminate() throws IOException {
        client.close();
    }

    T sendRequest() {
        try {
            return client.execute(httpMethod, responseHandler);
        } catch (IOException e) {
            return null;
        }
    }

    public abstract void submitRequest(int interval);
}
