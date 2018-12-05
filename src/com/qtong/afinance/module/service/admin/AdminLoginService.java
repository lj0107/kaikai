package com.qtong.afinance.module.service.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.qtong.afinance.core.component.IJedisClient;
import com.qtong.afinance.core.domain.ResultObject;
import com.qtong.afinance.core.util.DateUtil;
import com.qtong.afinance.module.dao.admin.AdminResourcesDao;
import com.qtong.afinance.module.dao.admin.AdminUserDao;
import com.qtong.afinance.module.pojo.admin.AdminResources;
import com.qtong.afinance.module.pojo.admin.AdminUser;
/**
 * 用户登录Service
 *
 */
@Service
@Transactional
public class AdminLoginService {
	
	
	@Autowired
	private AdminUserDao adminUserDao;
	@Autowired
	private AdminResourcesDao adminResourcesDao;
	@Autowired
	private IJedisClient jedisClient;
	
	/**
	 * 登录验证
	 * */
	public ResultObject login(String mobile,String password,String key,String code){
		
		String str = jedisClient.get(key);
		str=str.toLowerCase();
		code=code.toLowerCase();
		if(str==null||"".equals(str)){
			return ResultObject.build(2, "验证码超时,请刷新验证码");
		}
		if(!str.equals(code)){
			return ResultObject.build(2, "验证码不正确");
		}
		
		
		////校验用户名密码是否正确
		List<AdminUser> list=adminUserDao.getUsers(mobile);
		//取用户信息
		if(list==null||list.isEmpty()){
			return ResultObject.build(1, "用户名或密码错误！");
		}
		AdminUser user=list.get(0);
		//校验密码
		if(!user.getPassword().equals(password)){
		
			return ResultObject.build(1, "用户名或密码错误！");
		}
		
		
		//如果认证成功，同时也将roleName也加入到pojo
		List<AdminUser> queryRoleNames=adminUserDao.queryRoleNames(user.getId());
		String roleName = "";
		//拼出管理员角色名称 逗号分割
		for (AdminUser adminUser1 : queryRoleNames) {
			roleName += adminUser1.getRoleName()+",";
		}
		int length = roleName.length()-1;
		String substring = roleName.substring(0, length);
		user.setRoleName(substring);
		
		//判断当前用户密码是否以持续30天未更换
		if(DateUtil.getDifferDay(new Date(),-30).getTime()>DateUtil.toDate(user.getUpdpwdTime().toString(), DateUtil.YYYY_MM_DD_HH_MM_SS).getTime()){//首次登录
			
			return new ResultObject(0, "2", user);
			
		}
		
		return ResultObject.ok(user);
		
	}
	
	
	
	public Boolean isAuth(String userId){
		List<AdminResources> selUserRes = adminResourcesDao.selUserRes(userId);//获取用户权限
		for (AdminResources adminResources : selUserRes) {
			String resName=adminResources.getName();
			if(resName.equals("业务金额")){//有无此权限
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * 生成验证码
	 * */
	public Map<String,String> getAuthCode(String type){

		int codeCount = 4;
		// 创建一个随机数生成器类
		Random random = new Random();
		// randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
		char[] blends = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
				'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
				'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6',
				'7', '8', '9' };
		char[] englise = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
				'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
				'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
		char[] numbers = {'0', '1', '2', '3', '4', '5', '6','7', '8', '9' };
		char[] codeSeq=null;
		if("blend".equals(type)){
			codeSeq=blends;
		}else if("number".equals(type)){
			codeSeq=numbers;
		}else{
			codeSeq=englise;
		}
		StringBuffer randomCode = new StringBuffer();
		for (int i = 0; i < codeCount; i++) {
			// 得到随机产生的验证码数字。
			String strRand = String.valueOf(codeSeq[random.nextInt(codeSeq.length)]);
			// 将产生的四个随机数组合在一起。
			randomCode.append(strRand);
		}
		String uuid = UUID.randomUUID().toString();
		Long currentTimeMillis = System.currentTimeMillis();
		String str=currentTimeMillis.toString()+uuid;
		//将验证码放入redis
		jedisClient.set(str, randomCode.toString());
		//8秒后删除key值为key1的数据（也就是过期）
		jedisClient.expire(str, 60);
		Map<String,String> map=new HashMap<>();
		map.put("key", str);//redis库中的key值
		map.put("code", randomCode.toString());//验证码
		
		return map;
	}
	
	

}
