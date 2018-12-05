package com.qtong.afinance.module.pojo.urlGuard;

import java.io.Serializable;

/**
 * 恶意网址库
 *
 */
public class AfinUrlGuardSpiteStoreroom implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String url;//网址
	private String snapshot;//快照
	private String customerName;//所属客户名称
	private String createTime;//入库时间
	private String partnerName;//推入者
	private String state;//状态
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSnapshot() {
		return snapshot;
	}
	public void setSnapshot(String snapshot) {
		this.snapshot = snapshot;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "afinUrlguardSpiteStoreroom [url=" + url + ", snapshot=" + snapshot + ", customerName=" + customerName
				+ ", createTime=" + createTime + ", state=" + state + "]";
	}
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	
	
}
