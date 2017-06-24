package com.project.adsd.measurement;

import com.project.adsd.measurement.http.BasicRequest;
import com.project.adsd.measurement.http.LoginRequest;
import com.project.adsd.measurement.io.Output;
import com.project.adsd.measurement.sample.Sample;
import com.project.adsd.measurement.sample.Line;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;

public class Main {

    private static Sample sample = new Sample();
    private static Output output = new Output("/home/diego/Downloads/sample.csv");

    public static void main(String[] args) {
        String accessToken   = new LoginRequest().getAccessToken();
        BasicRequest request = new BasicRequest(accessToken, sample);

        Runnable runnableTask = new Runnable() {

            @Override
            public void run() {
                request.submitRequest(sample.nextLine());
            }
        };
        ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(10);
        threadPool.scheduleWithFixedDelay(runnableTask, 0, 60, TimeUnit.MILLISECONDS);

        try {
            // Sleep 1 minute
            Thread.sleep(2 * 60 * 1000);

            threadPool.shutdown();
            while (!threadPool.isTerminated());

            System.out.println("ScheduledThreadPool shutdown saving sample");
            List<Line> sampleData = sample.getData();

            output.save(sampleData);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
