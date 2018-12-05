package com.qtong.afinance.module.dao.portal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.qtong.afinance.core.dao.BaseJdbcDao;
import com.qtong.afinance.core.dao.PageData;
import com.qtong.afinance.core.dao.TableInfo;
import com.qtong.afinance.module.pojo.product.AdminProRatio;
import com.qtong.afinance.module.pojo.product.AdminProRatioInfo;
import com.qtong.afinance.module.pojo.product.AdminProRatioInfoRowMapper;
import com.qtong.afinance.module.pojo.product.AdminProRatioRowMapper;

/**
 * 审批
 *
 */
@Repository
public class AdminRatioDao extends BaseJdbcDao{

	/**
	 * 查询未审批的
	 * @param pageIndex页码
	 * @param apply_user提交人
	 * @param apply_timeStart提交时间
	 * @param apply_timeEnd提交时间
	 * @return
	 */
	public PageData getRatioList(String pageIndex, String apply_user, String apply_timeStart, String apply_timeEnd) {
		PageData pageData = new PageData();//分页的工具类
		pageData.setPageIndex(Integer.parseInt(pageIndex));
		String tableName=TableInfo.afin_admin_pro_ratio;//表名
		String fieldSelect="";
		String fieldOrderName="apply_time";
		String fieldOrderDirection="desc";			
		StringBuilder strWhere=new StringBuilder();
		strWhere.append(" state='1' ");	 
		List<Object> list = new ArrayList<Object>();
		if (!"".equals(apply_user)&&apply_user!=null) {
			strWhere.append(" and apply_user like ? ");
			list.add("%"+apply_user+"%");
		}
		if(!"".equals(apply_timeStart)&&apply_timeStart!=null&&!"undefined".equals(apply_timeStart)) {
			strWhere.append(" and apply_time >= ?");
			list.add(apply_timeStart);
		}
		if(!"".equals(apply_timeEnd)&&apply_timeEnd!=null&&!"undefined".equals(apply_timeStart)) {
			strWhere.append(" and apply_time <= ?");
			list.add(apply_timeEnd);
		}	
		PageData queryPage = this.queryPage(pageData,tableName,"id",fieldSelect,fieldOrderName,fieldOrderDirection,strWhere.toString(), list.toArray(), new AdminProRatioRowMapper());		       			
		return queryPage;
	}
	/**
	 * 查询审批的
	 * @param pageIndex页码
	 * @param apply_user提交人
	 * @param apply_timeStart提交时间
	 * @param apply_timeEnd提交时间
	 * @return
	 */
	public PageData getRatioList1(String pageIndex, String apply_user, String apply_timeStart, String apply_timeEnd,String confirm_user, String confirm_timeStart, String confirm_timeEnd,String state) {
		PageData pageData = new PageData();//分页的工具类
		pageData.setPageIndex(Integer.parseInt(pageIndex));
		String tableName=TableInfo.afin_admin_pro_ratio;//表名
		String fieldSelect="";
		String fieldOrderName="";
		String fieldOrderDirection="desc";			
		StringBuilder strWhere=new StringBuilder();
		List<Object> list = new ArrayList<Object>();
		if(!"".equals(state)&&state!=null) {
			strWhere.append(" state = ? ");
			list.add(state);
			fieldOrderName="apply_time";
		}else {
			strWhere.append(" state in('3','4')");
			fieldOrderName="confirm_time";
		}
		if (!"".equals(apply_user)&&apply_user!=null) {
			strWhere.append(" and apply_user like ? ");
			list.add("%"+apply_user+"%");
		}
		if(!"".equals(apply_timeStart)&&apply_timeStart!=null&&!"undefined".equals(apply_timeStart)) {
			strWhere.append(" and apply_time >= ?");
			list.add(apply_timeStart);
		}
		if(!"".equals(apply_timeEnd)&&apply_timeEnd!=null&&!"undefined".equals(apply_timeStart)) {
			strWhere.append(" and apply_time <= ? ");
			list.add(apply_timeEnd);
		}		
		if (!"".equals(confirm_user)&&confirm_user!=null) {
			strWhere.append(" and confirm_user like ? ");
			list.add("%"+confirm_user+"%");
		}
		if(!"".equals(confirm_timeStart)&&confirm_timeStart!=null&&!"undefined".equals(confirm_timeStart)) {
			strWhere.append(" and confirm_time >= ? ");
			list.add(confirm_timeStart);
		}
		if(!"".equals(confirm_timeEnd)&&confirm_timeEnd!=null&&!"undefined".equals(confirm_timeEnd)) {
			strWhere.append(" and confirm_time <= ? ");
			list.add(confirm_timeEnd);
		}		
		PageData queryPage = this.queryPage(pageData,tableName,"id",fieldSelect,fieldOrderName,fieldOrderDirection,strWhere.toString(), list.toArray(), new AdminProRatioRowMapper());		       			
		return queryPage;
	}

