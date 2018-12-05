package com.qtong.afinance.module.pojo.statistics;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 请求次数统计表
 *
 */
public class RequestCounts implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	private String productOrderId;//产品包订购关系Id
	private Timestamp recordTime;//统计日期
	private Integer count;//请求次数
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProductOrderId() {
		return productOrderId;
	}
	public void setProductOrderId(String productOrderId) {
		this.productOrderId = productOrderId;
	}
	public Timestamp getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(Timestamp recordTime) {
		this.recordTime = recordTime;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	
}
