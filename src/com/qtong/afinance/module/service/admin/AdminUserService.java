package com.qtong.afinance.module.service.admin;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qtong.afinance.core.dao.PageData;
import com.qtong.afinance.core.util.DateUtil;
import com.qtong.afinance.module.dao.admin.AdminUserDao;
import com.qtong.afinance.module.pojo.admin.AdminUser;

/**
 * 后台管理-管理员列表
 *
 */
@Service
@Transactional
public class AdminUserService {
	
	@Autowired
	private AdminUserDao adminUserDao;
	

	//分页查询所有
	/**
	 * 根据条件查所有
	 * @param jsonobject json字符串
	 * @return
	 */
	public String queryByPage(JSONObject json) {
		PageData pageData = new PageData();
    	
    	Object pageIndex = json.get("pageIndex");//获取当前页
    	if (pageIndex!=null && !"".equals(pageIndex)) {
			pageData.setPageIndex(Integer.parseInt(pageIndex.toString()));
		}
		
    	Object nameJson = json.get("name");//获取姓名
    	String name = null;//初始化name
    	if(nameJson!=null && !"".equals(nameJson)) {//若姓名不为空
    		name = nameJson.toString();//赋值给name
    	}
    	
    	Object departmentJson = json.get("department");//获取所属部门
    	String department = null;//初始化department
    	if(departmentJson!=null && !"".equals(departmentJson)) {//若所属部门不为空
    		department = departmentJson.toString();//赋值给department
    	}
    	Object jobJson = json.get("job");//获取所属部门
    	String job = null;//初始化department
    	if(jobJson!=null && !"".equals(jobJson)) {//若所属部门不为空
    		job = jobJson.toString();//赋值给department
    	}
    	
    	Object stateJson = json.get("state");//获取状态
    	String state = null;//初始化state
    	if(stateJson!=null && !"".equals(stateJson)) {//若状态不为空
    		state = stateJson.toString();//赋值给state
    	}
    	
    	Object mobileJson = json.get("mobile");//获取手机号
    	String mobile = null;//初始化mobile
    	if(mobileJson!=null && !"".equals(mobileJson)) {//若手机号不为空
    		mobile = mobileJson.toString();//赋值给mobile
    	}
    	
		 PageData adminUsers = getAdminUsers(pageData, name, department, job, mobile, state);
		 
		JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
 		String jsonString = JSON.toJSONString(adminUsers, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);
 		
 		return jsonString;
		
	}
	
	
	//拼用户的角色 以逗号分割
	public PageData getAdminUsers(PageData pageData,String name,String department,String job,String mobile,String state){
			
			//获取所有管理员id
			
			PageData pageQuery = adminUserDao.queryAllAdminUser(pageData, name, department, job, mobile, state);
			List<AdminUser> lst = (List<AdminUser>) pageQuery.getLst();
			
			//通过管理员id获取角色名称
			for (AdminUser adminUser : lst) {
				List<AdminUser> queryRoleNames = adminUserDao.queryRoleNames(adminUser.getId());
				//拼出管理员角色名称 逗号分割
				String roleName = "";
				for (AdminUser adminUser1 : queryRoleNames) {
					roleName += adminUser1.getRoleName()+",";
				}
				int length = roleName.length()-1;
				String substring = roleName.substring(0, length);
				adminUser.setRoleName(substring);
			}
			
			pageQuery.setLst(lst);
			
			return pageQuery;//返回list
		}
	
	
	
	
	
