package com.example.messageconverter.controller;

import java.util.Map;

public interface Controller {
    Map<String, String> process(Map<String, String> map);
}
