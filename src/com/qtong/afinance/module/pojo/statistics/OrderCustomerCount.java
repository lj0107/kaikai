package com.qtong.afinance.module.pojo.statistics;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 乾坤大数据—业务统计
 * 对订单中标签请求次数和账单金额进行统计
 */
public class OrderCustomerCount implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id;
	private String proName;//产品名称
	private String productName;//产品包名称
	private Timestamp recordTime;//统计日期
	private String customerNumber;//产品客户请求次数
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Timestamp getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(Timestamp recordTime) {
		this.recordTime = recordTime;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}
	
}
