package com.qtong.afinance.module.pojo.product;

import java.io.Serializable;

/**
 * 产品
 */
public class AdminProduct implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	private String code;//产品编码
	private String cnname;//名称
	private String enName;//英文代码
	private String description;//描述
	private String proTypeId;//产品类别ID
	private String partnerId;//合作伙伴
	private String productCode;//Boss产品编码
	private String isConnect;//关联boss(0关联1未关联)
	private String createTime;
	private String updateTime;
	private Integer state;

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCnname() {
		return cnname;
	}
	public void setCnname(String cnname) {
		this.cnname = cnname;
	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getProTypeId() {
		return proTypeId;
	}
	public void setProTypeId(String proTypeId) {
		this.proTypeId = proTypeId;
	}
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getIsConnect() {
		return isConnect;
	}
	public void setIsConnect(String isConnect) {
		this.isConnect = isConnect;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "AdminProduct [code=" + code + ", cnname=" + cnname + ", enName=" + enName
				+ ", description=" + description + ", proTypeId=" + proTypeId + ", partnerId=" + partnerId
				+ ", isConnect=" + isConnect + ", createTime=" + createTime + ", updateTime=" + updateTime + ", state="
				+ state + "]";
	}
	
}
