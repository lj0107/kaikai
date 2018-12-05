package com.qtong.afinance.module.service.interf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qtong.afinance.core.component.IJedisClient;
import com.qtong.afinance.core.dao.PageData;
import com.qtong.afinance.core.domain.ResultObject;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qtong.afinance.module.dao.interf.InterfaceDao;
import com.qtong.afinance.module.pojo.interf.Interface;
import com.qtong.afinance.module.pojo.interf.Product;

@Service
@Transactional
public class InterfaceService {
	@Autowired
	private InterfaceDao interfaceDao;
	
	@Autowired
	private IJedisClient jedisClient;
	
	
	/**
     * 增加产品
     * @param addProduct
     * @return
     */
	public int insertProduct(Product addProduct) {
		//同步信息到redis
		Map<String,Object> map=new HashMap<>();
		map.put("product_code", addProduct.getProductCode());
		map.put("secret_id", addProduct.getSecretId());
		map.put("secret_key", addProduct.getSecretKey());
		map.put("url", addProduct.getInterfaceUrl());
		jedisClient.hset("partner_product", addProduct.getProductCode(), JSON.toJSONString(map));		
		return interfaceDao.insertProduct(addProduct);		
	}
	/**
	 * 产品列表
	 * @param selectProduct
	 * @return
	 */
	public String selectProduct(String pageIndex,Product selectProduct) {
		
		String productCode = selectProduct.getProductCode();//产品编号
		String secretId = selectProduct.getSecretId();//合作伙伴分配账号
		String secretKey = selectProduct.getSecretKey();//合作伙伴分配秘钥
		String interfaceUrl = selectProduct.getInterfaceUrl();//合作伙伴分配路径
		
		PageData sProduct = interfaceDao.selectProduct(pageIndex,productCode,secretId,secretKey,interfaceUrl);
		String interfStr = JSON.toJSONString(sProduct, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);		
		return interfStr;
	}
	/**
	 * 删除产品
	 * @param id
	 * @return
	 */
	public int deleteProductById(String id) {		
		Product i = interfaceDao.selectProductByid(id);		
		jedisClient.hdel("partner_product", i.getProductCode());		
		return interfaceDao.deleteProductById(Integer.parseInt(id));
	}
	/**
	 * 更改产品
	 * @param updateProduct
	 * @return
	 */
	public int updateProduct(Product updateProduct) {
		
		//同步信息到redis
		Map<String,Object> map=new HashMap<>();
	
		map.put("product_code", updateProduct.getProductCode());
		map.put("secret_id", updateProduct.getSecretId());
		map.put("secret_key", updateProduct.getSecretKey());
		map.put("url", updateProduct.getInterfaceUrl());
		
		jedisClient.hset("partner_product", updateProduct.getProductCode(), JSON.toJSONString(map));	
		return interfaceDao.updateProduct(updateProduct);
	}
	/**
	 * 查询产品
	 * @param id
	 * @return
	 */
	public String selectProductByid(String id) {
		
		String interfStr = JSON.toJSONString(interfaceDao.selectProductByid(id), SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);		
		return interfStr;
	}
	
    /**
     * 增加接口
     * @param addInterface
     * @return
     */
	public int insertCoPartner(Interface addInterface) {
		//同步信息到redis
		Map<String,Object> map=new HashMap<>();
		map.put("afin_account", addInterface.getAfinAccount());
		map.put("afin_key", addInterface.getAfinKey());
		map.put("service_code", addInterface.getServiceCode());
		map.put("customer_number", addInterface.getCustomerNumber());
		map.put("secret_id", addInterface.getSecretId());
		map.put("secret_key", addInterface.getSecretKey());
		map.put("partner_id", addInterface.getPartnerId());
		map.put("url", addInterface.getInterfaceUrl());
		map.put("account_type", addInterface.getAccountType());
		map.put("state", 0);//默认可用
		map.put("value", addInterface.getValue());//默认可用
		
		jedisClient.hset("validate_customer", addInterface.getAfinAccount(), JSON.toJSONString(map));
		
		addInterface.setState(0);
		return interfaceDao.insert(addInterface);
		
		
	}
	/**
	 * * 服务编码相同时，和金融分配账号不能重复
	 * @param addInterface
	 * @return
	 */
	public int distinctAfinCount(Interface addInterface) {
		String afinAccount = addInterface.getAfinAccount();
		String serviceCode = addInterface.getServiceCode();
		return interfaceDao.distinctAfinCount(afinAccount,serviceCode);
	}
	/**
	 * 接口管理列表和条件查询
	 * @param addInterface
	 * @return
	 */
	public String selectInterf(String pageIndex,Interface addInterface) {
		String afinAccount = addInterface.getAfinAccount();//和金融分配账号
		String serviceCode = addInterface.getServiceCode();//服务编码
		String secretId = addInterface.getSecretId();//合作伙伴分配账号
		String customerName = addInterface.getCustomerName();//客户名称
		String partnerName = addInterface.getPartnerName();//合作伙伴名称
		Integer state = addInterface.getState();//状态
		String accountType = addInterface.getAccountType();//状态
		PageData selectInterf = interfaceDao.selectInterf(pageIndex,afinAccount,serviceCode,secretId,customerName,partnerName,state,accountType);
		String interfStr = JSON.toJSONString(selectInterf, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);		
		return interfStr;
	}
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public int deleteInterFById(String id) {
		
		Interface i = interfaceDao.selectByid(id);
		
		jedisClient.hdel("validate_customer", i.getAfinAccount());
		
		return interfaceDao.deleteInterFById(Integer.parseInt(id));
	}
	/**
	 * 更改
	 * @param updateInterface
	 * @return
	 */
	public int updateInterf(Interface updateInterface) {
		
		
		//同步信息到redis
		Map<String,Object> map=new HashMap<>();
		map.put("afin_account", updateInterface.getAfinAccount());
		map.put("afin_key", updateInterface.getAfinKey());
		map.put("service_code", updateInterface.getServiceCode());
		map.put("customer_number", updateInterface.getCustomerNumber());
		map.put("secret_id", updateInterface.getSecretId());
		map.put("secret_key", updateInterface.getSecretKey());
		map.put("partner_id", updateInterface.getPartnerId());
		map.put("account_type", updateInterface.getAccountType());
		map.put("url", updateInterface.getInterfaceUrl());
		map.put("state", updateInterface.getState());//默认可用
		map.put("value", updateInterface.getValue());//默认可用
		jedisClient.hset("validate_customer", updateInterface.getAfinAccount(), JSON.toJSONString(map));

		
		
		
		return interfaceDao.updateInterf(updateInterface);
	}
	/**
	 * 查询
	 * @param id
	 * @return
	 */
	public String selectByid(String id) {
		
		String interfStr = JSON.toJSONString(interfaceDao.selectByid(id), SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);		
		return interfStr;
	}
	
	
	
