package com.project.adsd.measurement.http;

import com.project.adsd.measurement.sample.Line;
import com.project.adsd.measurement.sample.Sample;
import com.project.adsd.measurement.utils.FileResponseHandler;
import org.apache.http.client.methods.HttpGet;

import java.io.File;

public class SimpleImageRequest extends BaseRequest<File> {

    public SimpleImageRequest(String accessToken, Sample sample) {
        super(accessToken, sample);

        httpMethod = new HttpGet(BASE_URL + "/api/images/58e3e4f02f968500043eb294/download");
        responseHandler = new FileResponseHandler(
                System.getProperty("user.home") + System.getProperty("file.separator")
        );
    }

    @Override
    public void submitRequest(int interval) {
        long startTime = System.nanoTime();
        File fileResponse = sendRequest();
        long endTime   = System.nanoTime();

        if (fileResponse != null) {
            Double requestTime = (endTime - startTime) / 1e6;
            fileResponse.delete();

            // Storage Request Metric
            sample.add(new Line("Image", interval, "Simple", requestTime));
        } else {
            System.out.println("Error Request, discarding measurement metric!");
        }

    }
}