	/**
	 * 查询未审批详细
	 * @param id
	 * @return
	 */
	//SELECT * from  afin_pro_confirm ,afin_order_sub where afin_pro_confirm.sub_order_num =afin_order_sub.sub_order_num and id=1
	public List<Map<String, Object>> getRatioInfo(String id) {
		StringBuilder s =new StringBuilder();
		s.append("SELECT afin_ratio_confirm.*,afin_order.product_number,afin_order.product_name,"
				+ "afin_order.customer_name,afin_order_sub.product_order_number,a"
				+ "fin_order_sub.pro_name,afin_order_sub.pro_code,afin_order_sub.product_order_id from  afin_ratio_confirm ,"
				+ "afin_order_sub,afin_order where afin_order_sub.product_order_id=afin_order.product_order_id "
				+ "and afin_ratio_confirm.sub_order_num =afin_order_sub.sub_order_num and id = ? ");
		Object[] value = new Object[]{id};
		return this.getJdbcTemplate().queryForList(s.toString(), value);
	}

	/**
	 * 查询伙伴
	 * @param code
	 * @return
	 */
	public Map<String, Object> getPartners(String code) {
		StringBuilder s =new StringBuilder();
		s.append("select * from  afin_product,afin_partner "
				+ "where afin_product.partner_id=afin_partner.id "
				+ "and product_code = ? " );
		Object[] value = new Object[]{code};
		try {
			return this.getJdbcTemplate().queryForMap(s.toString(), value);
		} catch (DataAccessException e) {
			return null;
		}
	}

	/**
	 * 审批同意
	 * @param sub_order_num
	 * @param confirm_user 
	 * @return
	 */
	public int updateRatioArgee(String sub_order_num, String confirm_user) {
		StringBuilder s =new StringBuilder();
		s.append("update ");
		s.append(TableInfo.afin_admin_pro_ratio);
		s.append(" set state=3,confirm_user=?,suggest ='',confirm_time=now() where sub_order_num = ?");
		Object[] value = new Object[]{confirm_user, sub_order_num};
		return this.getJdbcTemplate().update(s.toString(), value);
	}
	
	
	/**
	 * 查询指定状态的记录个数
	 * @return
	 */
	public int getCountByState(String state){
		StringBuffer sqlBuffer=new StringBuffer("Select Count(*) from ");
		sqlBuffer.append(TableInfo.afin_admin_pro_ratio);
		sqlBuffer.append(" where state =?");
		Object[] params=new Object[]{state};
		return this.getJdbcTemplate().queryForInt(sqlBuffer.toString(),params);
	}
	

	/**
	 * 修改订单子表
	 * @param ratio_after 
	 * @param sub_order_num 
	 * @return
	 */
	public int updateOrder(String sub_order_num, String ratio_after) {
		StringBuilder s =new StringBuilder();
		s.append("update ");
		s.append(TableInfo.afin_order_sub);
		s.append(" set ratio = ? where sub_order_num = ?");
		Object[] value = new Object[]{ratio_after, sub_order_num};
		return this.getJdbcTemplate().update(s.toString(), value);
	}

