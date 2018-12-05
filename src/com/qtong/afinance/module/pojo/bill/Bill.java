package com.qtong.afinance.module.pojo.bill;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 账单实体类
 *
 */
public class Bill implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String billNumber;//账单号
	private Timestamp billTime;//账单时间
	private String productOrderId;//订单号
	private Timestamp orderTime;//下单时间
	private String customerNumber;//客户编码
	private String customerName;//客户名称
	private String productName;//产品名称
	private double fee;
	
	public String getBillNumber() {
		return billNumber;
	}
	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}
	public Timestamp getBillTime() {
		return billTime;
	}
	public void setBillTime(Timestamp billTime) {
		this.billTime = billTime;
	}
	public String getProductOrderId() {
		return productOrderId;
	}
	public void setProductOrderId(String productOrderId) {
		this.productOrderId = productOrderId;
	}
	public Timestamp getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Timestamp orderTime) {
		this.orderTime = orderTime;
	}
	public String getCustomerNumber() {
		return customerNumber;
	}
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public double getFee() {
		return fee;
	}
	public void setFee(double fee) {
		this.fee = fee;
	}
	
}
