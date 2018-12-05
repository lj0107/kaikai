package com.qtong.afinance.module.pojo.statistics;

import java.io.Serializable;
import java.sql.Timestamp;
/**
 * 网址卫士-业务统计
 *
 */
public class UrlGuardStats implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id;//主键
	private String customerNumber;//客户编码
	private String state;//类型
	private Timestamp recordTime;//统计时间
	private int count;//网址数量
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCustomerNumber() {
		return customerNumber;
	}
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Timestamp getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(Timestamp recordTime) {
		this.recordTime = recordTime;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}
