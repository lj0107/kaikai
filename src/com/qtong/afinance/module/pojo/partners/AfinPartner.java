package com.qtong.afinance.module.pojo.partners;


import java.io.Serializable;
import java.sql.Timestamp;
/**
 * 后台管理-合作伙伴
 *
 */

public class AfinPartner implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id; //序号
	
	private String  name; //名称
	
	private String ContactPerson;//联系人
	
	private String ContactNumber;//联系电话
	
	private String ContactEmail;//联系邮箱
	
	private String CooperationContent;// 合作内容

	private Timestamp updateTime;//修改时间
	
	private Timestamp createTime;//创建时间
	
	private Integer operatorId;//操作人id
	
	private String filedCode;//合作领域
	
	public String getName() {
		return name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactPerson() {
		return ContactPerson;
	}

	public void setContactPerson(String contactPerson) {
		ContactPerson = contactPerson;
	}

	public String getContactNumber() {
		return ContactNumber;
	}

	public void setContactNumber(String contactNumber) {
		ContactNumber = contactNumber;
	}

	public String getContactEmail() {
		return ContactEmail;
	}

	public void setContactEmail(String contactEmail) {
		ContactEmail = contactEmail;
	}

	public String getCooperationContent() {
		return CooperationContent;
	}

	public void setCooperationContent(String cooperationContent) {
		CooperationContent = cooperationContent;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public String getFiledCode() {
		return filedCode;
	}

	public void setFiledCode(String filedCode) {
		this.filedCode = filedCode;
	}

	
}
