package com.qtong.afinance.module.pojo.heweishi;

import java.io.Serializable;

/**
 * 位置基地-城市编码比对子表城市区号对应的实体类
 *
 */
public class LbCityInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String citycode;//城市区号
	private String cityComparId;
	
	public String getCityComparId() {
		return cityComparId;
	}

	public void setCityComparId(String cityComparId) {
		this.cityComparId = cityComparId;
	}

	private LbCityComparison lbCityComparison;//关联关系

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCitycode() {
		return citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}

	public LbCityComparison getLbCityComparison() {
		return lbCityComparison;
	}

	public void setLbCityComparison(LbCityComparison lbCityComparison) {
		this.lbCityComparison = lbCityComparison;
	}

	@Override
	public String toString() {
		return "LbCityInfo [id=" + id + ", citycode=" + citycode + "]";
	}
	
	
}

