package com.qtong.afinance.module.pojo.admin;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 角色表
 */
public class AdminRole implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id ; 
	private String name;//名称
	private String parentId;//上级角色
	private String description;//描述
	private String creator;//创建人
	private Timestamp createTime;
	private Timestamp updateTime;
	private Integer state;
	
	
	
	public AdminRole() {
	}



	public AdminRole(Integer id, String name, String parentId,
			String description, String creator, Timestamp createTime,
			Timestamp updateTime, Integer state) {
		super();
		this.id = id;
		this.name = name;
		this.parentId = parentId;
		this.description = description;
		this.creator = creator;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.state = state;
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getParentId() {
		return parentId;
	}



	public void setParentId(String parentId) {
		this.parentId = parentId;
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



	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
