package com.qtong.afinance.module.service.record;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qtong.afinance.module.dao.record.RecordDao;
import com.qtong.afinance.module.pojo.statistics.RequestCounts;

@Transactional
@Service
public class RecordService {
	@Autowired
	private RecordDao recordDao;
	
	
	
	/**
	 * 获取每天的对应客户和产品的使用费用
	 * @param customerNumber
	 * @param proCode
	 * @return 
	 * @return
	 */
	public  Map<String, Object> getStatsCondition(String orderId){
		return recordDao.getStatsCondition(orderId);
	}
	
	
	/**
	 * 获取每天的对应客户和产品的请求次数
	 * @param customerNumber
	 * @param proCode
	 * @return
	 */
	public int getCountByCondition(String orderId) {
		return recordDao.getCountByCondition(orderId);
	}
	
	/**
	 * 获取每天的对应客户和产品的使用费用
	 * @param customerNumber
	 * @param proCode
	 * @return
	 */
	public Double getFeeByCondition(String orderId) {
		return recordDao.getFeeByCondition(orderId);
	}
	
	/**
	 * 统计每天乾坤大数据的请求次数
	 * @return
	 */
	public List<Map<String,Object>> getDatamarkCount(){
		return recordDao.queryCounts();
	}
	
	/**
	 * 批量插入请求次数表
	 * @param requestCounts
	 * @return
	 */
	public int saveRequestCounts(List<RequestCounts> requestCounts) {
		return recordDao.insertRequestCounts(requestCounts);
	}
}
