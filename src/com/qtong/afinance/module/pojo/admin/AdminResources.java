package com.qtong.afinance.module.pojo.admin;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class AdminResources implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id ; 
	private String name;//名称
	private Integer lev;//菜单层级
	private String type;//类型(0页面1功能)
	private String parentId;//上级角色
	private Integer ord;//排序
	private String url;//路径
	private String creator;//创建人
	private String description;//描述
	private Integer mark;//表记
	private Timestamp createTime;
	private Timestamp updateTime;
	private Integer state;
	
	private List<AdminResources> childRes;//子菜单
	
	
	









	public AdminResources() {
	}




	public AdminResources(String name, Integer lev, String type,
			String parentId, Integer ord, String url, String creator,
			String description, Integer mark, Timestamp createTime,
			Timestamp updateTime, Integer state) {
		super();
		this.name = name;
		this.lev = lev;
		this.type = type;
		this.parentId = parentId;
		this.ord = ord;
		this.url = url;
		this.creator = creator;
		this.description = description;
		this.mark = mark;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.state = state;
	}

	


	public AdminResources(Integer id, String name, Integer lev, String type,
			String parentId, Integer ord, String url, String creator,
			Timestamp createTime, Timestamp updateTime, Integer state,
			List<AdminResources> childRes) {
		super();
		this.id = id;
		this.name = name;
		this.lev = lev;
		this.type = type;
		this.parentId = parentId;
		this.ord = ord;
		this.url = url;
		this.creator = creator;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.state = state;
		this.childRes = childRes;
	}





	public String getDescription() {
		return description;
	}







	public void setDescription(String description) {
		this.description = description;
	}






	public Integer getMark() {
		return mark;
	}







	public void setMark(Integer mark) {
		this.mark = mark;
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



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public String getParentId() {
		return parentId;
	}



	public void setParentId(String parentId) {
		this.parentId = parentId;
	}



	public Integer getOrd() {
		return ord;
	}



	public void setOrd(Integer ord) {
		this.ord = ord;
	}



	public String getUrl() {
		return url;
	}



	public void setUrl(String url) {
		this.url = url;
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



	public List<AdminResources> getChildRes() {
		return childRes;
	}



	public void setChildRes(List<AdminResources> childRes) {
		this.childRes = childRes;
	}

	

	public Integer getLev() {
		return lev;
	}







	public void setLev(Integer lev) {
		this.lev = lev;
	}







	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

	
}
