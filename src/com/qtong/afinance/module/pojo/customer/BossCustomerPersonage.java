package com.qtong.afinance.module.pojo.customer;

import java.util.Date;
/**
 * 
 *客户信息从表（个人客户信息）
 */
public class BossCustomerPersonage {
	private static final long serialVersionUID = 1L;

    private String gender;//客户性别  1: 男 2: 女

    private String nationality;//客户民族

    private String marriedStatus;//客户婚姻状况  0-已婚，1-未婚

    private String education;//客户学历

    private String occupation;//客户职业

    private String salary;//客户薪水

    private String nickName;//客户昵称

    private BossCustomer bossCustomer;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality == null ? null : nationality.trim();
    }

    public String getMarriedStatus() {
        return marriedStatus;
    }

    public void setMarriedStatus(String marriedStatus) {
        this.marriedStatus = marriedStatus == null ? null : marriedStatus.trim();
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education == null ? null : education.trim();
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation == null ? null : occupation.trim();
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary == null ? null : salary.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

	public BossCustomer getBossCustomer() {
		return bossCustomer;
	}

	public void setBossCustomer(BossCustomer bossCustomer) {
		this.bossCustomer = bossCustomer;
	}
}