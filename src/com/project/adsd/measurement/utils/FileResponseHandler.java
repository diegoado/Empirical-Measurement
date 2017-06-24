package com.project.adsd.measurement.utils;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class FileResponseHandler implements ResponseHandler<File> {

    private final String filePath;

    public FileResponseHandler(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public File handleResponse(HttpResponse response) throws IOException {
        int status        = response.getStatusLine().getStatusCode();
        HttpEntity entity = response.getEntity();

        String contentDisposition = response.getFirstHeader("Content-Disposition").getValue();
        int index                 = contentDisposition.indexOf("filename=");

        if (status >= 200 && status < 300 && entity != null && index > 0) {
            String filename = contentDisposition.substring(index + 9, contentDisposition.length());
            File target = new File(filePath + filename);

            FileUtils.copyInputStreamToFile(entity.getContent(), target);
            return target;
        }
        return null;
    }
}
