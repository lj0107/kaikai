package com.qtong.afinance.module.pojo.admin;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 用户表
 */
public class AdminUser implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id ; 
	private String mobile;//账号
	private String password;//密码
	private String name;//名称
	private String ecid;//客户编码
	private String mail;//邮箱
	private String job;//职务
	private String department;//部门
	private String description;//描述
	private String creator;//创建人
	private Timestamp createTime;//创建时间
	private Timestamp updateTime;//更新日期
	private Timestamp updpwdTime;//更改密码日期
	
	private Integer state;
	
	private String roleName;//角色名称
	
	
	
	public AdminUser() {
	}

	public AdminUser(Integer id, String mobile, String password, String name,
			String ecid, String mail, String job, String department,
			String description, String creator, Timestamp createTime,
			Timestamp updateTime, Integer state, String roleName) {
		super();
		this.id = id;
		this.mobile = mobile;
		this.password = password;
		this.name = name;
		this.ecid = ecid;
		this.mail = mail;
		this.job = job;
		this.department = department;
		this.description = description;
		this.creator = creator;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.state = state;
		this.roleName = roleName;
	}







	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getMobile() {
		return mobile;
	}



	public void setMobile(String mobile) {
		this.mobile = mobile;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getEcid() {
		return ecid;
	}



	public void setEcid(String ecid) {
		this.ecid = ecid;
	}



	public String getMail() {
		return mail;
	}



	public void setMail(String mail) {
		this.mail = mail;
	}



	public String getJob() {
		return job;
	}



	public void setJob(String job) {
		this.job = job;
	}



	public String getDepartment() {
		return department;
	}



	public void setDepartment(String department) {
		this.department = department;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getCreator() {
		return creator;
	}



	public void setCreator(String creator) {
		this.creator = creator;
	}



	public Timestamp getCreateTime() {
		return createTime;
	}



	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}



	public Timestamp getUpdpwdTime() {
		return updpwdTime;
	}

	public void setUpdpwdTime(Timestamp updpwdTime) {
		this.updpwdTime = updpwdTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}



	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}



	public Integer getState() {
		return state;
	}



	public void setState(Integer state) {
		this.state = state;
	}

	

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

	
}
