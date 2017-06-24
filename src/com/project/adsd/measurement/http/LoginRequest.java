package com.project.adsd.measurement.http;

import com.project.adsd.measurement.utils.JSONResponseHandler;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;

import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginRequest {

    private final String USR = "adsd-project";
    private final String PWD = "measurement";
    private static final String URL = "https://discovery-trip-api.herokuapp.com/api/login";

    public String getAccessToken() {
        JSONObject jsonResponse = login();

        if (jsonResponse != null && jsonResponse.get("status").equals("ok"))
            return (String) jsonResponse.get("access_token");
        else
            return null;
    }

    private JSONObject login() {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json"));

        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("grant_type", "client_credentials");
        jsonRequest.put("username", USR);
        jsonRequest.put("password", PWD);

        HttpPost post = new HttpPost(URL);
        JSONResponseHandler responseHandler = new JSONResponseHandler();
        CloseableHttpClient client = HttpClients.custom().setDefaultHeaders(headers).build();
        try {
            StringEntity parameters = new StringEntity(jsonRequest.toJSONString());

            post.setEntity(parameters);
            return client.execute(post, responseHandler);
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
