package com.project.adsd.measurement.sample;


import java.util.ArrayList;
import java.util.List;

public class Sample {

    private List<Line> data = new ArrayList<>();

    public synchronized void add(Line line) {
        data.add(line);
    }

    public int nextLine() {
        return data.size() + 1;
    }

    public List<Line> getData() {
        return data;
    }
}

