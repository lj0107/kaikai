package com.qtong.afinance.core.util;

import java.util.concurrent.ConcurrentHashMap;
/**
 * 客户管理
 *
 */
public class CustMap {
	//证件类型
	public static final ConcurrentHashMap<String, String> icTypeHashMap = new ConcurrentHashMap<String, String>();
	//公司性质
	public static final ConcurrentHashMap<String, String> orgTypeHashMap = new ConcurrentHashMap<String, String>();
	//客户服务等级
	public static final ConcurrentHashMap<String, String> slHashMap = new ConcurrentHashMap<String, String>();
	//年营业额
	public static final ConcurrentHashMap<String, String> turnoverHashMap = new ConcurrentHashMap<String, String>();
	//员工数
	public static final ConcurrentHashMap<String, String> ecHashMap = new ConcurrentHashMap<String, String>();
	public  void taskstart() {
		icTypeHashMap.put("1", "组织机构代码证");
		icTypeHashMap.put("2", "营业执照");
		icTypeHashMap.put("3", "登记证书");
		icTypeHashMap.put("4", "单位证明");
		icTypeHashMap.put("5", "办事机构注册证");
		icTypeHashMap.put("6", "身份证");
		icTypeHashMap.put("7", "户口本");
		icTypeHashMap.put("8", "军官证");
		icTypeHashMap.put("9", "台港澳通行证");
		icTypeHashMap.put("10", "护照");
		icTypeHashMap.put("11", "统一社会信用代码");
		
		
		orgTypeHashMap.put("01", "国有企业（含国有股本主导）");
		orgTypeHashMap.put("02", "集体所有制企业");
		orgTypeHashMap.put("03", "股份制企业（不含国有股本主导）");
		orgTypeHashMap.put("04", "个体企业（包括无限责任公司）");
		orgTypeHashMap.put("05", "中外合资、合作企业");
		orgTypeHashMap.put("06", "外商（含港澳台资）独资企业");
		orgTypeHashMap.put("07", "党政军机构");
		orgTypeHashMap.put("08", "其他企业");
		 
		slHashMap.put("1", "金牌级");
		slHashMap.put("2", "银牌级");
		slHashMap.put("3", "铜牌级");
		slHashMap.put("4", "标准级");
		
		turnoverHashMap.put("1", "年收入>=30000万元");
		turnoverHashMap.put("2", "3000万元<=年收入<30000万元");
		turnoverHashMap.put("3", "1000万元<=年收入<3000万元");
		turnoverHashMap.put("4", "年收入<1000万元");
		
		ecHashMap.put("1", "员工人数>=1000");
		ecHashMap.put("2", "100 <=员工人数<1000");
		ecHashMap.put("3", "50 <=员工人数<100");
		ecHashMap.put("4", "员工人数<50");
	}
	
}
