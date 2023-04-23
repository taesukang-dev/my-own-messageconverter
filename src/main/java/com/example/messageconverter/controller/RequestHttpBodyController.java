package com.example.messageconverter.controller;

import java.util.Map;

@MyRestController("/request-body")
public class RequestHttpBodyController implements Controller{
    @Override
    public Map<String, String> process(Map<String, String> map) {
        return map;
    }
}
