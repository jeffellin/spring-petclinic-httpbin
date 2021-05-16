package org.springframework.samples.petclinic.httpbin.web;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import java.io.*;
import java.nio.charset.*;
import java.util.*;
@RestController
public class HttpBinCompatibleController {

    @GetMapping(path = "/get")
    public Map get(HttpServletRequest request){
        Map response = new HashMap();
        Map headers = new HashMap<>();
                for (String headerName : Collections.list(
                        request.getHeaderNames())) {
                    headers.put(headerName, request.getHeader(headerName));
                }

        response.put("url",getFullURL(request));
        response.put("headers", headers);
        response.put("parameters",mapParametersToJSON(request));

        return response;
    }


    private String getFullURL(HttpServletRequest request) {
        StringBuilder requestURL = new StringBuilder(
            request.getRequestURL().toString());
        String queryString = request.getQueryString();
        if (queryString == null) {
            return requestURL.toString();
        } else {
            return requestURL.append('?').append(queryString).toString();
        }
    }

    private Map mapParametersToJSON(HttpServletRequest request) {
        Map headers = new HashMap();

        for (String name : Collections.list(request.getParameterNames())) {
            String[] values = request.getParameterValues(name);
            if (values.length == 1) {
                headers.put(name, values[0]);
            } else {
                headers.put(name, values);
            }
        }

        return headers;
    }

}
