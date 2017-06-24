package com.project.adsd.measurement.sample;

import java.util.Arrays;
import java.util.List;

public class Line {

    private int requestInterval;
    private String responseType;

    private String operation;
    private Double responseTime;

    public Line(String requestType, int requestInterval, String operation, Double responseTime) {
        this.responseType = requestType;
        this.requestInterval = requestInterval;
        this.operation = operation;
        this.responseTime = responseTime;
    }

    @Override
    public String toString() {
        return String.format("%s;%s;%d;%.4f", responseType, operation, requestInterval, responseTime);
    }

    public List<String> asList() {
        return Arrays.asList(toString().split(";"));
    }
}
