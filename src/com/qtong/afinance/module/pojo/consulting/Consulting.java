package com.qtong.afinance.module.pojo.consulting;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 门户-项目咨询实体类
 *
 */
public class Consulting implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;//主键
	
	private String name;//姓名
	
	private String job;//职位
	
	private String companyName;//单位名称
	
	private String email;//邮箱
	
	private String mobile;//电话
	
	private String reqDep;//需求概述
	
	private Timestamp updateTime;//修改时间
	
	private Timestamp createTime;//创建时间
	
	private int state;//状态
	
	private String result;//处理结果
	
	private String product;//咨询产品

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getReqDep() {
		return reqDep;
	}

	public void setReqDep(String reqDep) {
		this.reqDep = reqDep;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	
	
	
	
	
}
