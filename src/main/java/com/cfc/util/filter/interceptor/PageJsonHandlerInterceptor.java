package com.cfc.util.filter.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by liuqingqian on 16/12/1.
 */
public class PageJsonHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {


//        if (request.getRequestURL().toString().contains("chaifangchen.com")) {
//            return;
//        }
        if ("json".equalsIgnoreCase(request.getParameter("to"))) {
            modelAndView.setViewName("/fragment/empty");
            response.addHeader("Content-Type", "application/json");
            String jsonStr = JSON.toJSONString(modelAndView.getModelMap(), SerializerFeature.BrowserCompatible
                    , SerializerFeature.WriteMapNullValue
                    , SerializerFeature.WriteNullStringAsEmpty
                    , SerializerFeature.DisableCircularReferenceDetect);
            response.getWriter().print(jsonStr);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
