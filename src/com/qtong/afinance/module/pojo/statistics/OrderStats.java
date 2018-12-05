package com.qtong.afinance.module.pojo.statistics;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 乾坤大数据—业务统计
 * 对订单中标签请求次数和账单金额进行统计
 */
public class OrderStats implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id;
	private String productOrderId;//产品包订购关系Id
	private String productCode;//产品编码
	private String productName;//产品名称
	private Timestamp recordTime;//统计日期
	private String customerNumber;//客户编码
	private Integer count;//请求次数
	private Double fee;//计费 
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
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
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
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Double getFee() {
		return fee;
	}
	public void setFee(Double fee) {
		this.fee = fee;
	}
	@Override
	public String toString() {
		return "OrderStats [id=" + id + ", productOrderId=" + productOrderId
				+ ", productCode=" + productCode + ", productName="
				+ productName + ", recordTime=" + recordTime
				+ ", customerNumber=" + customerNumber + ", count=" + count
				+ ", fee=" + fee + "]";
	}
	
}
