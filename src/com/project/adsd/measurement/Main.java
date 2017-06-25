package com.project.adsd.measurement;

import com.project.adsd.measurement.http.*;
import com.project.adsd.measurement.io.Output;
import com.project.adsd.measurement.sample.Sample;
import com.project.adsd.measurement.sample.Line;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;

public class Main {

    private static final String USER_HOME = System.getProperty("user.home");

    private static Sample sample = new Sample();
    private static Output output = new Output(USER_HOME + "/Downloads/sample.csv");

    public static void main(String[] args) throws IOException {
        String accessToken = new LoginRequest().getAccessToken();
        int cores          = Runtime.getRuntime().availableProcessors();

        BaseRequest[] requests = {
                new SimpleJSONRequest(accessToken, sample),
                new CalculatedJSONRequest(accessToken, sample),
                new SimpleImageRequest(accessToken, sample),
                new CalculatedImageRequest(accessToken, sample)
        };
        for (BaseRequest request : requests) {
            execute(request, 400, cores);
            execute(request, 40 , cores);

            request.terminate();
        }
        System.out.println("Experiment shutdown saving sample");

        List<Line> sampleData = sample.getData();
        output.save(sampleData);
    }

    private static void execute(BaseRequest request, int interval, int cores) {
        Runnable runnableTask = new Runnable() {
            @Override
            public void run() {
                request.submitRequest(interval);
            }
        };
        int initialDelay = interval / 10;
        System.out.println(String.format(
                "Running the experiment %s using %d cores and making a request every %d milliseconds",
                request.getClass().getSimpleName(), cores, interval));

        ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(cores);
        threadPool.scheduleWithFixedDelay(runnableTask, initialDelay, interval, TimeUnit.MILLISECONDS);

        try {
            // Sleep 1 minute
            Thread.sleep(60 * 1000 + initialDelay);

            threadPool.shutdown();
            while (!threadPool.isTerminated());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
