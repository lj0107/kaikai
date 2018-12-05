package com.qtong.afinance.module.pojo.urlGuard;

import java.io.Serializable;

/**
 * 网址卫士操作日志表
 *
 */
public class AfinUrlGuardProcess implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;//id
	private String url;//网址
	private String currentState;//当前状态
	private String lastState;//上一状态
	private String remark;//备注
	private String optId;//操作人id
	private String optName;//操作人名称
	private String optTime;//操作时间
	private String optType;//操作类型
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCurrentState() {
		return currentState;
	}
	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}
	public String getLastState() {
		return lastState;
	}
	public void setLastState(String lastState) {
		this.lastState = lastState;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getOptTime() {
		return optTime;
	}
	public void setOptTime(String optTime) {
		this.optTime = optTime;
	}
	public String getOptType() {
		return optType;
	}
	public void setOptType(String optType) {
		this.optType = optType;
	}
	@Override
	public String toString() {
		return "AfinUrlGuardProcess [id=" + id + ", url=" + url + ", currentState=" + currentState + ", lastState="
				+ lastState + ", remark=" + remark + ", optId=" + optId + ", optName=" + optName + ", optTime="
				+ optTime + ", optType=" + optType + "]";
	}
	
	
	
}
