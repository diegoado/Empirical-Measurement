package com.project.adsd.measurement.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.apache.http.client.ResponseHandler;

import java.io.IOException;

public class JSONResponseHandler implements ResponseHandler<JSONObject> {

    private JSONParser jsonParser = new JSONParser();

    @Override
    public JSONObject handleResponse(HttpResponse response) {

        JSONObject jsonResponse = new JSONObject();
        HttpEntity entity = response.getEntity();

        if (entity == null) {
            jsonResponse.put("status" , "no_data");
            jsonResponse.put("message", "Null data found");
        } else {
            try {
                jsonResponse = (JSONObject) jsonParser.parse(EntityUtils.toString(entity));
            } catch (ParseException | IOException | org.json.simple.parser.ParseException e) {
                jsonResponse.put("status", "error");
                jsonResponse.put("error" , "parser_error");
                jsonResponse.put("error_description", e.getMessage());
            }
        }
        return jsonResponse;
    }
}
