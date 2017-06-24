package com.project.adsd.measurement.io;


import com.project.adsd.measurement.sample.Line;
import com.project.adsd.measurement.utils.CSVUtils;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class Output {

    private File file;

    public Output(String path) {
        file = new File(path);

        // Create a folder if no exists
        file.getParentFile().mkdirs();
    }

    public void save(List<Line> lines) throws IOException {
        if (!file.exists())
            // Create the file if it no exists
            file.createNewFile();

        Writer fileWriter = new FileWriter(file);
        BufferedWriter writer = new BufferedWriter(fileWriter);

        CSVUtils.writeLine(
                writer,
                Arrays.asList("response.type", "operation", "request.interval", "response.time"),
                ';');

        for (Line line : lines)
            CSVUtils.writeLine(writer, line.asList(), ';');

        writer.close();
        fileWriter.close();
    }
}
