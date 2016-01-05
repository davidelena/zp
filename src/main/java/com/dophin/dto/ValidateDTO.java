package com.dophin.dto;

public class ValidateDTO {
	private String validateKey;

	private String validateMsg;

	public ValidateDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ValidateDTO(String validateKey, String validateMsg) {
		super();
		this.validateKey = validateKey;
		this.validateMsg = validateMsg;
	}

	public String getValidateKey() {
		return validateKey;
	}

	public void setValidateKey(String validateKey) {
		this.validateKey = validateKey;
	}

	public String getValidateMsg() {
		return validateMsg;
	}

	public void setValidateMsg(String validateMsg) {
		this.validateMsg = validateMsg;
	}

	@Override
	public String toString() {
		return "ValidateDTO [validateKey=" + validateKey + ", validateMsg=" + validateMsg + "]";
	}

}