	//修改状态
	/**
	 * 修改管理员状态
	 * @param id 要修改的id
	 * @param state 状态：0启用 1禁止
	 * @return
	 */
	public int updateState(String json) {
		JSONObject jsonObject  = JSONObject.parseObject(json);
		String id = null;//初始化id
		String state = null;//初始化state
		
		Object idJson = jsonObject.get("id");
		if(idJson!=null && !"".equals(idJson)) {
			id  = idJson.toString();
		}
		Object stateJson = jsonObject.get("state");
		if(stateJson!=null && !"".equals(stateJson)) {
			state  = stateJson.toString();
		}
		
		Timestamp updateTime = Timestamp.valueOf(DateUtil.toStr(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS));
		
		return adminUserDao.changeUserState(id, state, updateTime);
	}
	
	
	//删除一行记录
	/**
	 * 根据id删除一行记录
	 * @param id
	 * @return 删除的条数
	 */
	public int deleteAdminUser(String id) {
		return adminUserDao.deleteAdminUser(id);
	}
	
	
	//查看详情
	/**
	 * 根据id查看详情
	 * @param id
	 * @return
	 */
	public String selAdminUserById(String id) {
		AdminUser selAdminUserById = adminUserDao.selAdminUserById(id);
		JSON.DEFFAULT_DATE_FORMAT = "yyyy年MM月dd日 HH:mm:ss";
 		String jsonString = JSON.toJSONString(selAdminUserById, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);
 		return jsonString;
	}
	
	//编辑
	public int updateAdminUser(AdminUser u) {
		
		u.setPassword(DigestUtils.md5DigestAsHex(u.getPassword().getBytes()));
		
		Timestamp updateTime = Timestamp.valueOf(DateUtil.toStr(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS));
		
		u.setUpdateTime(updateTime);
		u.setUpdpwdTime(updateTime);
		
		
		return adminUserDao.updateAdminUser(u);
	}
	//编辑
	public int update(AdminUser u) {
		
		Timestamp updateTime = Timestamp.valueOf(DateUtil.toStr(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS));
		u.setUpdateTime(updateTime);
		
		return adminUserDao.update(u);
	}
	//修改密码
	public int updatePassword(AdminUser u) {
		u.setPassword(DigestUtils.md5DigestAsHex(u.getPassword().getBytes()));
		u.setUpdpwdTime(Timestamp.valueOf(DateUtil.toStr(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS)));
		
		Timestamp updateTime = Timestamp.valueOf(DateUtil.toStr(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS));
		u.setUpdateTime(updateTime);
		
		return adminUserDao.updatePassword(u);
	}
	
	
	//添加一个管理员
	/**
	 * 添加一个管理员
	 * @param adminUser
	 * @return
	 */
	public int insertAdminUser(AdminUser u) {
		
		Timestamp createTime = new Timestamp((new Date()).getTime());
		u.setCreateTime(createTime);//设置添加管理员时间
		u.setUpdateTime(createTime);
		u.setUpdpwdTime(Timestamp.valueOf(DateUtil.toStr(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS)));
		u.setState(0);//设置管理员状态默认为0 正常状态
		u.setPassword(DigestUtils.md5DigestAsHex(u.getPassword().getBytes()));
		
		return adminUserDao.insertAdminUser(u);
		
		
	}
	
	/**
	 * 判断姓名是否存在
	 * @param name
	 * @return
	 */
	public String queryByName(String name) {
		Map<String,Boolean> map = new HashMap<String,Boolean>();
		
		List<AdminUser> queryByName = adminUserDao.queryByName(name);
		if(queryByName.size()>0) {
			map.put("isNameEmpty", true);//存在
		}else {
			map.put("isNameEmpty", false);//不存在
		}
		
		return JSON.toJSONString(map);
	}
	/**
	 * 判断姓名是否存在
	 * @param name
	 * @return
	 */
	public String queryByMobile(String mobile) {
		Map<String,Boolean> map = new HashMap<String,Boolean>();
		
		List<AdminUser> queryByMobile = adminUserDao.queryByMobile(mobile);
		if(queryByMobile.size()>0) {
			map.put("isMobileEmpty", true);//存在
		}else {
			map.put("isMobileEmpty", false);//不存在
		}
		
		return JSON.toJSONString(map);
	}

	

	
	
}
