package com.qtong.afinance.module.service.portal;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qtong.afinance.core.dao.PageData;
import com.qtong.afinance.module.dao.portal.AdminRatioDao;
import com.qtong.afinance.module.pojo.product.AdminProRatioInfo;

/**
 * 审批
 *
 */
@Service
@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED)
public class AdminRatioService {

	@Autowired
	private AdminRatioDao adminRatioDao;

	/**
	 * 查询未审批的
	 * @param reqMess
	 * @return
	 */
	public String getRatioList(String reqMess) {
		JSONObject parseObject = JSON.parseObject(reqMess);
		String pageIndex = parseObject.getString("pageIndex").toString();
		String apply_user = parseObject.getString("apply_user").toString();
		String apply_timeStart = parseObject.getString("apply_timeStart").toString();
		String apply_timeEnd = parseObject.getString("apply_timeEnd").toString();
		PageData pageData = adminRatioDao.getRatioList(pageIndex,apply_user,apply_timeStart,apply_timeEnd);		
		JSON.DEFFAULT_DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";
		String jsonString = JSON.toJSONString(pageData, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);					
		return jsonString;
	}
	/**
	 * 查询审批的
	 * @param reqMess
	 * @return
	 */
	public String getRatioList1(String reqMess) {
		JSONObject parseObject = JSON.parseObject(reqMess);
		String pageIndex = parseObject.getString("pageIndex").toString();
		String apply_user = parseObject.getString("apply_user").toString();
		String apply_timeStart = parseObject.getString("apply_timeStart").toString();
		String apply_timeEnd = parseObject.getString("apply_timeEnd").toString();
		String confirm_user = parseObject.getString("confirm_user").toString();
		String confirm_timeStart = parseObject.getString("confirm_timeStart").toString();
		String confirm_timeEnd = parseObject.getString("confirm_timeEnd").toString();
		String state = parseObject.getString("state").toString();
		PageData pageData = adminRatioDao.getRatioList1(pageIndex,apply_user,apply_timeStart,apply_timeEnd,confirm_user,confirm_timeStart,confirm_timeEnd,state);		
		JSON.DEFFAULT_DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";
		String jsonString = JSON.toJSONString(pageData, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);					
		return jsonString;
	}

	/**
	 * 查询未审批详细
	 * @param id
	 * @return
	 */
	public String getRatioInfo(String id) {
		List<Map<String, Object>> list = adminRatioDao.getRatioInfo(id);
		String code = "";
		//Object  obj=null;
		for (Map<String, Object> map : list) {
			code = map.get("pro_code").toString();
			System.out.println(map.get("pro_code"));
		}
		//code = JSON.toJSONString(obj);
		Map<String, Object> p = adminRatioDao.getPartners(code);
		list.add(p);
		String jsonString = JSON.toJSONString(list);
		return jsonString;
	}

	/**
	 * 审批通过
	 * @param jsonstr
	 * @return
	 */
	public int updateRatioArgee(String jsonstr) {
		int i=0;
		JSONObject parseObject = JSON.parseObject(jsonstr);
		String sub_order_num = parseObject.get("sub_order_num").toString();
		String ratio_after = parseObject.get("ratio_after").toString();
		String confirm_user = parseObject.getString("roleName").toString();
		//String confirm_user="张三";
		int j = adminRatioDao.updateRatioArgee(sub_order_num,confirm_user);
		if(j>0){
			i = adminRatioDao.updateOrder(sub_order_num,ratio_after);
			//获取分成比例表同意的数据
			AdminProRatioInfo ratio =  adminRatioDao.getRatio(sub_order_num);
			//添加日志表
			i = adminRatioDao.updateRatioInfo(ratio);
		}
		return i;
	}
	/**
	 * 审批驳回
	 * @param jsonstr
	 * @return
	 */
	public int updateRatioReject(String jsonstr) {
		int i=0;
		JSONObject parseObject = JSON.parseObject(jsonstr);
		String sub_order_num = parseObject.get("sub_order_num").toString();
		String suggest = parseObject.get("suggest").toString();
		String confirm_user = parseObject.getString("roleName").toString();
		//String confirm_user="张三";
		i = adminRatioDao.updateRatioReject(sub_order_num,suggest,confirm_user);
			//获取分成比例表同意的数据
		AdminProRatioInfo ratio =  adminRatioDao.getRatio(sub_order_num);
		//添加日志表
		i = adminRatioDao.updateRatioInfo(ratio);
		return i;
	}
}
