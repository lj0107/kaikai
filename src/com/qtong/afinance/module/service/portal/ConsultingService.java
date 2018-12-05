package com.qtong.afinance.module.service.portal;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qtong.afinance.core.component.IJedisClient;
import com.qtong.afinance.core.domain.ResultObject;
import com.qtong.afinance.module.dao.portal.ConsultingDao;
import com.qtong.afinance.module.pojo.consulting.Consulting;

/**
 * 门户-项目咨询
 *
 */
@Service("portalConsultingService")
@Transactional
public class ConsultingService {
	
	@Autowired
	private ConsultingDao ConsultingDao;
	
	@Autowired
	private IJedisClient jedisClient;
	
	
	/**
	 * 添加项目咨询信息
	 * @param map 添加的 项目咨询信息map
	 */
    public ResultObject insertConsulting(String code,String keys,Consulting Consulting) {
    	
    	String str = jedisClient.get(keys);
		str=str.toLowerCase();
		code=code.toLowerCase();
		if(str==null||"".equals(str)){
			return ResultObject.build(2, "验证码超时,请刷新验证码");
		}
		if(!str.equals(code)){
			return ResultObject.build(2, "验证码不正确");
		}
		
		
    	
    	Timestamp createTime = new Timestamp((new Date()).getTime());

    	Consulting.setCreateTime(createTime);//设置创建时间
    	Consulting.setState(1);//设置默认状态为未出炉
    	
    	ConsultingDao.insertConsulting(Consulting);//添加到数据库中
    	
    	return ResultObject.build(0, "success");
    	
    }
    
}
