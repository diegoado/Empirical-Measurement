package com.project.adsd.measurement.utils;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class CSVUtils {

    private static final char DEFAULT_SEPARATOR = ',';

    public static void writeLine(Writer writer, List<String> values) throws IOException {
        writeLine(writer, values, DEFAULT_SEPARATOR);
    }

    private static String followCSVformat(String value) {
        if (value.contains("\"")) {
            value = value.replace("\"", "\"\"");
        }
        return value;

    }

    public static void writeLine(Writer writer, List<String> values, char separator) throws IOException {
        boolean first = true;

        if (separator == ' ') {
            separator = DEFAULT_SEPARATOR;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (String value : values) {
            if (!first) {
                stringBuilder.append(separator);
            }
            stringBuilder.append(followCSVformat(value));
            first = false;
        }

        stringBuilder.append("\n");
        writer.append(stringBuilder.toString());
    }
}