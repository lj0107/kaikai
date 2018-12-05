package com.qtong.afinance.module.service.portal;

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
import com.qtong.afinance.module.dao.portal.BossCustomerDao;
import com.qtong.afinance.module.pojo.customer.BossCustomer;
/**
 * 客户登录Service
 *
 */
@Service
@Transactional
public class PortalLoginService {
	
	@Autowired
	private BossCustomerDao bossCustomerDao;
	@Autowired
	private AdimOrderService orderService;
	@Autowired
	private IJedisClient jedisClient;
	/**
	 * 客户登录
	 * @param loginName
	 * @param loginPwd
	 * @return
	 */
	public ResultObject login(String loginName,String loginPwd,String key,String code){
		String str = jedisClient.get(key);
		str=str.toLowerCase();
		code=code.toLowerCase();
		if(str==null||"".equals(str)){
			return ResultObject.build(2, "验证码超时,请刷新验证码");
		}
		if(!str.equals(code)){
			return ResultObject.build(2, "验证码不正确");
		}
		
		//校验用户名密码是否正确
		List<BossCustomer> list=bossCustomerDao.getCustomers(loginName);
		//取用户信息
		if(list==null||list.isEmpty()){
			return ResultObject.build(1, "用户名或密码错误！");
		}
		BossCustomer customer=list.get(0);
		//校验密码
		if(!customer.getLoginPwd().equals(loginPwd)){
			return ResultObject.build(1, "用户名或密码错误！");
		}
		//校验用户是否过期
		if(orderService.getOrdeProductName2(customer.getCustomerNumber())){
			return ResultObject.build(1, "用户过期");
		}
		
		//判断当前用户是否首次登录
		if(customer.getUpdpwdTime().equals(customer.getOperateTime())){//首次登录
			return new ResultObject(0, "1", customer);
		}
		
		//判断当前用户密码是否以持续30天未更换
		if(DateUtil.getDifferDay(new Date(), -30).getTime()>DateUtil.toDate(customer.getUpdpwdTime().toString(),DateUtil.YYYY_MM_DD_HH_MM_SS).getTime()){
			return new ResultObject(0, "2", customer);
		}
		
		return ResultObject.ok(customer);
		
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
		//60秒后删除key值为key1的数据（也就是过期）
		jedisClient.expire(str, 60);
		Map<String,String> map=new HashMap<>();
		map.put("key", str);//redis库中的key值
		map.put("code", randomCode.toString());//验证码
		
		return map;
	}
	
}
