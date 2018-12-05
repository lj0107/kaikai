package com.qtong.afinance.module.pojo.product;



import java.sql.Timestamp;
import java.util.Date;

public class AdminProRatioInfo {
    private Integer id;

    private String subOrderNum;

    private String operState;

    private String ratioBefore;

    private String ratioAfter;

    private String applyUser;

    private String confirmUser;

    private Timestamp applyTime;

    private Timestamp confirmTime;

    private Timestamp updateTime;

    private String suggest;

    private String state;
    
    private String optMatter;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubOrderNum() {
        return subOrderNum;
    }

    public void setSubOrderNum(String subOrderNum) {
        this.subOrderNum = subOrderNum == null ? null : subOrderNum.trim();
    }

    public String getOperState() {
        return operState;
    }

    public void setOperState(String operState) {
        this.operState = operState == null ? null : operState.trim();
    }

    public String getRatioBefore() {
        return ratioBefore;
    }

    public void setRatioBefore(String ratioBefore) {
        this.ratioBefore = ratioBefore == null ? null : ratioBefore.trim();
    }

    public String getRatioAfter() {
        return ratioAfter;
    }

    public void setRatioAfter(String ratioAfter) {
        this.ratioAfter = ratioAfter == null ? null : ratioAfter.trim();
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser == null ? null : applyUser.trim();
    }

    public String getConfirmUser() {
        return confirmUser;
    }

    public void setConfirmUser(String confirmUser) {
        this.confirmUser = confirmUser == null ? null : confirmUser.trim();
    }

    public Timestamp getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Timestamp applyTime) {
        this.applyTime = applyTime;
    }

    public Timestamp getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Timestamp confirmTime) {
        this.confirmTime = confirmTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest == null ? null : suggest.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

	public String getOptMatter() {
		return optMatter;
	}

	public void setOptMatter(String optMatter) {
		this.optMatter = optMatter;
	}

	
}