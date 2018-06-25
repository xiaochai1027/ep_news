package com.cfc.util.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cfc.util.TextUtils;
import com.cfc.util.exception.*;
import com.cfc.util.spring.SpringMVCUtil;
import org.springframework.beans.TypeMismatchException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * 所有Controller的基类
 *
 * @author tianyl
 */
@Controller
public class BaseApiController extends BaseController {
    public BaseApiController() {

    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handleException(Exception e) {

        getResponse().setStatus(500);
        getResponse().setContentType("application/json");
        setHeader();
        Log.error("isError",e);
        String json;
        if (e instanceof SysException) {
            Log.error("isError",e);
            if (e instanceof KickOffException) {
                KickOffException sysError = (KickOffException) e;
                json = sysError.toJsonString();
                // edit by Simon 2018-03-13
                String ver = SpringMVCUtil.getRequest().getParameter("ver");

            } else {
                json = ((SysException) e).toJsonString();
            }

        } else if (e instanceof TypeMismatchException) {
            json = new ParamsException().toJsonString();
        } else {
            json = new UnknownException().toJsonString();
        }


        String callback = getRequest().getParameter("callback");

        if (!TextUtils.isEmpty(callback)) {
            json = callback + "(" + json + ");";
        }

        return json;
    }


    protected void setHeader() {
    }

    protected String genErrorJSON(Exception e) {
        getResponse().setContentType("application/json");
        setHeader();
        Log.error("isError",e);

        String json;

        if (e instanceof TypeMismatchException) {
            json = new ParamsException().toJsonString();
        }

        if (e instanceof SysException) {
            json = ((SysException) e).toJsonString();
        } else {
            json = new UnknownException().toJsonString();
        }

        String callback = getRequest().getParameter("callback");

        if (!TextUtils.isEmpty(callback)) {
            json = callback + "(" + json + ");";
        }

        return json;
    }

    /**
     * @param results
     * @param pageInfo 0：pageSize 1:totalCount
     * @return
     */
    protected Object genReturnJSON(Object results, int... pageInfo) {
        JSONObject jo = new JSONObject();

        filterCols(results);

        jo.put("results", results);

        getResponse().addHeader("Content-Type", "application/json");

        if (pageInfo != null && pageInfo.length > 0) {
            JSONObject pageJson = new JSONObject();
            pageJson.put("pageSize", pageInfo[0]);

            if (pageInfo.length > 1) {
                pageJson.put("totalCount", pageInfo[1]);
            }

            pageJson.put("pageCount"
                    , (int) Math.ceil((float) pageInfo[1] / pageInfo[0]));

            jo.put("page", pageJson);
        }

        jo.put("stime", System.currentTimeMillis());
        jo.put("requestId", SpringMVCUtil.getRequestId());

        String jsonStr = JSON.toJSONString(jo, SerializerFeature.BrowserCompatible
                , SerializerFeature.WriteMapNullValue
                , SerializerFeature.WriteNullStringAsEmpty
                , SerializerFeature.DisableCircularReferenceDetect);

        setHeader();
        Log.trace("return:" + jsonStr);

        Long timeCost = SpringMVCUtil.end();

        String callback = getRequest().getParameter("callback");

        if (!TextUtils.isEmpty(callback)) {
            jsonStr = callback + "(" + jsonStr + ");";

        }
        return jsonStr;
    }

    @RequestMapping("/api/*")
    @ResponseBody
    public String noSuchPattern() {
        return new SysException(Errors.ERROR_NO_SUCH_PATTERN
                , Errors.getErrorMessage(Errors.ERROR_NO_SUCH_PATTERN)).toJsonString();
    }

    private void filterCols(Object results) {
        String cols = getRequest().getParameter("cols");
        if (TextUtils.isEmpty(cols)) {
            return;
        }

        String[] colArr = cols.split(",");
        Set<String> colSet = new HashSet<>();
        colSet.addAll(Arrays.asList(colArr));

        if (results instanceof List) {
            Iterator ite = ((List) results).iterator();
            while (ite.hasNext()) {
                Object next = ite.next();
                if (next instanceof Map) {
                    removeCols((Map) next, colSet);
                }
            }

        } else if (results instanceof Map) {
            removeCols((Map) results, colSet);
        }
    }

    private void removeCols(Map map, Set<String> colSet) {
        Set<String> keys = new HashSet<>(map.keySet());

        keys.removeAll(colSet);

        for (String key : keys) {
            map.remove(key);
        }
    }
}
