package com.project.adsd.measurement.sample;

import java.util.Arrays;
import java.util.List;

public class Line {

    private int requestNumber;
    private String responseType;

    private String operation;
    private Double responseTime;

    public Line(String requestType, int requestNumber, String operation, Double responseTime) {
        this.responseType = requestType;
        this.requestNumber = requestNumber;
        this.operation = operation;
        this.responseTime = responseTime;
    }

    @Override
    public String toString() {
        return String.format("%s;%s;%d;%.4f", responseType, operation, requestNumber, responseTime);
    }

    public List<String> asList() {
        return Arrays.asList(toString().split(";"));
    }
}
