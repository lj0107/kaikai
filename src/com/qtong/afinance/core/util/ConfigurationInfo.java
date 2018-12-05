package com.qtong.afinance.core.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 配置信息
 *
 */
public class ConfigurationInfo {
	static Resource resource = new ClassPathResource("/configs.properties");
	static Properties props = new Properties();
	public static String bocomSecretId;//平台分配给交行的身份id
	public static String afinSecretId4sjz;//试金石分配给和金融的身份id
	public static Map<Integer,String> msgInfo;//返回码信息！！
	static{
		try {
			props = PropertiesLoaderUtils.loadProperties(resource);
			
			//平台分配给交行的身份id
			bocomSecretId=props.getProperty("bocomSecretId");
			//试金石分配给和金融的身份id
			afinSecretId4sjz=props.getProperty("afinSecretId4sjz");
			
			//返回码信息！！
			msgInfo=new HashMap<Integer,String>();
			//和金融平台返回响应报文中的响应码与描述
			msgInfo.put(new Integer(0000), "查询成功");
			msgInfo.put(new Integer(1001), "参数错误");
			msgInfo.put(new Integer(1002), "身份认证失败");
			msgInfo.put(new Integer(1003), "权限不足");
			msgInfo.put(new Integer(1004), "用户状态异常");
			msgInfo.put(new Integer(1005), "查询请求标识重复");
			msgInfo.put(new Integer(2001), "查询失败");
			msgInfo.put(new Integer(4001), "产品未发布");
			msgInfo.put(new Integer(5000), "查询失败");
			msgInfo.put(new Integer(5001), "接口暂时无法访问");

				
			//短信发送返回码与描述
			msgInfo.put(new Integer(0), "预发送成功！");
			msgInfo.put(new Integer(10001), "短信内容不能为空！");
			msgInfo.put(new Integer(10002), "应用服务名不能为空！");
			msgInfo.put(new Integer(10003), "应用服务密码不能为空！");
			msgInfo.put(new Integer(10004), "发送号码不能为空！");

			msgInfo.put(new Integer(11001), "发送号码格式不正确！");
			msgInfo.put(new Integer(11002), "IP 不合法！");
			msgInfo.put(new Integer(11003), "定制信息过长！");
			msgInfo.put(new Integer(11004), "wap短信url长度过长！");

			msgInfo.put(new Integer(12001), "密码错误或该应用没有注册！");
			msgInfo.put(new Integer(12002), "没有发送wap短信权限！");
			msgInfo.put(new Integer(12003), "应用发送短信功能开关没有开启！");
			msgInfo.put(new Integer(12004), "当前时段您无权发送短信！");
			msgInfo.put(new Integer(12005), "客户端IP获取错误！");
			msgInfo.put(new Integer(12006), "短信发送量已超出每分钟规定数量！");
			msgInfo.put(new Integer(12007), "短信发送量已超出每小时规定数量！");
			msgInfo.put(new Integer(12008), "短信发送量已超出每天规定数量！");

			msgInfo.put(new Integer(19001), "验证时间规定数量错误！");
			msgInfo.put(new Integer(19002), "未知错误！");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
