package com.cfc.util.filter;


import com.cfc.util.spring.SpringMVCUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * author fangchen
 * date  2018/6/25 下午3:15
 */
public class RequestResponseContextHolderFilter implements Filter {
    private static Logger Log = LoggerFactory.getLogger(RequestResponseContextHolderFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        SpringMVCUtil.initRequestId();
        SpringMVCUtil.setRequestAndResponse(req, res);
        SpringMVCUtil.start();

        Map param = req.getParameterMap();
        StringBuilder sb = new StringBuilder();
        sb.append(req.getRequestURI()).append("(");
        for (Object key : param.keySet()) {
            sb.append(key).append(":").append(req.getParameter(key + "")).append("|");
        }
        sb.append(")");
        Log.info(sb.toString());

        try {
            chain.doFilter(request, response);
        } finally {
            SpringMVCUtil.cleanRequestAndResponse();
        }
    }

    @Override
    public void destroy() {

    }
}
