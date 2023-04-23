package com.example.messageconverter.controller;

import java.util.Map;


@MyRestController("/request-param")
public class RequestParameterController implements Controller {
    @Override
    public Map<String, String> process(Map<String, String> map) {
        return map;
    }
}
