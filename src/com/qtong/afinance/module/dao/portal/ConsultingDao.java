package com.qtong.afinance.module.dao.portal;


import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.qtong.afinance.core.dao.BaseJdbcDao;
import com.qtong.afinance.core.dao.TableInfo;
import com.qtong.afinance.module.pojo.consulting.Consulting;

/**
 * 门户-项目咨询DAO层
 *
 */
@Repository("portalConsultingDao")
public class ConsultingDao extends BaseJdbcDao{
	final org.slf4j.Logger logger = LoggerFactory.getLogger(ConsultingDao.class);

	/**
	 * 添加一条项目咨询记录
	 * @param Consulting
	 */
	public int insertConsulting(Consulting consulting) {
		StringBuffer sqlBuffer=new StringBuffer("insert into ");
		sqlBuffer.append(TableInfo.afin_consulting);
		sqlBuffer.append("(name,job,company_name,email,mobile,req_dep,create_time,state,product,update_time) values(?,?,?,?,?,?,?,?,?,?)");
		return this.getJdbcTemplate().update(sqlBuffer.toString(),
				consulting.getName(),
				consulting.getJob(),
				consulting.getCompanyName(),
				consulting.getEmail(),
				consulting.getMobile(),
				consulting.getReqDep(),
				consulting.getCreateTime(),
				consulting.getState(),
				consulting.getProduct(),
				consulting.getCreateTime());
	}
	
	
}
