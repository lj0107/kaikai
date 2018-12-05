package com.qtong.afinance.module.service.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qtong.afinance.module.dao.admin.AdminResourcesDao;
import com.qtong.afinance.module.dao.admin.CustomerDao;
import com.qtong.afinance.module.dao.portal.AdminOrderDao;
import com.qtong.afinance.module.dao.portal.AdminRatioDao;
import com.qtong.afinance.module.dao.product.AdminBossProDao;
import com.qtong.afinance.module.dao.urlGuard.AfinUrlGuardDao;
import com.qtong.afinance.module.pojo.admin.AdminResources;
import com.qtong.afinance.module.pojo.customer.BossCustomer;
import com.qtong.afinance.module.pojo.product.AdminOrder;
/**
 * 用户代办
 *
 */
@Service
@Transactional
public class WaitManageService {
	
	@Autowired
	private AdminOrderDao orderDao;
	@Autowired
	private AdminResourcesDao resourcesDao;
	@Autowired
	private AfinUrlGuardDao urlGuardDao;
	@Autowired
	private AdminBossProDao bossProDao;
	@Autowired
	private AdminRatioDao ratioDao;
	@Autowired
	private CustomerDao customerDao;
	
	/**
	 * 获取用户所有待办事项
	 * @return
	 */
	public List<Map<String, Object>> waitManageTable(String userId) {
		
		List<Map<String, Object>> result=new ArrayList<Map<String,Object>>();
		
		//查询出用户所有权限
		List<AdminResources> resList = resourcesDao.selUserRes(userId);
		
		for (AdminResources adminResources : resList) {
			Map<String, Object> map=new HashMap<>();
			if(adminResources.getMark()==991){//待审核恶意网址
				int count=urlGuardDao.getCountByState("102");
				if(count!=0){
					map.put("count", count);//数量
					map.put("mark", 991);//标识
					map.put("url", adminResources.getUrl());//
					result.add(map);
				}
			}
			if(adminResources.getMark()==992){//待下发恶意网址
				int count=urlGuardDao.getCountByState("104");
				if(count!=0){
					map.put("count", count);//数量
					map.put("mark", 992);//标识
					map.put("url", adminResources.getUrl());//
					result.add(map);
				}
			}
			if(adminResources.getMark()==993){//待拦截恶意网址
				int count=urlGuardDao.getCountByState("105");
				if(count!=0){
					map.put("count", count);//数量
					map.put("mark", 993);//标识
					map.put("url", adminResources.getUrl());//
					result.add(map);
				}
			}
			if(adminResources.getMark()==994){//待审核误报网址
				int count=urlGuardDao.getCountByState("201");
				if(count!=0){
					map.put("count", count);//数量
					map.put("mark", 994);//标识
					map.put("url", adminResources.getUrl());//
					result.add(map);
				}
			}
			if(adminResources.getMark()==995){//待下发误报网址
				int count=urlGuardDao.getCountByState("203");
				if(count!=0){
					map.put("count", count);//数量
					map.put("mark", 995);//标识
					map.put("url", adminResources.getUrl());//
					result.add(map);
				}
			}
			if(adminResources.getMark()==996){//待解除误报网址
				int count=urlGuardDao.getCountByState("204");
				if(count!=0){
					map.put("count", count);//数量
					map.put("mark", 996);//标识
					map.put("url", adminResources.getUrl());//
					result.add(map);
				}
			}
			if(adminResources.getMark()==4){//待创建账号客户
				List<BossCustomer> customers = customerDao.getCustomers();
				int count=0;
				for (BossCustomer bossCustomer : customers) {
					if(bossCustomer.getLoginName()==null||bossCustomer.getLoginName().equals("")){
						count++;
					}
				}
				if(count!=0){
					map.put("count", count);//数量
					map.put("mark", 4);//标识
					map.put("url", adminResources.getUrl()+"?flag=1");///
					result.add(map);
				}
			}
			if(adminResources.getMark()==998){//待关联产品
				int count=bossProDao.getCountByState("1");
				if(count!=0){
					map.put("count", count);//数量
					map.put("mark", 998);//标识
					map.put("url", adminResources.getUrl()+"?product_state=1");//
					result.add(map);
				}
			}
			if(adminResources.getMark()==999){//待审批事项          
				int count=ratioDao.getCountByState("1");
				if(count!=0){
					map.put("count", count);//数量
					map.put("mark", 999);//标识
					map.put("url", adminResources.getUrl());//
					result.add(map);
				}
			}
			if(adminResources.getMark()==990){//待设置分成比例的订单
				
				
				List<AdminOrder> orders = orderDao.getOrders();
				int count=0;
				for (AdminOrder order : orders) {
					if(order.getProductRatio()!=0){
						count++;
					}
				}
				if(count!=0){
					map.put("count", count);//数量
					map.put("mark", 990);//标识
					map.put("url", adminResources.getUrl()+"?flag=1");//
					result.add(map);
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 获取用户所有待办事项
	 * @return
	 */
	public Integer waitManageCount(String userId) {
		
		int allcount=0;
		
		//查询出用户所有权限
		List<AdminResources> resList = resourcesDao.selUserRes(userId);
		
		for (AdminResources adminResources : resList) {
			if(adminResources.getMark()==991){//待审核恶意网址
				int count=urlGuardDao.getCountByState("102");
				if(count!=0)
					allcount++;
			}
			if(adminResources.getMark()==992){//待下发恶意网址
				int count=urlGuardDao.getCountByState("104");
				if(count!=0)
					allcount++;
			}
			if(adminResources.getMark()==993){//待拦截恶意网址
				int count=urlGuardDao.getCountByState("105");
				if(count!=0)
					allcount++;
			}
			if(adminResources.getMark()==994){//待审核误报网址
				int count=urlGuardDao.getCountByState("201");
				if(count!=0)
					allcount++;
			}
			if(adminResources.getMark()==995){//待下发误报网址
				int count=urlGuardDao.getCountByState("203");
				if(count!=0)
					allcount++;
			}
			if(adminResources.getMark()==996){//待解除误报网址
				int count=urlGuardDao.getCountByState("204");
				if(count!=0)
					allcount++;
			}
			if(adminResources.getMark()==4){//待创建账号客户
				List<BossCustomer> customers = customerDao.getCustomers();
				int count=0;
				for (BossCustomer bossCustomer : customers) {
					if(bossCustomer.getLoginName()==null||bossCustomer.getLoginName().equals("")){
						count++;
					}
				}
				
				if(count!=0)
					allcount++;
			}
			if(adminResources.getMark()==998){//待关联产品
				int count=bossProDao.getCountByState("1");
				if(count!=0)
					allcount++;
			}
			if(adminResources.getMark()==999){//待审批事项          
				int count=ratioDao.getCountByState("1");
				if(count!=0)
					allcount++;
			}
			if(adminResources.getMark()==990){//待设置分成比例的订单
				List<AdminOrder> orders = orderDao.getOrders();
				int count=0;
				for (AdminOrder order : orders) {
					if(order.getProductRatio()!=0){
						count++;
					}
				}
				if(count!=0)
					allcount++;
			}
		}
		
		return allcount;
	}
}
