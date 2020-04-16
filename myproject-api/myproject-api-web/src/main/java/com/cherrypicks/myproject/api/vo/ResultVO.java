
package com.cherrypicks.myproject.api.vo;

import com.cherrypicks.myproject.util.ApiCodeStatus;

public class ResultVO {

	private Object result;
	private int errorCode = ApiCodeStatus.SUCCUSS;
	private String errorMessage = "";

	public Object getResult() {
		return result;
	}

	public void setResult(final Object result) {
		this.result = result;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(final int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(final String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
