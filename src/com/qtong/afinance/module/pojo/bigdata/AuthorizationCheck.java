package com.qtong.afinance.module.pojo.bigdata;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 乾坤大数据-授权核验-授权核验列表实体类
 *
 */
public class AuthorizationCheck implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String productOrderId;//产品包订购关系id
	
	private Timestamp effTime;//下单时间 订购生效时间
	
	private String customerName;//客户名称
	
	private String productName;//产品名称
	
	private String selCounts;//查询次数

	

	public String getProductOrderId() {
		return productOrderId;
	}

	public void setProductOrderId(String productOrderId) {
		this.productOrderId = productOrderId;
	}

	public Timestamp getEffTime() {
		return effTime;
	}

	public void setEffTime(Timestamp effTime) {
		this.effTime = effTime;
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

	public String getSelCounts() {
		return selCounts;
	}

	public void setSelCounts(String selCounts) {
		this.selCounts = selCounts;
	}

	@Override
	public String toString() {
		return "AuthorizationCheck [productOrderId=" + productOrderId + ", effTime=" + effTime + ", customerName="
				+ customerName + ", productName=" + productName + ", selCounts=" + selCounts + "]";
	}

	
	
	
	
}
