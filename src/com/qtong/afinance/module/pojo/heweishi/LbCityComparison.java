package com.qtong.afinance.module.pojo.heweishi;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 位置基地-城市编码比对主表对应的实体类
 *
 */
public class LbCityComparison implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String chanlCustNo;//主键
	private String actionCode;//功能标识 
	private String userName;//业务名称
	private String pwd;//密码
	private String logserial;//请求流水号
	private String mobile;//手机号
	private String msgContent;//需要发送的短信内容
	
	private String proOrdId;//产品订购关系id
	private Timestamp requestTime;//请求时间
	
	private int fee;//费用
	
	
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getLogserial() {
		return logserial;
	}
	public void setLogserial(String logserial) {
		this.logserial = logserial;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getProOrdId() {
		return proOrdId;
	}
	public void setProOrdId(String proOrdId) {
		this.proOrdId = proOrdId;
	}
	public Timestamp getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(Timestamp requestTime) {
		this.requestTime = requestTime;
	}
	public int getFee() {
		return fee;
	}
	public void setFee(int fee) {
		this.fee = fee;
	}
	@Override
	public String toString() {
		return "LbCityComparison [chanlCustNo=" + chanlCustNo + ", actionCode=" + actionCode + ", userName=" + userName + ", pwd=" + pwd
				+ ", logserial=" + logserial + ", mobile=" + mobile + ", msgContent=" + msgContent + ", proOrdId="
				+ proOrdId + ", requestTime=" + requestTime + ", fee=" + fee + "]";
	}
	public String getChanlCustNo() {
		return chanlCustNo;
	}
	public void setChanlCustNo(String chanlCustNo) {
		this.chanlCustNo = chanlCustNo;
	}
	
	
}
