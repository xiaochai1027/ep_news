package com.cfc.util.controller;

import com.cfc.util.TextUtils;
import com.cfc.util.spring.SpringMVCUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

/**
 * 所有Controller的基类
 *
 * @author tianyl
 */
public class BaseController {

    public Logger Log = LoggerFactory.getLogger(BaseController.class);

    public BaseController() {

    }


    /**
     * 获取request对象
     *
     * @return
     */
    public HttpServletRequest getRequest() {
        return SpringMVCUtil.getRequest();
    }

    /**
     * 获取response对象
     *
     * @return
     */
    public HttpServletResponse getResponse() {
        return SpringMVCUtil.getResponse();
    }

    /**
     * 多参数表示and关系
     *
     * @param params
     * @return
     */
    protected boolean checkParamsEmpty(String... params) {
        if (params == null || params.length == 0) {
            return true;
        }
        for (String param : params) {
            if (!TextUtils.isEmpty(param)) {
                return false;
            }
        }

        return true;
    }

    public String getToken() {
        if (SpringMVCUtil.getRequest() == null) {
            return null;
        }

        Cookie[] cookies = SpringMVCUtil.getRequest().getCookies();
        if (cookies == null) {
            return SpringMVCUtil.getRequest().getParameter("token");
        }

        for (Cookie cookie : cookies) {
            if (getCookieTokenKey().equalsIgnoreCase(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return SpringMVCUtil.getRequest().getParameter("token");
    }



    protected String getCookieTokenKey() {
        return "kc_token";
    }




}
