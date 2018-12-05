package com.qtong.afinance.module.service.product;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qtong.afinance.core.dao.PageData;
import com.qtong.afinance.module.dao.product.AdminProTypeDao;

@Service
@Transactional
public class AdminProTypeService {
	@Autowired
	private AdminProTypeDao adminProTypeDao;

	/**
	 * 产品品类别管理查询
	 * 
	 * @param request
	 * @param response
	 */
	public String getAdminProTypeList(String pageIndex) {
		PageData pageData = adminProTypeDao.getAdminProTypeList(pageIndex);
		String jsonString = JSON.toJSONString(pageData, SerializerFeature.DisableCircularReferenceDetect,
				SerializerFeature.WriteDateUseDateFormat);
		return jsonString;
	}

	/**
	 * 产品品类别管理判断是否能删除
	 * 
	 * @param id
	 * @return
	 */
	public int deleteAdminProType(String str) {
		JSONObject parseObject = JSON.parseObject(str);
		String packageCode = parseObject.get("packageCode").toString();
		String lev = parseObject.get("lev").toString();
		int i = 0;
		int s=0;
		if("2".equals(lev)) {
			i = adminProTypeDao.getProductById(packageCode);
		}
		if("1".equals(lev)) {
			s = adminProTypeDao.getProduct(packageCode, lev);
			if(s==0) {
				i= adminProTypeDao.getProductById(packageCode);
				s=i;
			}
		}
		if(i == 0&&s==0) {
			return 0;
		}else {
			return 1;
		}
	}
	/**
	 * 产品品类别管理删除
	 * 
	 * @param id
	 * @return
	 */
	public int deleteAdminProType2(String str) {
		int j = adminProTypeDao.deleteAdminProType(str);
		return j;
	}

	/**
	 * 产品类别管理查询name</br>
	 * 判断数据库里是否有重复的name
	 * 
	 * @param name
	 * @return
	 */
	public int getNameByAdminProType(String name) {

		JSONObject parseObject = JSON.parseObject(name);
		String name1 = parseObject.get("name1").toString();
		String parentName = parseObject.get("parentName").toString();
		String lev = parseObject.get("lev").toString();
		
		return adminProTypeDao.getNameByAdminProType(name1,parentName,lev);
	}

	/**
	 * 产品品类别管理添加
	 *  private Integer id;
	 *  private String name;//类别名称
	 *  private String lev;//所属层级
	 *  private String parentName;//上级名称
	 *  private Integer parentId;//上级id
	 *  private String create_time;//创建时间
	 *  private String update_time;//更新时间
	 *  private Integer user_id;//操作人id
	 * @param jsonstr
	 * @return
	 */
	public int addAdminProType(String jsonstr) {
		String parentName = "";
		String parentId = "";
		JSONObject parseObject = JSON.parseObject(jsonstr);
		String name = parseObject.get("name").toString();
		String lev = parseObject.get("lev").toString();
		String user_id = parseObject.get("user_id").toString();
		String typeCode="";
		if("2".equals(lev)){
			 parentName = parseObject.get("parentName").toString();
			 parentId = parseObject.get("parentId").toString();
		}
		char id='A';
		Integer valueOf = null;
		char value;
		String ids="";
		//查询一级类别的Type_code
		if("1".equals(lev)) {
			typeCode = adminProTypeDao.getTypeCode();
			if("".equals(typeCode)||typeCode.equals(null)) {
				typeCode="1";
			}
			try {
				valueOf = Integer.valueOf(typeCode);
			} catch (NumberFormatException e) {
				value=typeCode.charAt(0);
				value++;
				ids = String.valueOf(value);
			}
			if(valueOf!=null) {
				if(valueOf<9) {
					valueOf++;
					ids=String.valueOf(valueOf);
				}else {
					ids=String.valueOf(id);
				}
			}
			
		}
		if("2".equals(lev)||typeCode.equals(null)) {
			String nameId = adminProTypeDao.getTypeCodeById(parentName);
			char n = nameId.charAt(0);
			typeCode = adminProTypeDao.getTypeCode2(parentName);
			if("".equals(typeCode)) {
				ids=""+n+"1";
			}else {
				String reg="^\\d+$";
				if(typeCode.matches(reg)) {
					valueOf = Integer.valueOf(typeCode);
					String num=""+n+"9";
					if(valueOf<Integer.valueOf(num)) {
						valueOf ++;
						ids = String.valueOf(valueOf);
					}else {
						char charAt = typeCode.charAt(1);
						if(57==(int)charAt) {
							charAt=65;
						}else if(90>(int)charAt){
							charAt++;
						}else {
							return 0;//超过Z  返回0;
						}
						ids = String.valueOf(typeCode.charAt(0))+String.valueOf(charAt);
					}
				}
				else {
					char charAt = typeCode.charAt(1);
					if(57==(int)charAt) {
						charAt=65;
					}else if(90>(int)charAt){
						charAt++;
					}else {
						return 0;//超过Z  返回0;
					}
					ids = String.valueOf(typeCode.charAt(0))+String.valueOf(charAt);
				}
			}
			
		}
		return adminProTypeDao.addAdminProType(ids,name,lev,parentName,parentId,user_id);
	}

	/**
	 * 查询总数
	 * 
	 * @return
	 */
	public int getAdminProTypeCount() {
		return adminProTypeDao.getAdminProTypeCount();
	}
}
