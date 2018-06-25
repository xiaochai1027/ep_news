package com.cfc.util.exception;


/**
 * @author yuanzhe(yuanzhe@lanxum.com)
 *
 * @date 2014年9月24日 上午9:29:58
 */
public class ParamsException extends SysException {
	private static final long serialVersionUID = -7337141821124168233L;

	public ParamsException() {
		super(Errors.ERROR_PARAMS, Errors.getErrorMessage(Errors.ERROR_PARAMS));
	}
}
