package com.cfc.util.exception;


public class InvalidateTokenError extends SysException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6781166432719545126L;

	public InvalidateTokenError() {
		super(Errors.ERROR_TOKEN_FORMAT, Errors.getErrorMessage(Errors.ERROR_TOKEN_FORMAT));
		// TODO Auto-generated constructor stub
	}

}
