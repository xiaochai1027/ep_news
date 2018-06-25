package com.cfc.util.exception;


/**
 * @author yuanzhe(yuanzhe@lanxum.com)
 *
 * @date 2014年9月24日 上午9:40:35
 */
public class UnknownException extends SysException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8793712439748882643L;
	
	public UnknownException() {
		super(Errors.ERROR_UNKNOWN, Errors.getErrorMessage(Errors.ERROR_UNKNOWN));
	}
}
