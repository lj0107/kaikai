package com.qtong.afinance.module.dao.daping;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.qtong.afinance.core.dao.BaseJdbcDao;
import com.qtong.afinance.core.util.DateUtil;
import com.qtong.afinance.module.pojo.bill.Bill;
import com.qtong.afinance.module.pojo.bill.BillRowMapper;


@Component
public class DaPingDao extends BaseJdbcDao{

	/**
	 * 浦发
	 * 查询次数
	 */
	public List<Bill> getOrders(){
		StringBuilder sqlBuffer = new StringBuilder("select * from ");
		sqlBuffer.append(" afin_bill where customer_number=? and bill_time<? ORDER BY bill_time ");
		Object[] params=new Object[]{"E0002017103110010884",DateUtil.getInitMonth(new Date())};
		List<Bill> bill=this.getJdbcTemplate().query(sqlBuffer.toString(), new BillRowMapper(),params);
		return bill;
	}
	/**
	 * 查询浦发乾坤大数据单价
	 * @return
	 */
	public List<Map<String, Object>> getSum(){
		StringBuilder sqlBuffer = new StringBuilder("select fee from ");
		sqlBuffer.append(" afin_order_relation where customer_number=?");
		Object[] params=new Object[]{"E0002017103110010884"};
		List<Map<String, Object>> feeForList = this.getJdbcTemplate().queryForList(sqlBuffer.toString(),params);
		return feeForList;
	}
	/**
	 * 查询太保当月总次数
	 */
	public Integer getCurrentMonthCount(String customerNumber,String productOrderId,Date recordTime) {
		
		StringBuilder sqlBuffer = new StringBuilder("select sum(count) count from ");
		sqlBuffer.append(" afin_order_stats where customer_number=? and product_order_id=? and record_time>=?");
		Object[] params=new Object[]{customerNumber,productOrderId,recordTime};
		Map<String, Object> map = this.getJdbcTemplate().queryForMap(sqlBuffer.toString(),params);
		if(null != map.get("count"))
			return Integer.parseInt(map.get("count").toString());
		return 0;
	}
}
