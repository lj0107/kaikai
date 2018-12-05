package com.qtong.afinance.module.pojo.urlGuard;

import java.io.Serializable;

public class AfinUrlGuardCode implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String url;//网址
	private String counterfeitDomain;//仿冒域名
	private String snapshot;//快照
	private String customerNo;//所属客户编码
	private String customerName;//所属客户名称
	private String checkTime;//检测时间
	private Integer interceptCount;//累计拦截次数
	private String invalidTime;//失效时间
	private String siteState;//网站状态
	private String serverIp;//服务器IP
	private String serverLocation;//服务器所在地
	private String registerPerson;//注册人名称
	private String registerMai;//注册邮箱
	private String registerPhone;//注册人电话
	private String partnerNo;//推送者编码
	private String partnerName;//推送者名称
	private String pushTime;//推送时间
	private String state;//状态
	private String optTime;//操作时间
	private String optId;//操作人id
	private String optName;//操作人名称
	
	private String code;//状态编码
	private String display;//状态显示
	private String description;//状态描述
	public String getCounterfeitDomain() {
		return counterfeitDomain;
	}
	public void setCounterfeitDomain(String counterfeitDomain) {
		this.counterfeitDomain = counterfeitDomain;
	}
	public String getSnapshot() {
		return snapshot;
	}
	public void setSnapshot(String snapshot) {
		this.snapshot = snapshot;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}
	public Integer getInterceptCount() {
		return interceptCount;
	}
	public void setInterceptCount(Integer interceptCount) {
		this.interceptCount = interceptCount;
	}
	public String getInvalidTime() {
		return invalidTime;
	}
	public void setInvalidTime(String invalidTime) {
		this.invalidTime = invalidTime;
	}
	public String getSiteState() {
		return siteState;
	}
	public void setSiteState(String siteState) {
		this.siteState = siteState;
	}
	public String getServerIp() {
		return serverIp;
	}
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	public String getServerLocation() {
		return serverLocation;
	}
	public void setServerLocation(String serverLocation) {
		this.serverLocation = serverLocation;
	}
	public String getRegisterPerson() {
		return registerPerson;
	}
	public void setRegisterPerson(String registerPerson) {
		this.registerPerson = registerPerson;
	}
	public String getRegisterMai() {
		return registerMai;
	}
	public void setRegisterMai(String registerMai) {
		this.registerMai = registerMai;
	}
	public String getRegisterPhone() {
		return registerPhone;
	}
	public void setRegisterPhone(String registerPhone) {
		this.registerPhone = registerPhone;
	}
	public String getPartnerNo() {
		return partnerNo;
	}
	public void setPartnerNo(String partnerNo) {
		this.partnerNo = partnerNo;
	}
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	public String getPushTime() {
		return pushTime;
	}
	public void setPushTime(String pushTime) {
		this.pushTime = pushTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getOptTime() {
		return optTime;
	}
	public void setOptTime(String optTime) {
		this.optTime = optTime;
	}
	public String getOptId() {
		return optId;
	}
	public void setOptId(String optId) {
		this.optId = optId;
	}
	public String getOptName() {
		return optName;
	}
	public void setOptName(String optName) {
		this.optName = optName;
	}
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
	
}
