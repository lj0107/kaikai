package com.qtong.afinance.module.pojo.product;

import java.io.Serializable;

/**
 * boss产品表（手动数据库录入）
 * 
 */
public class AdminBossPro implements Serializable {

	private static final long serialVersionUID = 1L;

	private String proName;// 产品包名称
	private String proCode;// 产品包编码
	private String subProName;// 产品名称
	private String subProCode;// 产品编码
	private String description;// 描述
	private String syncTime;// 同步时间
	private String productState;//是否同步
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public String getProCode() {
		return proCode;
	}
	public void setProCode(String proCode) {
		this.proCode = proCode;
	}
	public String getSubProName() {
		return subProName;
	}
	public void setSubProName(String subProName) {
		this.subProName = subProName;
	}
	public String getSubProCode() {
		return subProCode;
	}
	public void setSubProCode(String subProCode) {
		this.subProCode = subProCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSyncTime() {
		return syncTime;
	}
	public void setSyncTime(String syncTime) {
		this.syncTime = syncTime;
	}
	public String getProductState() {
		return productState;
	}
	public void setProductState(String productState) {
		this.productState = productState;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "AdminBossPro [proName=" + proName + ", proCode=" + proCode + ", subProName=" + subProName
				+ ", subProCode=" + subProCode + ", description=" + description + ", syncTime=" + syncTime
				+ ", productState=" + productState + "]";
	}

	

}
