package com.example.messageconverter;

import com.example.messageconverter.controller.Controller;
import com.example.messageconverter.controller.RequestHttpBodyController;
import com.example.messageconverter.controller.RequestParameterController;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "MessageConverterController", urlPatterns = "/*")
public class MessageConverterDispatcherServlet extends HttpServlet {
    Map<String, Controller> mappingMap = new HashMap<>();

    @PostConstruct
    void initMappingMap() {
        mappingMap.put("/request-param", new RequestParameterController());
        mappingMap.put("/request-body", new RequestHttpBodyController());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        Controller controller = mappingMap.get(requestURI);
        ServletOutputStream out = response.getOutputStream();
        if (ObjectUtils.isEmpty(controller)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "fail");
            return;
        }

        String parsedJson = "";
        if (request.getMethod().equals("GET")) {
            // 쿼리 파라미터 처리
            Map<String, String> map = createParamMap(request);
            Map<String, String> responseMap = controller.process(map);
            parsedJson = mapToJson(responseMap);
        } else {
            // http body(json) 처리
            Map<String, String> bodyMap = rawToMap(readRequestBody(request, parsedJson));
            Map<String, String> responseMap = controller.process(bodyMap);
            parsedJson = mapToJson(responseMap);
        }

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        out.println(parsedJson);
        out.flush();
    }

    // request 로 들어온 json 을 map 으로 parsing 합니다.
    private Map<String, String> rawToMap(String rawJson) {
        Map<String, String> temp = new HashMap<>();
        rawJson = replaceToWhiteSpace(rawJson, "{");
        rawJson = replaceToWhiteSpace(rawJson, "}");

        String[] split = rawJson.split(",");
        for (int i = 0; i < split.length; i++) {
            String[] innerSplit = split[i].split(":");
            innerSplit[0] = replaceToWhiteSpace(innerSplit[0].trim(), "\"");
            innerSplit[1] = replaceToWhiteSpace(innerSplit[1].trim(), "\"");
            temp.put(innerSplit[0], innerSplit[1]);
        }
        return temp;
    }

    // request 로부터 inputstream 을 얻어 body 에 있는 데이터를 읽습니다.
    private static String readRequestBody(HttpServletRequest request, String parsedJson) throws IOException {
        StringBuilder temp = new StringBuilder();
        // request 로부터 소켓의 fd 를 바로 엽니다. -> 서블릿컨테이너가 헤더는 처리하였으므로 본문 데이터만 포함합니다.
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
        String requestLine;
        while ((requestLine = br.readLine()) != null) {
            temp.append(requestLine);
        }
        return temp.toString();
    }

    private static String replaceToWhiteSpace(String rawJson, String target) {
        return rawJson.replace(target, "");
    }

    private static Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }

    private String mapToJson(Map<String, String> map) {
        StringBuilder json = new StringBuilder();
        json.append("{");

        int count = 0;
        for (Map.Entry<String, String> key : map.entrySet()) {
            json.append(key.getKey());
            json.append(" : ");
            json.append(key.getValue());
            if (++count < map.size()) json.append(", ");
        }
        json.append("}");
        return json.toString();
    }
}
