package com.qtong.afinance.module.service.product;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qtong.afinance.core.util.RandomNumber;
import com.qtong.afinance.core.util.SnowflakeIdWorker;
import com.qtong.afinance.module.dao.portal.AdminOrderSubDao;
import com.qtong.afinance.module.dao.product.AdminProductDao;
import com.qtong.afinance.module.dao.statistics.OrderStatsDao;
import com.qtong.afinance.module.service.bill.AdminBillService;

/**
 * 产品Service
 * 
 *
 */
@Service
@Transactional
public class AdminProductService {

	@Autowired
	private AdminProductDao adminProductDao;
	@Autowired
	private OrderStatsDao orderStatsDao;
	@Autowired
	private AdminOrderSubDao adminOrderSubDao;
	@Autowired
	private AdminBillService adminBillService;
	

	/**
	 * 产品管理查询
	 * 
	 * @param str
	 * @return
	 */
	public String getAdminProductList(String str) {
		JSONObject parseObject = JSON.parseObject(str);
		String type_name = parseObject.get("type_name").toString();// 一级分类名称
		String cn_name = parseObject.get("cn_name").toString();// 产品名字
		String parent_name = parseObject.get("parent_name").toString();// 二级分类名称
		String code = parseObject.get("code").toString();// 产品编码
		String proCode = parseObject.get("product_code").toString();// boss产品编号
		String partner_id = parseObject.get("partners_id").toString();// 产品来源(合作伙伴)
		String state = parseObject.get("state").toString();// 产品关联
		String pageIndex = parseObject.get("pageIndex").toString();
		String type_name1 ="";
		List<Map<String, Object>> query = adminProductDao.getAdminProductList(type_name, cn_name, parent_name, code,
				proCode, partner_id, state, pageIndex);
		Map m = new HashMap<>();
		int bossCount = adminProductDao.getAdminProductCount(type_name, cn_name, parent_name, code,
				proCode, partner_id, state);
		m.put("bossCount", bossCount);
		m.put("list", query);
		String jsonString = JSON.toJSONString(m);
		return jsonString;
		
	}

	/**
	 * 查询一级类
	 * 
	 * @return
	 */
	public String getTypeNameByAdminProType() {
		List<Map<String, Object>> m = adminProductDao.getTypeNameByAdminProType();
		String jsonString = JSON.toJSONString(m);
		return jsonString;
	}

	/**
	 * 查询二级类
	 * 
	 * @param jsonstr
	 * @return
	 */
	public String getTypeName(String jsonstr) {
		JSONObject parseObject = JSON.parseObject(jsonstr);
		String lev = parseObject.getString("lev").toString();
		String type_name = parseObject.getString("type_name").toString();
		List<Map<String, Object>> m = adminProductDao.getTypeName(lev, type_name);
		String jsonString = JSON.toJSONString(m);
		return jsonString;
	}

	/**
	 * 查询来源
	 * 
	 * @param jsonstr
	 * @return
	 */
	public String getPartners() {
		List<Map<String, Object>> m = adminProductDao.getPartners();
		String jsonString = JSON.toJSONString(m);
		return jsonString;
	}

	/**
	 * 添加
	 * 
	 * @param jsonstr
	 * @return
	 */
	public int addAdminProduct(String jsonstr) {
		JSONObject parseObject = JSON.parseObject(jsonstr);
		String type1 = parseObject.getString("type1").toString();
		String type2 = parseObject.getString("type2").toString();
		String cn_name = parseObject.getString("cn_name").toString();
		String descriptione = parseObject.getString("description").toString();
		String partners_id = parseObject.getString("partners_id").toString();
		String user_id = parseObject.getString("user_id").toString();
		String en_name = parseObject.getString("en_name").toString();
		// 生成唯一的产品code 如：A45623
		int[] reult1 = RandomNumber.randomCommon(10000, 99999, 1);
		String code = "";
		System.out.println(reult1[0]);
		if("1".equals(type1)) {
		    code = "D"+reult1[0];
		}else if("2".equals(type1)){
			code = "W"+reult1[0];
		}else if("3".equals(type1)){
			code = "L"+reult1[0];
		}
		return adminProductDao.addAdminProduct(code, type1, type2, cn_name, en_name, descriptione, partners_id,user_id);
	}
	/**
	 * 修改
	 * 
	 * @param jsonstr
	 * @return
	 */
	public int updateAdminProduct(String jsonstr) {
		String type="";
		JSONObject parseObject = JSON.parseObject(jsonstr);
		String code = parseObject.getString("code").toString();
		String type1 = parseObject.getString("type1").toString();
		String type2 = parseObject.getString("type2").toString();
		String cn_name = parseObject.getString("cn_name").toString();
		String en_name = parseObject.getString("en_name").toString();
		String descriptione = parseObject.getString("description").toString();
		String partners_id = parseObject.getString("partners_id").toString();
		String is_connect = parseObject.get("is_connect").toString();// 产品关联
		if(!type1.isEmpty()){
			List<Map<String, Object>> proType = adminProductDao.getPackage_code(type1,type2);
			for (Map<String, Object> map : proType) {
				Object object = map.get("package_code");
				type = JSON.toJSONString(object);
			}
		}else{
			type="";
		}
		int i = adminProductDao.updateAdminProduct(code, type, cn_name, en_name, descriptione, partners_id);
		//判断是否关联，如果关联修改
		if(is_connect.equals("0")) {
			String bossCode =  "";
			if(i>0) {
				//查询产品关联后的产品编码
				 List<Map<String,Object>> adminProductList = adminProductDao.getAdminProductList(null, null, null, code, null, null, null, "1");
				 for (Map<String, Object> map : adminProductList) {
					 bossCode = map.get("product_code").toString();
				}
				//调账单、试金石、话单接口修改产品名称
				adminBillService.upProEnAndProName(bossCode,cn_name,en_name);
				orderStatsDao.updateProName(cn_name, bossCode);
				adminOrderSubDao.updateProName(cn_name, bossCode);
			}
		}
		return i;
	}

	/**
	 * 根据code获取详细信息
	 * 
	 * @param code
	 * @return
	 */
	public String getAdminProductInfo(String code) {
		Map map = new HashMap<>();
		List<Map<String, Object>> productList = adminProductDao.getAdminProductInfo(code);
		String codeId="";
		for (Map<String, Object> m : productList) {
			Object object = m.get("product_code");
			if(object!=null) {
				codeId = m.get("product_code").toString();
			}
			//codeId = JSON.toJSONString(object);
		}
		if(!codeId.equals("")) {
			List<Map<String, Object>> oderList =adminProductDao.getOder(codeId);
			map.put("oderList", oderList);
		}
		map.put("productList", productList);
		String jsonString = JSON.toJSONString(map);
		return jsonString;
	}

	/**
	 * 根据code删除
	 * 
	 * @param code
	 * @return
	 */
	public int delAdminProduct(String code) {
		return adminProductDao.delAdminProduct(code);
	}

	/**
	 * 查询名字
	 * @param cn_name
	 * @return
	 */
	public int getCnNameByAdminProductCount(String cn_name) {
		// TODO Auto-generated method stub
		return adminProductDao.getCnNameByAdminProductCount(cn_name);
	}
}
