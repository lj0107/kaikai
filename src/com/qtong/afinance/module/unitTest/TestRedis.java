package com.qtong.afinance.module.unitTest;


import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.qtong.afinance.core.component.IJedisClient;
import com.qtong.afinance.core.util.SerializeUtil;
import com.qtong.afinance.module.pojo.admin.AdminUser;
import com.qtong.afinance.module.service.bigdata.AuthorizationCheckService;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:WebContent/WEB-INF/applicationContext.xml","file:WebContent/WEB-INF/applicationContext-redis.xml"})
public class TestRedis {
	@Autowired
	private IJedisClient jedisClient;
	@Autowired
	private AuthorizationCheckService authorizationCheckService;
	
	/**
	 * 将字符串存到redis
	 */
	@Test
	public void insertStringKey(){
		//String queryOne = authorizationCheckService.queryOne("111", "111");
		//System.out.println(queryOne);
	}
	
	/**
	 * 将对象转成Json后存到Hash集合
	 */
	@Test
	public void insertObjectJsonHash(){
		AdminUser user=new AdminUser();
		user.setId(1);
		user.setName("张三22222");
		user.setMobile("18821322134214");
		
		jedisClient.hset("afin:admin:user", user.getId()+"", JSON.toJSONString(user));
	}
	
	/**
	 * 从Hash集合中获取json字符串并转成对象
	 */
	@Test
	public void getObjectJsonHash(){
		String jsonUser = jedisClient.hget("afin:admin:user", 1+"");
		
		AdminUser user=JSON.parseObject(jsonUser, AdminUser.class);
		
		System.out.println("User.Name="+user.getName());
		
	}
	
	/**
	 * 从Hash集合中删除数据
	 */
	@Test
	public void deleteForHash(){
		jedisClient.hdel("afin:admin:user", 2+"");
	}
	
	/**
	 * 将对象序列化后存到redis
	 */
	@Test
	public void insertObjectSerialize(){
		AdminUser user=new AdminUser();
		user.setId(2);
		user.setName("张三22222");
		user.setMobile("18821322134214");
		
		
		jedisClient.set(String.valueOf(2).getBytes(), SerializeUtil.serialize(user));
		
	}
	
	/**
	 * 将对象序列化取出
	 */
	@Test
	public void getObjectSerialize(){
		
		byte[] byt=jedisClient.get(String.valueOf(2).getBytes());
		AdminUser user=(AdminUser) SerializeUtil.unserizlize(byt);
		System.out.println("Name="+user.getName());
	}
	
	
	/**
	 * 对值加一操作
	 */
	@Test
	public void incrKey(){
		
		String key="userId";
		
		if(jedisClient.exists(key)){
			jedisClient.incr(key);
		}else{
			jedisClient.set(key, 0+"");
			jedisClient.incr(key);
		}
	}
	
}

