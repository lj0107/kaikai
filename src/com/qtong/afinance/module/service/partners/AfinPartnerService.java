package com.qtong.afinance.module.service.partners;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qtong.afinance.core.dao.PageData;
import com.qtong.afinance.module.dao.partners.AfinPartnerDao;
import com.qtong.afinance.module.pojo.partners.AfinPartner;

@Service
@Transactional
public class AfinPartnerService {
	
	@Autowired
	private AfinPartnerDao partnersDao;

    public String queryAllByPage(int pageIndex){
    	PageData pageData = new PageData();
    	pageData.setPageIndex(pageIndex);
    	PageData queryByPage = partnersDao.queryByPage(pageData);
    	JSON.DEFFAULT_DATE_FORMAT = "yyyy年MM月dd日 HH:mm:ss";
		String jsonString = JSON.toJSONString(queryByPage, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);
		return jsonString;
    }

    /**
     * 查询合作伙伴是否能删除
     * 
     * @param id
     * @return
     */
	public int deletePartnersById(int id) {
		//根据id去查询产品表有没有关联
		int p = partnersDao.getProductById(id);
		//根据id去查接口表有没有关联
		int f = partnersDao.getInterfaceById(id);
		if(p>0||f>0) {
			return 0;
		}else {
			return 1;
		}
	}
	
	/**
	 * 删除合作伙伴
	 * @param id
	 * @return
	 */
	public int deletePartnersById2(int id) {
		return partnersDao.deletePartnersById(id);
	}

	public int insertPartners(AfinPartner businessPartners) {
		int insertPartners = partnersDao.insertPartners(businessPartners);
		return insertPartners;
	}

	public String selectPartnersById(int id) {
		AfinPartner businessPartners = partnersDao.selectPartnersById(id);
		String BusinessStr = JSON.toJSONString(businessPartners);
		
		return BusinessStr;
	}

	public int updatePartners(AfinPartner businessPartners) {
		int updatePartners = partnersDao.updateRecentNews(businessPartners);
		return updatePartners;
	}

	public int selectPartnersName(String name) {
		int selectPartnersName = partnersDao.selectPartnersName(name);
		return selectPartnersName;
	}

	public String getSiteGuardInterface() {
		 List<Map<String, Object>> siteGuardInterfaceList = partnersDao.getSiteGuardInterface();
			String jsonString = JSON.toJSONString(siteGuardInterfaceList);		
			System.out.println("jsonString:"+jsonString);
		 return jsonString;
	}

	/**
	 * 查询所有合作伙伴 下拉框接口
	 * @return
	 */
	public String getAllPartnerInterface() {
		 List<Map<String, Object>> siteGuardInterfaceList = partnersDao.getAllPartner();
			String jsonString = JSON.toJSONString(siteGuardInterfaceList);		
			System.out.println("jsonString:"+jsonString);
		 return jsonString;
	}

}