	/**
	 * 查询[包括两部分：接口信息+当前接口管理产品信息]
	 * @param id
	 * @return
	 */
	public Interface queryDetailById(String id) {
		
		Interface i = interfaceDao.selectByid(id);
		String serviceCode = i.getServiceCode();
		String customerNumber = i.getCustomerNumber();
		List<Map<String,Object>> list = interfaceDao.queryBindProducts(serviceCode, customerNumber);
		i.setBindProducts(list);
		return i;
	}
	
	
	
	
	
	/**
	 * 查询当前用户订购的所有产品
	 * @param pageIndex 当前页
	 * @param state 产品状态
	 * @param productName1  一级类别
	 * @param productName2 二级类别
	 * @param productName3 产品名称
	 * @param productEn 产品英文名
	 * @param customerNumber 客户编码
	 * @param serviceCode 服务编码
	 * @return
	 */
	public PageData queryProducts(Integer pageIndex,Integer state,String productName1,String productName2,String productName3,String productEn,String customerNumber,String serviceCode){
		//1.从数据库中查出产品信息
		PageData pageData = new PageData();
		pageData.setPageIndex(pageIndex);
		
		PageData data = interfaceDao.queryProducts(pageData,state,productName1,productName2,productName3,productEn,customerNumber);
		
		//2.判断当前产品是否已分配服务编码：如果已分配，判断服务编码是否为传过来的服务编码，如果是则回显，否则不展示；如果未分配，展示
		List<Map<String, Object>> list = (List<Map<String, Object>>) data.getLst();
		Iterator<Map<String, Object>> it=list.iterator();
		while (it.hasNext()) {
			Map<String, Object> map=it.next();
			String sc = (String) map.get("service_code");
			
			if (sc==null||sc.isEmpty()) {
				map.put("service_code", 1);//表示未绑定服务编码
			}else {
				if(sc.equals(serviceCode)){
					map.put("service_code", 0);//表示已绑定服务编码
				}else {
					it.remove();
				}
			}
			
			
		}
		
		return data;
	}
	
	/**
	 * 保存绑定服务编码
	 * @param customerNumber 客户编码
	 * @param serviceCode 服务编码
	 * @param productCodes 绑定产品【绑定、解除绑定】
	 * @return
	 */
	public ResultObject saveServiceCode(String customerNumber,String serviceCode, String[] productCodes ,String[] unProductCodes) {
		List<Map<String, String>> bind=new ArrayList<Map<String,String>>();
		
		//1、从redis中查出当前客户订购的所有产品
		String json = jedisClient.hget("order_relation", customerNumber);
		
		Map map = JSON.parseObject(json, Map.class);
		List list = (List)map.get("list");
		
		
		
		
		//2、绑定操作：已绑定其它serviceCode，不操作；未绑定serviceCode，绑定操作；已绑定serviceCode，但当前unProductCodes中存在，productCodes中不存在，解除绑定
		Iterator<Map<String, Object>> it=list.iterator();
		while (it.hasNext()) {
			Map<String, Object> m=it.next();
			String sc = (String) m.get("service_code");
			
			if (sc==null||sc.isEmpty()) {//表示未绑定服务编码，绑定
				m.put("service_code", serviceCode);
				HashMap<String,String> hashMap = new HashMap<>();
				hashMap.put("key", (String) m.get("product_order_id"));
				hashMap.put("value", serviceCode);
				bind.add(hashMap);
			}else {
				if(sc.equals(serviceCode)){
					boolean flag=false;
					for (String code : productCodes) {
						if (code.equals(sc)) {
							flag=true;
						}
					}
					if(!flag){
						for (String code : unProductCodes) {
							if (code.equals(sc)) {//解除绑定
								m.put("service_code", "");
								HashMap<String,String> hashMap = new HashMap<>();
								hashMap.put("key", (String) m.get("product_order_id"));
								hashMap.put("value", "");
								bind.add(hashMap);
							}
						}
					}
				}
			}
		}
		
		//3.同步数据到redis
		map.put("list", list);
		jedisClient.hset("order_relation", customerNumber, JSON.toJSONString(map));
		
		//4.持久化
		interfaceDao.saveServiceCode(bind);
		
		return ResultObject.ok();
	}
	
}
