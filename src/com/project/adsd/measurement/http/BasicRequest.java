package com.project.adsd.measurement.http;

import com.project.adsd.measurement.sample.Line;
import com.project.adsd.measurement.sample.Sample;
import com.project.adsd.measurement.utils.JSONResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.json.simple.JSONObject;

public class BasicRequest extends BaseRequest<JSONObject> {

    public BasicRequest(String accessToken, Sample sample) {
        super(accessToken, sample);

        httpMethod = new HttpGet(BASE_URL + "/api/users/");
        responseHandler = new JSONResponseHandler();
    }

    public void submitRequest(int interval) {
        long startTime = System.nanoTime();
        JSONObject jsonResponse = sendRequest();
        long endTime   = System.nanoTime();

        if (jsonResponse != null && jsonResponse.get("status").equals("ok")) {
            Double requestTime = (endTime - startTime) / 1e6;

            // Storage Request Metric
            sample.add(new Line("JSON", interval, "Simple", requestTime));
        } else {
            System.out.println("Error Request, discarding measurement metric!");
        }
    }
}
