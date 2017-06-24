package com.project.adsd.measurement.http;

import com.project.adsd.measurement.sample.Line;
import com.project.adsd.measurement.sample.Sample;
import com.project.adsd.measurement.utils.JSONResponseHandler;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BasicRequest implements BaseRequest {

    private Sample sample;
    private List<Header> headers = new ArrayList<>();

    public BasicRequest(String accessToken, Sample sample) {
        this.sample = sample;

        headers.add(new BasicHeader(HttpHeaders.CACHE_CONTROL, "no-cache"));
        headers.add(new BasicHeader(HttpHeaders.CONTENT_TYPE , "application/json"));
        headers.add(new BasicHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken));
    }

    public void submitRequest(int number) {
        long startTime = System.nanoTime();
        JSONObject jsonResponse = sendRequest();
        long endTime   = System.nanoTime();

        if (jsonResponse != null && jsonResponse.get("status").equals("ok")) {
            Double requestTime = (endTime - startTime) / 1e6;

            // Storage Request Metric
            sample.add(new Line("JSON", number, "Simple", requestTime));
        } else {
            System.out.println("Error Request, discarding measurement metric!");
        }
    }

    private JSONObject sendRequest() {
        HttpGet get = new HttpGet("https://discovery-trip-api.herokuapp.com/api/users/");
        JSONResponseHandler responseHandler = new JSONResponseHandler();

        CloseableHttpClient client = HttpClients.custom().setDefaultHeaders(headers).build();

        try {
            return client.execute(get, responseHandler);
        } catch (IOException e) {
            return null;
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
