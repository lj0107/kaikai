package com.qtong.afinance.module.pojo.urlGuard;

import java.io.Serializable;

/**
 * 网址卫士状态码
 *
 */
public class AfinUrlGuardStateCode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;//状态编码
	private String display;//状态显示
	private String description;//状态描述
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "AfinUrlGuardStateCode [code=" + code + ", display=" + display + ", description=" + description + "]";
	}
	
}
