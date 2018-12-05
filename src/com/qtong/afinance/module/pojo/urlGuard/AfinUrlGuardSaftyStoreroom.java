package com.qtong.afinance.module.pojo.urlGuard;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * 安全网址库
 *
 */
public class AfinUrlGuardSaftyStoreroom implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String url;//网址
	private String snapshot;//快照
	private String customerNo;//所属客户编码
	private String customerName;//所属客户名称
	private Timestamp createTime;//入库时间
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
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp timestamp) {
		this.createTime = timestamp;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "AfinUrlguardSaftyStoreroom [url=" + url + ", snapshot=" + snapshot + ", customerNo=" + customerNo
				+ ", customerName=" + customerName + ", createTime=" + createTime + "]";
	}
	
	
}