	/**
	 * 审批驳回
	 * @param sub_order_num
	 * @param suggest
	 * @param confirm_user
	 * @return
	 */
	public int updateRatioReject(String sub_order_num, String suggest, String confirm_user) {
		StringBuilder s =new StringBuilder();
		s.append("update ");
		s.append(TableInfo.afin_admin_pro_ratio);
		s.append(" set state = 4,suggest = ?,confirm_user = ?,confirm_time=now() where sub_order_num = ? ");
		Object[] value = new Object[]{suggest, confirm_user, sub_order_num};
		return this.getJdbcTemplate().update(s.toString(), value);
	}
	/**
	 * //获取分成比例表同意的数据
	 * @param sub_order_num
	 * @return
	 */
	public AdminProRatioInfo getRatio(String sub_order_num) {
		StringBuffer sqlBuffer=new StringBuffer("Select * from afin_ratio_confirm ");
		//sqlBuffer.append(TableInfo.afin_admin_pro_ratio);
		sqlBuffer.append(" where sub_order_num  = ?");
		Object[] params=new Object[]{sub_order_num};
		return this.getJdbcTemplate().queryForObject(sqlBuffer.toString(),new AdminProRatioInfoRowMapper(),params);
	}
	/**
	 * 添加一条新的分成比例
	 * @param ratio
	 * @return
	 */
	public int insertRatioInfo(AdminProRatio ratio) {
		StringBuilder s =new StringBuilder();
		s.append("INSERT INTO `afin_ratio_info` (`sub_order_num`, `opt_matter`, `oper_state`, `ratio_before`, "
				+ "`ratio_after`, `apply_user`, `confirm_user`, `apply_time`, `confirm_time`, `update_time`, `suggest`, `state`)"
				+ " VALUES (?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?)");
		Object[] value = new Object[]{ratio.getSubOrderNum(),ratio.getOptMatter(), ratio.getOperState(),ratio.getRatioBefore(),ratio.getRatioAfter(),
				ratio.getApplyUser(),ratio.getConfirmUser(),ratio.getApplyTime(),ratio.getConfirmTime(),ratio.getApplyTime(),
				ratio.getSuggest(),ratio.getState()};
		return this.getJdbcTemplate().update(s.toString(), value);
	}
	/**
	 * 更改日志表中的分成比例状态为已失效
	 * @param subOrderNum
	 */
	public void updateRatioInfoState(String subOrderNum) {
		StringBuilder s =new StringBuilder();
		s.append("update afin_ratio_info ");
		s.append(" set state=2,update_time=now() where sub_order_num = ? and state=1");
		Object[] value = new Object[]{subOrderNum};
		this.getJdbcTemplate().update(s.toString(), value);
	}
	/**
	 * 根据流水号判断是否有数据
	 * @param subOrderNum
	 */
	public List<AdminProRatioInfo> selectAdminProRatio(String subOrderNum) {
		StringBuffer stringBuffer = new StringBuffer("select * from ");
			stringBuffer.append(" afin_ratio_info where sub_order_num= ?");
			Object[] params=new Object[]{subOrderNum};
			return this.getJdbcTemplate().query(stringBuffer.toString(),new AdminProRatioInfoRowMapper(),params);
	}
	/**
	 * 添加日志表
	 * @param ratio
	 * @return
	 */
	public int updateRatioInfo(AdminProRatioInfo ratio) {
		StringBuilder s =new StringBuilder();
		s.append("update afin_ratio_info ");
		s.append(" set state=?,confirm_time=now(),suggest = ?,confirm_user=? where sub_order_num = ? and state=1");
		Object[] value = new Object[]{ratio.getState(),ratio.getSuggest(),ratio.getConfirmUser(),ratio.getSubOrderNum()};
		return this.getJdbcTemplate().update(s.toString(), value);
	}
	
}
