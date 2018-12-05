package com.qtong.afinance.module.pojo.customer;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
/**
 * 
 *  客户信息表（个人用户和集团用户共同信息的表）
 */
public class BossCustomer implements Serializable {
	private static final long serialVersionUID = 1L;

    private String oprType;//操作类型 1/增 2/注销 3/变更

    private String custType;//客户类型 1/个人 2/集团

    private String customerNumber;//客户编码

    private String customerName;//客户名称

    private String companyId;//客户签约方/主办方，统一编码的省编码

    private String loginName;//客户登录名/互联网客户时填写
    
    private String loginPwd;//客户登录密码

    private String nationId;//国籍

    private String province;//归属省/所在省

    private String city;//客户所在地市

    private String icType;//证件类型

    private String icNumber;//证件号码

    private String cusPhone;//客户电话

    private String cusEmail;//客户email

    private String cusFax;//客户传真

    private String cusAddress;//客户地址

    private String cusZipCode;//客户邮政编码

    private String cusWeb;//网址

    private String attributeId;//扩展属性编码

    private String attributeName;//扩展属性名称

    private String attributeValue;//扩展属性取值

    private String notes;//备注
    
    private Timestamp oprTime;//同步时间

    private Timestamp operateTime;//操作时间
    
    private  Integer  status;//状态 0正常 1禁用
    
    private String picName;//负责人姓名
    
    private String picPhone;//负责人电话

    private String subType;//客户类型 1.内部客户 2.测试客户 3.普通客户
    
	private String mobile;//账号-AdminUser表
	
	private Timestamp createTime;//创建登陆用户的时间-AdminUser表
	private Timestamp updpwdTime;//更改密码日期
    
    private BossCustomerGroup bossCustomerGroup;
    private BossCustomerPersonage bossCustomerPersonage;
    
    public String getOprType() {
        return oprType;
    }

    public void setOprType(String oprType) {
        this.oprType = oprType == null ? null : oprType.trim();
    }

    public String getCustType() {
        return custType;
    }

    public void setCustType(String custType) {
        this.custType = custType == null ? null : custType.trim();
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber == null ? null : customerNumber.trim();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getNationId() {
        return nationId;
    }

    public void setNationId(String nationId) {
        this.nationId = nationId == null ? null : nationId.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getIcType() {
        return icType;
    }

    public void setIcType(String icType) {
        this.icType = icType == null ? null : icType.trim();
    }

    public String getIcNumber() {
        return icNumber;
    }

    public void setIcNumber(String icNumber) {
        this.icNumber = icNumber == null ? null : icNumber.trim();
    }

    public String getCusPhone() {
        return cusPhone;
    }

    public void setCusPhone(String cusPhone) {
        this.cusPhone = cusPhone == null ? null : cusPhone.trim();
    }

    public String getCusEmail() {
        return cusEmail;
    }

    public void setCusEmail(String cusEmail) {
        this.cusEmail = cusEmail == null ? null : cusEmail.trim();
    }

    public String getCusFax() {
        return cusFax;
    }

    public void setCusFax(String cusFax) {
        this.cusFax = cusFax == null ? null : cusFax.trim();
    }

    public String getCusAddress() {
        return cusAddress;
    }

    public void setCusAddress(String cusAddress) {
        this.cusAddress = cusAddress == null ? null : cusAddress.trim();
    }

    public String getCusZipCode() {
        return cusZipCode;
    }

    public void setCusZipCode(String cusZipCode) {
        this.cusZipCode = cusZipCode == null ? null : cusZipCode.trim();
    }

    public String getCusWeb() {
        return cusWeb;
    }

    public void setCusWeb(String cusWeb) {
        this.cusWeb = cusWeb == null ? null : cusWeb.trim();
    }

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId == null ? null : attributeId.trim();
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName == null ? null : attributeName.trim();
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue == null ? null : attributeValue.trim();
    }

    public String getNotes() {
        return notes;
    }

    public Timestamp getUpdpwdTime() {
		return updpwdTime;
	}

	public void setUpdpwdTime(Timestamp updpwdTime) {
		this.updpwdTime = updpwdTime;
	}

	public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }

   

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType == null ? null : subType.trim();
    }

	public BossCustomerPersonage getBossCustomerPersonage() {
		return bossCustomerPersonage;
	}

	public void setBossCustomerPersonage(BossCustomerPersonage bossCustomerPersonage) {
		this.bossCustomerPersonage = bossCustomerPersonage;
	}

	public BossCustomerGroup getBossCustomerGroup() {
		return bossCustomerGroup;
	}

	public void setBossCustomerGroup(BossCustomerGroup bossCustomerGroup) {
		this.bossCustomerGroup = bossCustomerGroup;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public Timestamp getOprTime() {
		return oprTime;
	}

	public void setOprTime(Timestamp oprTime) {
		this.oprTime = oprTime;
	}

	public Timestamp getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Timestamp operateTime) {
		this.operateTime = operateTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	public String getPicPhone() {
		return picPhone;
	}

	public void setPicPhone(String picPhone) {
		this.picPhone = picPhone;
	}
	
}