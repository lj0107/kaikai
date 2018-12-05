package com.qtong.afinance.module.service.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qtong.afinance.core.dao.PageData;
import com.qtong.afinance.module.dao.admin.AdvertDao;
import com.qtong.afinance.module.pojo.admin.BusinessAdvert;

/**
 * 推荐位service
 * 
 *
 */
@Service
@Transactional
public class AdvertService {

	@Autowired
	private AdvertDao advertDao;

	/**
	 * 推荐位根据id查询
	 * 
	 * @param id
	 * @return
	 */
	public String advertUpdate(String id) {
		BusinessAdvert advert = advertDao.advertUpdate(id);
		String jsonString = JSON.toJSONString(advert, SerializerFeature.DisableCircularReferenceDetect,
				SerializerFeature.WriteDateUseDateFormat);
		return jsonString;
	}

	/**
	 * 查询总数
	 * 
	 * @param id
	 * @return
	 */
	public int getAdvertCount(String advert_address) {
		return advertDao.getAdvertCount(advert_address);
	}

	/**
	 * 推荐位上线或者下线
	 * 
	 * @param advert
	 * @return
	 */
	public int updateAdvertStates(String str) {
		JSONObject o = JSON.parseObject(str);
		String id = o.get("id").toString();
		String advert_states = o.get("advert_states").toString();
		String advert_address = o.get("advert_address").toString();
		String advert_sort = o.get("advert_sort").toString();
		String states = "";
		if ("1".equals(advert_states)) {
			states = "2";
		}
		if ("2".equals(advert_states)) {
			states = "1";
		}
		// 查询已上线的条数
		List<Map<String, Object>> list = advertDao.advertCount(advert_address);
		if ("5".equals(advert_sort)) {
			String string = "";
			int i = 0;
			// 判断是否有广告上线
			if (list.size() > 0) {
				for (Map<String, Object> map : list) {
					++i;
					if (i == 5) {
						string = map.get("id").toString();
					}
				}
				advertDao.update(string);
			}
		}
		int i = advertDao.updateAdvertStates(id, states);
		updateDefaultAdvertOnlineState(advert_address);
		return i;
	}

	/**
	 * 推荐位搜索及列表
	 * 
	 * @param advert
	 * 
	 */
	public String advertFind(BusinessAdvert advert) {
		PageData pageData = advertDao.advertFind(advert.getAdvert_title(), advert.getCreate_time(),
				advert.getCustom_time(), advert.getAdvert_states(), advert.getAdvert_address(), advert.getId());
		String jsonString = JSON.toJSONString(pageData, SerializerFeature.DisableCircularReferenceDetect,
				SerializerFeature.WriteDateUseDateFormat);
		return jsonString;
	}

	/**
	 * 推荐位删除
	 * 
	 * @param id
	 * @return
	 */
	public int advertDelete(String str) {
		JSONObject jasonObject = JSONObject.parseObject(str);
		int i = advertDao.advertDelete(jasonObject.get("id").toString());
		updateDefaultAdvertOnlineState(jasonObject.get("advert_address").toString());
		return i;
	}

	/**
	 * 修改默认广告的上线下线
	 * 
	 * @param advert_address
	 */
	public void updateDefaultAdvertOnlineState(String advert_address) {
		List<Map<String, Object>> list3 = advertDao.advertCount(advert_address);
		// 查询默认广告的id
		List<Map<String, Object>> list2 = advertDao.getDefaultOnlineById(advert_address);
		String onlineId = "";
		for (Map<String, Object> map : list2) {
			onlineId = map.get("id").toString();
		}
		if (list3.size() > 0) {
			// 默认广告下线
			advertDao.defaultOnline("2", onlineId);
		} else {
			// 默认广告下线
			advertDao.defaultOnline("1", onlineId);
		}
	}

	/**
	 * 推荐位增加
	 * 
	 * @param advert
	 * @return
	 */
	public int addAdvert(BusinessAdvert advert) {
		if ("5".equals(advert.getCustom_time())) {
			String string = "";
			// 查询已上线的条数
			List<Map<String, Object>> list = advertDao.advertCount(advert.getAdvert_address());
			int i = 0;
			for (Map<String, Object> map : list) {
				++i;
				if (i == 5) {
					string = map.get("id").toString();
				}
			}
			advertDao.update(string);
		}
		int i = advertDao.addAdvert(advert);
		updateDefaultAdvertOnlineState(advert.getAdvert_address());
		return i;
	}

	/**
	 * 推荐位修改
	 * 
	 * @param advert
	 * @return
	 */
	public int updateAdvert(BusinessAdvert advert) {
		if ("5".equals(advert.getCustom_time())) {
			String string = "";
			// 查询已上线的条数
			List<Map<String, Object>> list = advertDao.advertCount(advert.getAdvert_address());
			int i = 0;
			for (Map<String, Object> map : list) {
				++i;
				if (i == 5) {
					string = map.get("id").toString();
				}
			}
			advertDao.update(string);
		}
		int i = advertDao.updateAdvert(advert);
		updateDefaultAdvertOnlineState(advert.getAdvert_address());
		return i;
	}

	/**
	 * 推荐位判断是否上线五个
	 * 
	 * @param advert_address
	 * @return
	 */
	public int advertIfFives(String advert_address) {
		return advertDao.advertIfFives(advert_address);
	}

	/**
	 * 推荐位查询广告
	 * 
	 * @param advert_address
	 * @return
	 */
	public String advertFindState(String advert_address) {
		List<Map<String, Object>> list = advertDao.advertFindState(advert_address);
		if (list.size() < 0 || list.isEmpty()) {
			list = advertDao.findDefault(advert_address);
		}
		String jsonString = JSON.toJSONString(list, SerializerFeature.DisableCircularReferenceDetect,
				SerializerFeature.WriteDateUseDateFormat);
		return jsonString;
	}
}
