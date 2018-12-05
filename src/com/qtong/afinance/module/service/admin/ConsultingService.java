package com.qtong.afinance.module.service.admin;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qtong.afinance.core.dao.PageData;
import com.qtong.afinance.core.util.DateUtil;
import com.qtong.afinance.module.dao.admin.ConsultingDao;
import com.qtong.afinance.module.pojo.consulting.Consulting;

/**
 * 后台管理-项目咨询管理
 *
 */
@Service("adminConsultingService")
@Transactional
public class ConsultingService {
	
	@Autowired
	private ConsultingDao ConsultingDao;
	
	
    
    /****************************后台管理-项目咨询**************************************************/
    
    /**
     * 查询所有项目咨询信息
     * @return 查到的项目咨询信息集合
     */
    public List<Consulting> queryAll(){
    	return ConsultingDao.queryAll();
    }
    
    
    /**
	 * -批量删除
	 * @return
	 */
	public int deleteAll(String id) {
		String d[] = id.split(",");
		List<String> list = new ArrayList<String>();	         
		for (int i = 0; i < d.length; i++) {
		     list.add(d[i]);	             
		}
		int i=ConsultingDao.deleteAll(list);		
		return i;		
	}
    
    
    /**
     * 根据id查询项目咨询详细信息
     * @param id 
     * @return 详情
     */
    public String queryById(int id) {
    	Consulting consulting = ConsultingDao.queryById(id);
    	JSON.DEFFAULT_DATE_FORMAT = "yyyy年MM月dd日 HH:mm:ss";
		String jsonString = JSON.toJSONString(consulting, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);
		return jsonString;
    }
    
    /**
     * 根据id修改项目咨询处理状态
     * @param id
     */
    public int updateState(int id,String result) {
    	
    	Timestamp updateTime = Timestamp.valueOf(DateUtil.toStr(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS));
    	
    	return ConsultingDao.updateState(id,result,updateTime);
    }
    
 
    
    /**
     * 根据条件查询项目咨询集合
     * @param json 前台传的json字符串
     * @return
     */
    public String queryByPage(String pageIndex,String state,String product,String job,String companyName,String name){
    	
    	PageData pageData = new PageData();
    	pageData.setPageIndex(Integer.parseInt(pageIndex));
    	
    	PageData queryByPage = ConsultingDao.queryByPage(pageData,  state, product, job, companyName, name );
    	JSON.DEFFAULT_DATE_FORMAT = "yyyy年MM月dd日 HH:mm:ss";
 		String jsonString = JSON.toJSONString(queryByPage, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);
 		return jsonString;
    }
}
