package com.qtong.afinance.module.pojo.customer;

import java.sql.Timestamp;
import java.util.Date;
/**
 * 
 * 客户信息从表（集团信息）
 *
 */
public class BossCustomerGroup {
	private static final long serialVersionUID = 1L;
   // private String customerNumber;//客户编码

    private String parentCustomerNumber;//上级集团客户编码

    private String orgType;//公司性质

    private String serviceLevel;//客户服务等级

    private String industryId;//行业类别

    private String turnover;//年营业额

    private String employeeCount;//员工数

    private String isStrategic;//是否战略客户

    private String managerName;//客户经理姓名

    private String managerPhone;//客户经理电话

    private String name;//联系人姓名

    private String phone;//联系人电话

    private String email;//联系人email

    private String address;//联系人地址

    private String zipCode;//联系人邮编

    
    private BossCustomer bossCustomer;

   /* public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber == null ? null : customerNumber.trim();
    }*/

    public String getParentCustomerNumber() {
        return parentCustomerNumber;
    }

    public void setParentCustomerNumber(String parentCustomerNumber) {
        this.parentCustomerNumber = parentCustomerNumber == null ? null : parentCustomerNumber.trim();
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType == null ? null : orgType.trim();
    }

    public String getServiceLevel() {
        return serviceLevel;
    }

    public void setServiceLevel(String serviceLevel) {
        this.serviceLevel = serviceLevel == null ? null : serviceLevel.trim();
    }

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId == null ? null : industryId.trim();
    }

    public String getTurnover() {
        return turnover;
    }

    public void setTurnover(String turnover) {
        this.turnover = turnover == null ? null : turnover.trim();
    }

    public String getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(String employeeCount) {
        this.employeeCount = employeeCount == null ? null : employeeCount.trim();
    }

    public String getIsStrategic() {
        return isStrategic;
    }

    public void setIsStrategic(String isStrategic) {
        this.isStrategic = isStrategic == null ? null : isStrategic.trim();
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName == null ? null : managerName.trim();
    }

    public String getManagerPhone() {
        return managerPhone;
    }

    public void setManagerPhone(String managerPhone) {
        this.managerPhone = managerPhone == null ? null : managerPhone.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode == null ? null : zipCode.trim();
    }


	public BossCustomer getBossCustomer() {
		return bossCustomer;
	}

	public void setBossCustomer(BossCustomer bossCustomer) {
		this.bossCustomer = bossCustomer;
	}
}