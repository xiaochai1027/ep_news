package com.cfc.util.controller;

import com.alibaba.fastjson.JSONObject;
import com.cfc.util.TextUtils;
import com.cfc.util.exception.KickOffException;
import com.cfc.util.spring.SpringMVCUtil;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class BasePageController extends BaseController {
    protected Map<String, Object> extraInfo = new HashMap<>();

    protected Map<String, Object> genReturnMap(Map<String, Object> map) {

        map.put("stime", System.currentTimeMillis());
        map.put("requestId", SpringMVCUtil.getRequestId());
        String token = getToken();
        map.put("logined", 0);
        if (!TextUtils.isEmpty(token)) {
            try {

            } catch (Exception e) {
                Log.error("isError", e);
            }
        } else {
            clearToken();
        }
        fillReturnInfo();
        map.putAll(extraInfo);

        Log.trace("page content:" + map.toString());
        Long timeCost = SpringMVCUtil.end();

        // Sensors Analytics

        // Statistic Logging
        // added by xiuzongda
        String uid = null;
        if (null != map.get("account")) {
            JSONObject accInfo = (JSONObject) map.get("account");
            uid = accInfo.getString("uid");
        }
//        loggingStatistic(map, timeCost, uid);
        // end of Statistic Logging
        return map;
    }

    protected abstract void fillReturnInfo();



    protected void setReturnInfo(String key, String val) {
        extraInfo.put(key, val);
    }

    private void clearToken() {
        Cookie c = new Cookie("kc_token", null);
        c.setMaxAge(0);
        c.setPath("/");

        Cookie c2 = new Cookie("kc_token", null);
        c2.setMaxAge(0);
        getResponse().addCookie(c);
        getResponse().addCookie(c2);
//        clearCookieAll("kc_token");
    }

    protected void clearCookieAll(String name) {
        if (TextUtils.isEmpty(name)) {
            return;
        }
        String serverName = getRequest().getServerName();
        if (TextUtils.isEmpty(serverName)) {
            return;
        }
        Set<String> domains = new HashSet<>();
        if (!"localhost".equals(serverName)) {
            domains.add("." + serverName);
            String secondDomain = getSecondDomain(serverName);
            domains.add(secondDomain);
            domains.add("." + secondDomain);
        }
        //删除对应域名的cookie
        domains.forEach(domain -> {
            Cookie c = new Cookie(name, null);
            c.setHttpOnly(true);
            c.setDomain(domain);
            c.setMaxAge(0);
            c.setPath("/");
            getResponse().addCookie(c);
        });
        //删除默认域名下的cookie
        Cookie c = new Cookie(name, null);
        c.setHttpOnly(true);
        c.setMaxAge(0);
        c.setPath("/");
        getResponse().addCookie(c);
    }

    protected String getSecondDomain(String serverName) {
        String[] serverNameArr = serverName.split("\\.");
        String secondDomain;
        if (serverNameArr.length < 2) {
            secondDomain = serverName;
        } else {
            secondDomain = serverNameArr[serverNameArr.length - 2] + "." + serverNameArr[serverNameArr.length - 1];
        }

        return secondDomain;
    }

    @ExceptionHandler(Exception.class)
    protected void handleException(Exception e) {
        Log.error("isError",e);
        try {
            Log.info("redirect to error");
            getResponse().sendRedirect(getErrorUri());
        } catch (IOException e1) {
            Log.error("isError",e1);
        }
    }

    @ExceptionHandler(KickOffException.class)
    public String handleException(KickOffException e) {
        return "forward:" + getKickOfUri();
    }

    @RequestMapping("*")
    public ModelAndView error() {
        ModelAndView mv = new ModelAndView("redirect:" + getErrorUri());
        return mv;
    }

    protected String getErrorUri() {
        return "/error.html";
    }

    private String getKickOfUri() {
        return "/account/kicked";
    }

    public static void main(String[] args) {
        String serverName = "kaochong.xuanke.com";
        String[] serverNameArr = serverName.split("\\.");
        String secondDomain = null;
        if (serverNameArr.length < 2) {
            secondDomain = serverName;
        } else {
            secondDomain = serverNameArr[serverNameArr.length - 2] + "." + serverNameArr[serverNameArr.length - 1];
        }
        System.out.println(secondDomain);
    }
}
