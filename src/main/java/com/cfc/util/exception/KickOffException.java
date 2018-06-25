package com.cfc.util.exception;

import com.alibaba.fastjson.JSONObject;
import com.cfc.util.spring.SpringMVCUtil;

import java.util.Map;

/**
 * DESC   : PLACEHOLDER
 * <p>
 * DATE   : 2018/1/16
 * AUTHOR : hongxin
 */
public class KickOffException extends SysException{

    private Map<String, Object> exData;

    public KickOffException(int errorCode) {
        super(errorCode);
    }


    public void setExData(Map<String, Object> exData) {
        this.exData = exData;
    }



    public String toJsonString() {
        JSONObject jo = new JSONObject();
        jo.put("errorCode", getErrorCode());
        jo.put("errorInfo", exData);
        jo.put("errorMsg", getErrorMessage());
        jo.put("stime", System.currentTimeMillis());
        jo.put("requestId", SpringMVCUtil.getRequestId());
        return jo.toJSONString();
    }
}
