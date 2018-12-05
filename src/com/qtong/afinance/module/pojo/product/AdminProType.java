package com.qtong.afinance.module.pojo.product;

import java.io.Serializable;

/**
 * 产品类别
 */
public class AdminProType implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	private String packageCode;
	private String typeName;//类别名称
	private String lev;//所属层级
	private String parentName;//上级名称
	private String parentId;//上级id
	private String create_time;//创建时间
	private String update_time;//更新时间
	private String user_id;//操作人id
	private String typeState;//是否已经同步
	public String getPackageCode() {
		return packageCode;
	}
	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getLev() {
		return lev;
	}
	public void setLev(String lev) {
		this.lev = lev;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getTypeState() {
		return typeState;
	}
	public void setTypeState(String typeState) {
		this.typeState = typeState;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "AdminProType [packageCode=" + packageCode + ", typeName=" + typeName + ", lev=" + lev + ", parentName="
				+ parentName + ", parentId=" + parentId + ", create_time=" + create_time + ", update_time="
				+ update_time + ", user_id=" + user_id + ", typeState=" + typeState + "]";
	}
	
	
}
