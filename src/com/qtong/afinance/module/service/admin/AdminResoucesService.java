package com.qtong.afinance.module.service.admin;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;








import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.qtong.afinance.module.dao.admin.AdminResourcesDao;
import com.qtong.afinance.module.pojo.admin.AdminResources;

/**
 * 资源service
 */
@Service
@Transactional
public class AdminResoucesService {
	
	@Autowired
	private AdminResourcesDao resDao;
	
	/**
	 * 
	 * 查用户权限为首页块状菜单展示提供数据（此处展示模块数目最多6个。如果不够6个，顺序排列，后面模块不展示，点击功能入口，跳到对应功能模块的第一个最低级菜单页。）
	 * @param userId
	 * @return
	 */
	public String getRes4Show(String userId){
		//一级菜单
		List<AdminResources> resultlist=new ArrayList<AdminResources>();
		//所有菜单
		List<AdminResources> reslist=resDao.selUserRes(userId);
		
		
		
		for (int i = 0; i < reslist.size(); i++) {
			//一级菜单
			if (reslist.get(i).getParentId().equals("0")&&reslist.get(i).getMark()!=0) {
				resultlist.add(reslist.get(i));
			}
		}
		/*if(resultlist.size()>6){
			resultlist=resultlist.subList(0, 6);
		}*/
		
		for (AdminResources res : resultlist) {
			res.setChildRes(this.getChild(res.getId().toString(), reslist));
	    }
		
		//改造resource
		for (AdminResources resource : resultlist) {
			AdminResources copy=new AdminResources();
			copy.setUrl(resource.getUrl());
			copy.setChildRes(resource.getChildRes());
			String url="";
			while (true) {
				if(copy.getChildRes()!=null&&copy.getChildRes().size()>0){
					copy=copy.getChildRes().get(0);
				}else{
					url=copy.getUrl();
					break;
				}
			}
			
			resource.setUrl(url);
			resource.setChildRes(null);
		}
		
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		
	    jsonMap.put("menu", resultlist);
	    String resultStr=JSON.toJSONString(jsonMap);
		return resultStr;
	}
	
	
	/**
	 * 查所有res
	 * @return
	 */
	public String selAll(){
		List<AdminResources> resultlist=new ArrayList<AdminResources>();
		
		List<AdminResources> reslist=resDao.selAll();
		for (int i = 0; i < reslist.size(); i++) {
			//一级菜单
			if (reslist.get(i).getParentId().equals("0")) {
				resultlist.add(reslist.get(i));
			}
		}
		for (AdminResources res : resultlist) {
			res.setChildRes(this.getChild(res.getId().toString(), reslist));
	    }
		
		Map<String,Object> jsonMap = new HashMap<String,Object>();
	    jsonMap.put("menu", resultlist);
	    String resultStr=JSON.toJSONString(jsonMap);
		return resultStr;
	}
	
	/**
	 * 查角色下所有res
	 * @return
	 */
	public String selResByRole(String roleId){
		List<AdminResources> resultlist=new ArrayList<AdminResources>();
		
		List<AdminResources> reslist=resDao.selRoleRes(roleId);
		for (int i = 0; i < reslist.size(); i++) {
			//一级菜单
			if (reslist.get(i).getParentId().equals("0")) {
				resultlist.add(reslist.get(i));
			}
		}
		for (AdminResources res : resultlist) {
			res.setChildRes(this.getChild(res.getId().toString(), reslist));
	    }
		
		Map<String,Object> jsonMap = new HashMap<String,Object>();
	    jsonMap.put("menu", resultlist);
	    String resultStr=JSON.toJSONString(jsonMap);
		return resultStr;
	}
	
	/**
	 * 查用户权限
	 * @param userId
	 * @return
	 */
	public String getUserRes(String userId){
		List<AdminResources> resultlist=new ArrayList<AdminResources>();
		
		List<AdminResources> reslist=resDao.selUserRes(userId);
		for (int i = 0; i < reslist.size(); i++) {
			//一级菜单
			if (reslist.get(i).getParentId().equals("0")) {
				resultlist.add(reslist.get(i));
			}
		}
		for (AdminResources res : resultlist) {
			res.setChildRes(this.getChild(res.getId().toString(), reslist));
	    }
		
		Map<String,Object> jsonMap = new HashMap<String,Object>();
	    jsonMap.put("menu", resultlist);
	    String resultStr=JSON.toJSONString(jsonMap);
		return resultStr;
	}
	
	/**
	 * 查角色权限
	 * @param roleId
	 * @return
	 */
	public String getRoleRes(String roleId){		
		List<AdminResources> resultlist=new ArrayList<AdminResources>();
		
		List<AdminResources> reslist=resDao.selRoleRes(roleId);
		for (int i = 0; i < reslist.size(); i++) {
			//一级菜单
			if (reslist.get(i).getParentId().equals("0")) {
				resultlist.add(reslist.get(i));
			}
		}
		for (AdminResources res : resultlist) {
			res.setChildRes(this.getChild(res.getId().toString(), reslist));
	    }
		
		Map<String,Object> jsonMap = new HashMap<String,Object>();
	    jsonMap.put("menu", resultlist);
	    String resultStr=JSON.toJSONString(jsonMap);
		return resultStr;
	}
	
	/**
	 * 根据角色Id查询权限
	 * @param roleId
	 * @return
	 */
	public String getResByRoleId(String roleId){
		List<Integer> resIds=new ArrayList<>();
		List<AdminResources> list= resDao.selRoleRes(roleId);
		for (AdminResources adminResources : list) {
			resIds.add(adminResources.getId());
		}
		String resultStr=JSON.toJSONString(resIds);
		return resultStr;
	}
	
	/**
	 * 递归取子集res
	 * @param id
	 * @param reslist
	 * @return
	 */
	private List<AdminResources> getChild(String id, List<AdminResources> reslist){
		List<AdminResources> childList = new ArrayList<>();
		
	    for (AdminResources res : reslist) {
	        // 遍历所有节点，将父菜单id与传过来的id比较
	        if (res.getParentId().equals(id)) {
	        	childList.add(res);
	        }
	    }
	    // 把子菜单的子菜单再循环一遍
	    for (AdminResources res : childList) {  
	    	// 没有url子菜单还有子菜单
	        if (res.getUrl()==null||res.getUrl().equals("")) {
	        	res.setChildRes(getChild(res.getId().toString(), reslist));
	        }
	    } 
	    // 递归退出条件
	    if (childList.size() == 0) {
	        return null;
	    }
	    return childList;
	}


	
	
}
