package com.qtong.afinance.module.service.product;

import java.util.HashMap;
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
import com.qtong.afinance.module.dao.product.AdminBossProDao;

/**
 * boss产品service
 * 
 *
 */
@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED)
@Service
public class AdminBossService {

	@Autowired
	private AdminBossProDao adminBossProDao;
	

	/**
	 * boss查询
	 * 
	 * @param str
	 * @return
	 */
	@Transactional(propagation =Propagation.NOT_SUPPORTED)
	public String getAdminBossProList(String str) {
		JSONObject parseObject = JSON.parseObject(str);
		String pageIndex = parseObject.get("pageIndex").toString();
		String type_name = parseObject.get("type1").toString();
		String parent_name = parseObject.get("type2").toString();
		String cnName = parseObject.get("cnName").toString();
		String productCode = parseObject.get("productCode").toString();
		String syncTimeStart = parseObject.get("syncTimeStart").toString();
		String syncTimeOut = parseObject.get("syncTimeOut").toString();
		String state = parseObject.get("state").toString();
		List<Map<String, Object>> list = adminBossProDao.getAdminBossProList(pageIndex, type_name, cnName, productCode,
				syncTimeStart, syncTimeOut, state,parent_name);

		/**
		 * 查询boss总数
		 * @return
		 */
		Map m = new HashMap<>();
		int bossCount = adminBossProDao.getBossCount(pageIndex, type_name, cnName, productCode,
				syncTimeStart, syncTimeOut, state,parent_name);
		m.put("bossCount", bossCount);
		m.put("list", list);
		String jsonString = JSON.toJSONString(m);
		return jsonString;
	}

	/**
	 * 查询产品
	 * @return
	 */
	@Transactional(propagation =Propagation.NOT_SUPPORTED)
	public String getProduct(String str) {
		JSONObject parseObject = JSON.parseObject(str);
		String type1 = parseObject.get("type1").toString();
		String type2 = parseObject.get("type2").toString();
		List<Map<String, Object>> list = adminBossProDao.getProduct(type1,type2);
		String jsonString = JSON.toJSONString(list, SerializerFeature.DisableCircularReferenceDetect,
				SerializerFeature.WriteDateUseDateFormat);
		return jsonString;
	}

	/**
	 * boss修改</br>
	 * 此方法只局限在boss只支持两级标签编码情况下
	 * @param str
	 * @return 
	 */
	public int updateBoss(String str) {
		JSONObject parseObject = JSON.parseObject(str);
		String bossCode = parseObject.get("bossCode").toString();
		String bossName = parseObject.get("bossName").toString();
		String type11 = parseObject.get("type11").toString();
		String type22 = parseObject.get("type22").toString();
		String cp = parseObject.get("cp").toString();
		//通部boss编码到product上并且关联
		int i = adminBossProDao.updateProduct(bossCode,bossName,cp,type11,type22);
		//修改关联状态
		int b = adminBossProDao.updateBoss(bossCode);
		if(i>0&&b>0){
			return 1;
		}
		return 0;
	}

	/**
	 * 取消关联
	 * 
	 * @param str
	 * @return
	 */
	public int updateQxgl(String str) {
		JSONObject parseObject = JSON.parseObject(str);
		String bossCode = parseObject.get("bossCode").toString();
		String code = parseObject.get("code").toString();
		int i = adminBossProDao.updateQxglProduct(code);
		//修改关联状态
		int b = adminBossProDao.updateQxglBoss(bossCode);
		if(i>0&&b>0) {
			return 1;
		}else {
			return 0;
		}
	}
}
