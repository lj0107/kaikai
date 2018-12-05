package com.qtong.afinance.module.service.admin;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qtong.afinance.core.dao.PageData;
import com.qtong.afinance.core.domain.ResultObject;
import com.qtong.afinance.module.dao.admin.AdminRoleDao;
import com.qtong.afinance.module.pojo.admin.AdminRole;

@Service
@Transactional
public class AdminRoleService {
	
    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();
	
	@Autowired
	private AdminRoleDao roleDao;
		
	/**
	 * 分页查询
	 * @param pageIndex
	 * @return
	 */
	public String pageQuery(String pageIndex){
		PageData data=roleDao.pageQuery(pageIndex);
//		List<AdminRole> list= (List<AdminRole>) data.getLst();
		String jsonString = JSON.toJSONString(data);		
		return jsonString;
	}
		
	/**
	 * 新增角色
	 * @param role
	 * @return
	 */
	public ResultObject insert(String userId,AdminRole role) {
		List<AdminRole> list=roleDao.getRolesByName(role.getName());
		//如果角色名已存在，新增失败
		if(list!=null&&!list.isEmpty()){
			return ResultObject.build(1, "角色名已存在！");
		}
		role.setCreateTime(new Timestamp(System.currentTimeMillis()));
		role.setState(0);
		role.setCreator(userId);
		roleDao.insertRole(role);
		return ResultObject.ok();
	}
		
	/**
	 * 删除角色
	 * @param id
	 * @return
	 */
	public String delete(String id){
		roleDao.deleteRole(id);
		roleDao.removeRoleResRel(id);
		roleDao.removeUserRoleRelByRoleId(id);
		return "success";
	}
		
	/**
	 * 修改角色
	 * @param role
	 * @return
	 */
	public ResultObject update(AdminRole role) {
		List<AdminRole> list=roleDao.getRolesByName(role.getName());
		//如果角色名已存在，新增失败
		if(list!=null&&!list.isEmpty()){
			AdminRole adminRole = roleDao.selRoleById(String.valueOf(role.getId()));
			if(!adminRole.getName().equals(role.getName()))
				return ResultObject.build(1, "角色名已存在！");
		}
		roleDao.updateRole(role);
		return ResultObject.ok();
	}
	
		
	/**
	 * 根据id查角色
	 * @param id
	 * @return
	 */
	public String selById(String id) {		
		AdminRole role = roleDao.selRoleById(id);
		String jsonString = JSON.toJSONString(role);		
		return jsonString;
	}
	
	
	/**
	 * 查已分配和未分配该角色的user集合
	 * @param roleId
	 * @return
	 */
	public String allotUserToRole(String roleId){
		//无该角色的用户
		List userlist=roleDao.selUserIdAndName(roleId);
		//已有用户
		List list=roleDao.selUserByRoleId(roleId);
		Map map = new HashMap();
		map.put("left", userlist);
		map.put("right", list);
		String jsonString = JSON.toJSONString(map);		
		return jsonString; 
	}
	
	/**
	 * 角色绑定user
	 * @param roleId
	 * @param userId
	 * @return
	 */
	public String addUserRole(String reqMess){
		JSONObject obj=JSON.parseObject(reqMess);		
		String roleId=obj.getString("roleId");
		String substring = roleId.substring(2, roleId.length()-2);//截取id
		//先删除
		roleDao.removeUserRoleRelByRoleId(substring);
		JSONArray userId=obj.getJSONArray("userId");
		for(int i=0;i<userId.size();i++){
			roleDao.addUserRoleRel(userId.get(i).toString(), substring);
		}
		return "success";
	}
	
	/**
	 * 保存 角色-权限关系
	 * @param reqMess
	 * @return
	 */
	public String addRoleRes(String reqMess){
		JSONObject obj=JSON.parseObject(reqMess);	
		String roleId=obj.getString("roleId");
		//先删除
		roleDao.removeRoleResRel(roleId);
		
		
		JSONArray resId=null;
		
		try {
			resId=obj.getJSONArray("resId");
		} catch (Exception e) {
		}finally{
			if(resId==null){
				resId=new JSONArray();
				String string = obj.getString("resId");
				
				if (string==null||string.isEmpty()) {
					return "success";
				}else {
					resId.add(string);
				}
				
			}
		}
		
		roleDao.addRoleRes(roleId, resId);
		return "success"; 
	}
}
