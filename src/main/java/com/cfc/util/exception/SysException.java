package com.cfc.util.exception;

import com.alibaba.fastjson.JSONObject;
import com.cfc.util.spring.SpringMVCUtil;

/**
 * @author yuanzhe(yuanzhe@lanxum.com)
 *
 * @date 2014年9月24日 上午9:27:36
 */
public class SysException extends RuntimeException {

	private static final long serialVersionUID = -4569408156188186501L;
	
	
	private int errorCode = Errors.ERROR_UNKNOWN;
	
	private String errorMessage = Errors.getErrorMessage(Errors.ERROR_UNKNOWN);

	public SysException(int errorCode) {
		this(errorCode, Errors.getErrorMessage(errorCode));
	}
	
	public SysException(JSONObject json) {
	    this(json.getInteger("errorCode"), json.getString("errorMsg"));
	}

	public SysException(int errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	
	public String toJsonString() {
		JSONObject jo = new JSONObject();
		jo.put("errorCode", errorCode);
		jo.put("errorMsg", errorMessage);
		jo.put("stime", System.currentTimeMillis());
		jo.put("requestId", SpringMVCUtil.getRequestId());
		return jo.toJSONString();
	}
}
