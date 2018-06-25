package com.cfc.util.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

/**
 * web.xml需要配置com.lanxum.platform.core.web.filter.RequestResponseContextHolderFilter
 * 
 * @author tianyl
 * 
 */
public class SpringMVCUtil {

    private static Logger Log = LoggerFactory.getLogger(SpringMVCUtil.class);
    private static final ThreadLocal<HttpServletRequest> REQ_LOCAL = new ThreadLocal<HttpServletRequest>();
    private static final ThreadLocal<HttpServletResponse> RES_LOCAL = new ThreadLocal<HttpServletResponse>();
    private static final ThreadLocal<Integer> REQUEST_ID = new ThreadLocal<Integer>();
    
    private static final ThreadLocal<Long> START = new ThreadLocal<Long>();

    public static void start() {
        START.set(System.currentTimeMillis());
    }
    
    public static Long end() {
        Long end = System.currentTimeMillis();
        Long cost = end - (START.get() == null ? 0 : START.get());
        Log.trace("api mills:" + cost);
        return cost;
    }
    
    public static HttpServletRequest getRequest() {
        return REQ_LOCAL.get();
    }

    public static HttpServletResponse getResponse() {
        return RES_LOCAL.get();
    }
    
    public static void initRequestId() {
        REQUEST_ID.set(new Random().nextInt(Integer.MAX_VALUE));
    }

    public static Integer getRequestId() {
        return REQUEST_ID.get();
    }

    public static void setRequestAndResponse(HttpServletRequest request, HttpServletResponse response) {
        REQ_LOCAL.set(request);
        RES_LOCAL.set(response);
    }

    public static void cleanRequestAndResponse() {
        REQ_LOCAL.remove();
        RES_LOCAL.remove();

    }


    public static String getParameter(String key){
        HttpServletRequest request = getRequest();
        if(request != null){
            return request.getParameter(key);
        }
        return null;
    }

    public static void render(String result) {
        render(result, "text/plain");
    }

    public static void renderText(String result) {
        render(result, "text/plain");
    }

    public static void renderJson(String result) {
        render(result, "application/json");
    }

    public static void renderXml(String result) {
        render(result, "text/xml");
    }

    public static void renderHtml(String result) {
        render(result, "text/html");
    }

    public static void render(String result, String contentType) {
        if (result == null) {
            return;
        }
        String fullContentType = contentType + ";charset=UTF-8";
        HttpServletResponse response = getResponse();
        response.setContentType(fullContentType);
        try {
            response.getWriter().write(result);
            response.getWriter().flush();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    public static boolean isDebugServer(){
        return getRequest().getServerName().contains("xuanke.com");


    }
}
