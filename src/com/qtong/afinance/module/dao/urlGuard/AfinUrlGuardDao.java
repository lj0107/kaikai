package com.qtong.afinance.module.dao.urlGuard;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.qtong.afinance.core.dao.BaseJdbcDao;
import com.qtong.afinance.core.dao.PageData;
import com.qtong.afinance.core.util.DateUtil;
import com.qtong.afinance.module.pojo.urlGuard.AfinUrlGuard;
import com.qtong.afinance.module.pojo.urlGuard.AfinUrlGuardCode;
import com.qtong.afinance.module.pojo.urlGuard.AfinUrlGuardCodeRowMapper;
import com.qtong.afinance.module.pojo.urlGuard.AfinUrlGuardProcess;
import com.qtong.afinance.module.pojo.urlGuard.AfinUrlGuardProcessRowMapper;
import com.qtong.afinance.module.pojo.urlGuard.AfinUrlGuardRowMapper;
import com.qtong.afinance.module.pojo.urlGuard.AfinUrlGuardSaftyStoreroom;
import com.qtong.afinance.module.pojo.urlGuard.AfinUrlGuardSaftyStoreroomRowMapper;
import com.qtong.afinance.module.pojo.urlGuard.AfinUrlGuardSpiteStoreroom;
import com.qtong.afinance.module.pojo.urlGuard.AfinUrlGuardSpiteStoreroomRowMapper;

/**
 *网址卫士dao
 * 
 *
 */
@Repository
public class AfinUrlGuardDao extends BaseJdbcDao {

	/**
	 * 网址卫士通用查询
	 * 
	 * @param pageIndex		  页数
	 * @param url            url
	 * @param snapshot       快照
	 * @param customer_no	   所属客户
	 * @param push_timeStart 推送时间
	 * @param push_timeEnd
	 * @param partner_no     推送者编码
	 * @param opt_timeStart  提交时间或者审核时间
	 * @param opt_timeEnd
	 * @param state
	 * @return
	 */
	public List<Map<String, Object>> getAfinUrlGuardList(String pageIndex, String url, String snapshot,
			String customer_no, String push_timeStart, String push_timeEnd, String partner_no, String opt_timeStart,
			String opt_timeEnd, String state) {
		StringBuilder strWhere = new StringBuilder();
		strWhere.append("select * from afin_urlguard,afin_urlguard_state_code ");
		strWhere.append(" where state=code ");
		List<Object> list = new ArrayList<>();
		if (!"".equals(state) && state != null&& !"undefined".equals(state)) {
			String[] split = state.split(",");
			strWhere.append(" and state in ( ");
			int i = 0;
			for (String string : split) {
				i++;
				if(i<split.length) {
					strWhere.append("?,");	
				}else {
					strWhere.append("? ");
				}
				list.add(string);
			}
			strWhere.append("  ) ");
		}
		if (!"".equals(url) && url != null&& !"undefined".equals(url)) {
			strWhere.append(" and url = ? ");
			list.add(url);
		}
		if (!"".equals(snapshot) && snapshot != null&& !"undefined".equals(snapshot)) {
			strWhere.append(" and snapshot = ? ");
			list.add(snapshot);
		}
		if (!"".equals(customer_no) && customer_no != null&& !"undefined".equals(customer_no)) {
			strWhere.append(" and customer_name like ? ");
			list.add("%" + customer_no + "%");
		}
		if (!"".equals(partner_no) && partner_no != null&& !"undefined".equals(partner_no)) {
			strWhere.append(" and partner_no like ? ");
			list.add("%" + partner_no + "%");
		}
		if (!"".equals(push_timeStart) && push_timeStart != null && !"undefined".equals(push_timeStart)) {
			strWhere.append(" and push_time >= ?");
			list.add(push_timeStart);
		}
		if (!"".equals(push_timeEnd) && push_timeEnd != null && !"undefined".equals(push_timeEnd)) {
			strWhere.append(" and push_time <= ?");
			list.add(push_timeEnd);
		}
		if (!"undefined".equals(opt_timeStart)&&!"".equals(opt_timeStart) && opt_timeStart != null) {
			strWhere.append(" and opt_time >= ?");
			list.add(opt_timeStart);
		}
		if (!"undefined".equals(opt_timeEnd)&&!"".equals(opt_timeEnd) && opt_timeEnd != null ) {
			strWhere.append(" and opt_time <= ?");
			list.add(opt_timeEnd);
		}
		if("102,103".equals(state)||"103,102".equals(state)) {
			strWhere.append(" order by opt_time desc ");
		}
		if (!"".equals(pageIndex) && pageIndex != null) {
			int page = 0;
			page = (Integer.valueOf(pageIndex) - 1) * 10;
			strWhere.append(" limit ?,10");
			list.add(page);
		}
		return this.getJdbcTemplate().queryForList(strWhere.toString(),list.toArray());
	}
	/**
	 * 下拉框：状态
	 * @return
	 */
	public List<Map<String, Object>> selectSateList() {
		StringBuilder sqlBuffer = new StringBuilder("select code,description,display from afin_urlguard_state_code");
		List<Map<String, Object>> queryForList = this.getJdbcTemplate().queryForList(sqlBuffer.toString());		
		return queryForList;
	}
	/**
	 * 恶意网址列表
	 * @param pageIndex 当前页
	 * @param snapshot  快照
	 * @param url  网址
	 * @param customerName 所属 客户	
	 * @param partnerName 推送者
	 *  推送时间范围
	 * @param firstTime  第一时间
	 * @param secondTime 第二时间
	 * select sc.display,sps.* from  afin_urlguard_spite_storeroom sps LEFT JOIN
afin_urlguard_state_code sc
on sps.state=sc.code
	 */
	public PageData selectSpiteList(String pageIndex, String snapshot, String url, String customerName, String partnerName,
			String firstTime, String secondTime) {
		PageData pageData = new PageData();//分页的工具类
		pageData.setPageIndex(Integer.parseInt(pageIndex));
		StringBuilder sql = new StringBuilder("select sc.display state,sps.* from  ");				
		sql.append(" afin_urlguard_spite_storeroom sps LEFT JOIN ")
		   .append(" afin_urlguard_state_code sc ")
		   .append(" on sps.state=sc.code")
		   .append(" where 1=1");		
		
		StringBuilder sqlCount = new StringBuilder(" select count(*) from ");
		sqlCount.append(" afin_urlguard_spite_storeroom sps LEFT JOIN ")
		   .append(" afin_urlguard_state_code sc ")
		   .append(" on sps.state=sc.code ")
		   .append(" where 1=1");
		List<Object> list = new ArrayList<>();
		if(!"".equals(snapshot)&&snapshot!=null) {
			sql.append(" and sps.snapshot  = ?");
			sqlCount.append(" and sps.snapshot = ?");
			list.add(snapshot);
		}
		if(!"".equals(url)&&url!=null) {
			sql.append(" and sps.url = ?");
			sqlCount.append(" and sps.url = ?");
			list.add(url);
		}
		if(!"".equals(customerName)&&customerName!=null) {
			sql.append(" and sps.customer_name=?");
			sqlCount.append(" and sps.customer_name=?");
			list.add(customerName);
		}
		if(!"".equals(partnerName)&&partnerName!=null) {
			sql.append(" and sps.partner_name=?");
			sqlCount.append(" and sps.partner_name=?");
			list.add(partnerName);
		}
		if(!"".equals(firstTime)&&firstTime!=null) {
			sql.append(" and sps.create_time>=?");
			sqlCount.append(" and sps.create_time>=?");
			list.add(firstTime);
		}
		if(!"".equals(secondTime)&&secondTime!=null) {
			sql.append(" and sps.create_time<=?");
			sqlCount.append(" and sps.create_time<=?");
			list.add(secondTime);
		}		
		pageData.setRowCount(this.getJdbcTemplate().queryForObject(sqlCount.toString(),Integer.class,list.toArray()).intValue());		
		sql.append(" order by sps.create_time desc ").append(" LIMIT ").append(((pageData.getPageIndex()-1)*pageData.getPageSize())).append(" , ").append(pageData.getPageSize());
		pageData.setLst(this.getJdbcTemplate().query(sql.toString(), list.toArray(), new AfinUrlGuardSpiteStoreroomRowMapper()));
		return pageData;	
		
	}
	/**
	 * 恶意网址列表-导出
	 * @param snapshot
	 * @param url
	 * @param customerName
	 * @param partnerName
	 * @param firstTime
	 * @param secondTime
	 */
	public List<AfinUrlGuardSpiteStoreroom> exportSpiteList(String snapshot, String url, String customerName, String partnerName, String firstTime,
			String secondTime) {
		StringBuilder sql = new StringBuilder("select sc.display state,sps.* from  ");				
		sql.append(" afin_urlguard_spite_storeroom sps LEFT JOIN ")
		   .append(" afin_urlguard_state_code sc ")
		   .append(" on sps.state=sc.code")
		   .append(" where 1=1");
		List<Object> list = new ArrayList<>();
		if(!"".equals(snapshot)&&snapshot!=null) {
			sql.append(" and sps.snapshot  = ?");
			list.add(snapshot);
		}
		if(!"".equals(url)&&url!=null) {
			sql.append(" and sps.url = ? ");
			list.add(url);
		}
		if(!"".equals(customerName)&&customerName!=null) {
			sql.append(" and sps.customer_name=?");
			list.add(customerName);
		}
		if(!"".equals(partnerName)&&partnerName!=null) {
			sql.append(" and sps.partner_name=?");
			list.add(partnerName);
		}
		if(!"".equals(firstTime)&&firstTime!=null) {
			sql.append(" and sps.create_time>=?");
			list.add(firstTime);
		}
		if(!"".equals(secondTime)&&secondTime!=null) {
			sql.append(" and sps.create_time<=?");
			list.add(secondTime);
		}		
		sql.append(" order by sps.create_time desc ");
		return this.getJdbcTemplate().query(sql.toString(), list.toArray(), new AfinUrlGuardSpiteStoreroomRowMapper());
	}
	/**
	 * 1.安全网址-列表
	 * @param pageIndex  当前页
	 * @param customerNo 客户名称
	 * 入库时间
	 * @param firstTime  第一时间
	 * @param secondTime  第二时间
	 * @return
	 * 
select ao.customer_name,ao.customer_number,COUNT(url) url,Max(create_time)  create_time from afin_order ao LEFT JOIN


afin_urlguard_safty_storeroom aus on ao.customer_number=aus.customer_no 

 GROUP BY ao.customer_name,ao.customer_number order by create_time desc
	 * 
     */
	public PageData selectSaftyList(String pageIndex, String customerName, String firstTime, String secondTime) {
		PageData pageData = new PageData();//分页的工具类
		pageData.setPageIndex(Integer.parseInt(pageIndex));
		StringBuilder sql = new StringBuilder("select ao.customer_name,ao.customer_number customer_no,COUNT(url) url,Max(create_time)  create_time from afin_order ao LEFT JOIN  ");				
		sql.append(" afin_urlguard_safty_storeroom aus on ao.customer_number=aus.customer_no  ")
		   .append(" where ao.product_name='网址卫士'");
		
		StringBuilder sqlCount = new StringBuilder(" select count(DISTINCT(ao.customer_name)) from afin_order ao LEFT JOIN  " );
		sqlCount.append("  afin_urlguard_safty_storeroom aus on ao.customer_number=aus.customer_no " );
		sqlCount.append(" where product_name='网址卫士'");
		List<Object> list = new ArrayList<>();
		if(!"".equals(customerName)&&customerName!=null) {
			sql.append(" and ao.customer_name=?");
			sqlCount.append(" and ao.customer_name=?");
			list.add(customerName);
		}
		if(!"".equals(firstTime)&&firstTime!=null) {
			sql.append(" and create_time>=?");
			sqlCount.append(" and create_time>=?");
			list.add(firstTime);
		}
		if(!"".equals(secondTime)&&secondTime!=null) {
			sql.append(" and create_time<=?");
			sqlCount.append(" and create_time<=?");
			list.add(secondTime);
		}		
		pageData.setRowCount(this.getJdbcTemplate().queryForObject(sqlCount.toString(),Integer.class,list.toArray()).intValue());		
		sql.append(" GROUP BY ao.customer_name,ao.customer_number order by create_time desc ").append(" LIMIT ").append(((pageData.getPageIndex()-1)*pageData.getPageSize())).append(" , ").append(pageData.getPageSize());
		pageData.setLst(this.getJdbcTemplate().query(sql.toString(), list.toArray(), new AfinUrlGuardSaftyStoreroomRowMapper()));			
		return pageData;	
	}
	/**
	 * 2.1安全网址-查看详情
	 * @param pageIndex 当前有页
	 * @param url  网址
	 * @param customerNumber  客户编码
	 * 入库时间
	 * @param firstTime  第一时间
	 * @param secondTime 第二时间
	 * @return
	 */
	public PageData selectByUrlList(String pageIndex, String url, String customerNumber, String firstTime,
			String secondTime) {
		PageData pageData = new PageData();//分页的工具类
		pageData.setPageIndex(Integer.parseInt(pageIndex));
		String tableName=" afin_urlguard_safty_storeroom ";//表名
		String fieldSelect="";
		String fieldOrderName="create_time";
		String fieldOrderDirection="desc";				
		StringBuilder strWhere=new StringBuilder();
		List<Object> list = new ArrayList<>();
			strWhere.append("  customer_no=?");	
			list.add(customerNumber);
		if (url!=null && url.trim().length()>0) {
			strWhere.append(" and url = ? ");	
			list.add(url);
		}
		if(!"".equals(firstTime)&&firstTime!=null) {
			strWhere.append(" and create_time>=?");
			list.add(firstTime);
		}
		if(!"".equals(secondTime)&&secondTime!=null) {
			strWhere.append(" and create_time<=?");
			list.add(secondTime);
		}		
		PageData queryPage = this.queryPage(pageData,tableName,"url",fieldSelect,fieldOrderName,fieldOrderDirection,strWhere.toString(), list.toArray(), new AfinUrlGuardSaftyStoreroomRowMapper());		       			
		return queryPage;
	}
	/**
	 * 2.2安全网址-查看详情-导出
	 * @param url
	 * @param customerNo
	 * @param firstTime
	 * @param secondTime
	 * @return
	 */
	public List<AfinUrlGuardSaftyStoreroom> exportByUrlList(String url, String customerNo, String firstTime,
			String secondTime) {
		List<Object> list = new ArrayList<>();
		StringBuilder strWhere=new StringBuilder();	
		strWhere.append(" select * from afin_urlguard_safty_storeroom where  ");
			strWhere.append("  customer_no=?");	
			list.add(customerNo);
		if (url!=null && url.trim().length()>0) {
			strWhere.append(" and url = ? ");	
			list.add(url);
		}
		if(!"".equals(firstTime)&&firstTime!=null) {
			strWhere.append(" and create_time>=?");
			list.add(firstTime);
		}
		if(!"".equals(secondTime)&&secondTime!=null) {
			strWhere.append(" and create_time<=?");
			list.add(secondTime);
		}		
		strWhere.append(" order by create_time desc ");
		
		return this.getJdbcTemplate().query(strWhere.toString(), list.toArray(), new AfinUrlGuardSaftyStoreroomRowMapper());
	}
	/**
	 * 3.安全网址-普通删除
	 * @param url 网址
	 * @return
	 */
	public int deleteByUrl(String url) {
		StringBuffer sqlBuffer=new StringBuffer("delete from afin_urlguard_safty_storeroom ");
		sqlBuffer.append(" where url=?");
		Object[] params = new Object[]{url};
        return this.getJdbcTemplate().update(sqlBuffer.toString(),params);
	}
	/**
	 * 4.安全网址-批量删除
	 * @param list
	 * @return
	 */
	public int deleteList(final List url) {
		StringBuffer sb=new StringBuffer("delete from afin_urlguard_safty_storeroom ");
		sb.append(" where url=?"); 	
		this.getJdbcTemplate().batchUpdate(sb.toString(), new BatchPreparedStatementSetter() {			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, url.get(i).toString());		
			}					
			@Override
			public int getBatchSize() {
				return url.size();
			}
		});
		return 1;
	}


	/**
	 * 根据url查询日志表
	 * 
	 * @param url
	 * @return
	 */
	public List<Map<String, Object>> getAfinUrlGuardProcess(String url) {
		StringBuffer str = new StringBuffer();
		str.append("select * from afin_urlguard_process,afin_urlguard_state_code where current_state=code and url = ?");
		Object[] o = new Object[] {url};
		return this.getJdbcTemplate().queryForList(str.toString(), o);
	}

	/**
	 * 查询总数                  
	 * @param url             url       
	 * @param snapshot        快照        
	 * @param customer_no     所属客户    
	 * @param push_timeStart  推送时间      
	 * @param push_timeEnd             
	 * @param partner_no      送者编码     
	 * @param opt_timeStart   交时间或者审核时间
	 * @param opt_timeEnd
	 * @param state
	 * @return
	 */
	public List<Map<String, Object>> getAfinUrlGuardCount(String url, String snapshot, String customer_no,
			String push_timeStart, String push_timeEnd, String partner_no, String opt_timeStart, String opt_timeEnd,
			String state) {
		StringBuilder strWhere = new StringBuilder();
		strWhere.append("select * from afin_urlguard,afin_urlguard_state_code ");
		strWhere.append(" where state=code ");
		List<Object> list = new ArrayList<>();
		if (!"".equals(state) && state != null&& !"undefined".equals(state)) {
			String[] split = state.split(",");
			strWhere.append(" and state in ( ");
			int i = 0;
			for (String string : split) {
				i++;
				if(i<split.length) {
					strWhere.append("?,");	
				}else {
					strWhere.append("? ");
				}
				list.add(string);
			}
			strWhere.append("  ) ");
		}
		if (!"".equals(url) && url != null&& !"undefined".equals(url)) {
			strWhere.append(" and url = ? ");
			list.add(url);
		}
		if (!"".equals(snapshot) && snapshot != null&& !"undefined".equals(snapshot)) {
			strWhere.append(" and snapshot = ? ");
			list.add(snapshot);
		}
		if (!"".equals(customer_no) && customer_no != null&& !"undefined".equals(customer_no)) {
			strWhere.append(" and customer_name like ? ");
			list.add("%" + customer_no + "%");
		}
		if (!"".equals(partner_no) && partner_no != null&& !"undefined".equals(partner_no)) {
			strWhere.append(" and partner_no like ? ");
			list.add("%" + partner_no + "%");
		}
		if (!"".equals(push_timeStart) && push_timeStart != null && !"undefined".equals(push_timeStart)) {
			strWhere.append(" and push_time >= ?");
			list.add(push_timeStart);
		}
		if (!"".equals(push_timeEnd) && push_timeEnd != null && !"undefined".equals(push_timeEnd)) {
			strWhere.append(" and push_time <= ?");
			list.add(push_timeEnd);
		}
		if (!"undefined".equals(opt_timeStart)&&!"".equals(opt_timeStart) && opt_timeStart != null) {
			strWhere.append(" and opt_time >= ?");
			list.add(opt_timeStart);
		}
		if (!"undefined".equals(opt_timeEnd)&&!"".equals(opt_timeEnd) && opt_timeEnd != null ) {
			strWhere.append(" and opt_time <= ?");
			list.add(opt_timeEnd);
		}
		return this.getJdbcTemplate().queryForList(strWhere.toString(),list.toArray());
	}

	

	/**
	 * 根据url查询当前状态
	 * @param url 网址
	 * @return
	 */
	public AfinUrlGuard queryByURL(String url) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("select * from afin_urlguard ")
				  .append(" where url=?");
		Object[] o = new Object[] {url};
		AfinUrlGuard queryForObject = this.getJdbcTemplate().queryForObject(sqlBuilder.toString(), new AfinUrlGuardRowMapper(), o);
		
		return queryForObject;
	}
	
	
	/**
	 * 修改状态
	 * @param url 网址
	 * @param state 状态码
	 * @param optId 操作人id
	 * @param optName 操作人名称
	 * @return
	 */
	public int updateState(final List<String> urls,final String state,final String optId,final String optName,Date optTime) {
		
		  final String sql = "update afin_urlguard set state=?,opt_id=?,opt_name=?,opt_time=now() where url =?";
	        int[] batchUpdate = this.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
	            
	            public void setValues(PreparedStatement ps, int i) throws SQLException {
	               
	                ps.setString(1, state);
	                ps.setString(2, optId);
	                ps.setString(3, optName);
	                ps.setString(4, urls.get(i));
	            }
	            
	            public int getBatchSize() {
	                return urls.size();
	            }
	        });
	      
		return batchUpdate.length;
		
	}
	
	/**
	 * 查询指定状态的记录个数
	 * @return
	 */
	public int getCountByState(String state){
		StringBuffer sqlBuffer=new StringBuffer("Select Count(*) from ");
		sqlBuffer.append("afin_urlguard");
		sqlBuffer.append(" where state =?");
		
		Object[] params=new Object[]{state};
		return this.getJdbcTemplate().queryForInt(sqlBuffer.toString(),params);
	}
	
	
	/**
	 * 添加日志表
	 * @param ids 生成的uuid集合
	 * @param urls url网址集合
	 * @param lastState 上一次状态
	 * @param currentState 当前状态
	 * @param remark 备注
	 * @param afinUrlGuard 操作人id操作人name操作时间
	 * @return
	 */
	public int insert(final List<String> ids,final List<String> urls,final String lastState,final String currentState,
			final String remark,final String optType,final AfinUrlGuard afinUrlGuard) {
		
		 final String sql = "insert into afin_urlguard_process (id,url,current_state,last_state,remark,opt_id,opt_name,opt_time,opt_type) value(?,?,?,?,?,?,?,?,?)";
	        int[] batchUpdate = this.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
	            
	            public void setValues(PreparedStatement ps, int i) throws SQLException {
	               
	                ps.setString(1, ids.get(i));
	                ps.setString(2, urls.get(i));
	                ps.setString(3, currentState);
	                ps.setString(4, lastState);
	                ps.setString(5, remark);
	                ps.setString(6, afinUrlGuard.getOptId());
	                ps.setString(7, afinUrlGuard.getOptName());
	                ps.setString(8, afinUrlGuard.getOptTime());
	                ps.setString(9, optType);
	            }
	            
	            public int getBatchSize() {
	                return urls.size();
	            }
	        });
//			int length = ids.size();
//			String[] sql = new String[length];
//			for(int i=0;i<urls.size();i++) {
//				sql[i]="insert into afin_urlguard_process (id,url,current_state,last_state,remark,opt_id,opt_name,opt_time,opt_type)"+
//						" values('"+ids.get(i)+"','"+urls.get(i)+"','"+currentState+"','"+lastState+"','"+remark+
//						"','"+afinUrlGuard.getOptId()+"','"+afinUrlGuard.getOptName()+"','"+afinUrlGuard.getOptTime()+"','"+optType+"');";
//			}
//			
//			int[] batchUpdate = this.getJdbcTemplate().batchUpdate(sql);
		
		return batchUpdate.length;
		
	}

	/**
	 * 5.安全网址-导入网址
	 */  
	public int insertUrl(List<AfinUrlGuardSaftyStoreroom> afinUrlGuardSaftyStoreroom){
		{
			final List<AfinUrlGuardSaftyStoreroom> tempBpplist = afinUrlGuardSaftyStoreroom;
			String sql ="insert into afin_urlguard_safty_storeroom(url,customer_no,customer_name) "
					+ "values(?,?,?)";
			this.getJdbcTemplate().batchUpdate(sql,new BatchPreparedStatementSetter() {   
				  
	            @Override  
	            public int getBatchSize() {   
	                 return tempBpplist.size();    
	            }   
	            @Override  
	            public void setValues(PreparedStatement ps, int i)   
	                    throws SQLException {   
	                  ps.setString(1, tempBpplist.get(i).getUrl().trim());
	                  ps.setString(2, tempBpplist.get(i).getCustomerNo());   
	                  ps.setString(3, tempBpplist.get(i).getCustomerName());	              
	            }    
	      });   
		}
		return 1;
	}
	
	
	/**
	 * 修改一条记录的状态
	 * @param url 网址
	 * @param state 状态码
	 * @param optId 操作人id
	 * @param optName 操作人名称
	 * @return
	 */
	public int updateOneState(String url,String state,String optId,String optName,Date optTime) {
		
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("update afin_urlguard ")
				  .append("set state=?,opt_id=?,opt_name=?,opt_time=? where url = ?");
		
		Object[] params = new Object[]{state,optId,optName,optTime,url};
		return this.getJdbcTemplate().update(sqlBuilder.toString(), params);
	}
	
	/**
	* 添加日志表
	 * @param ids 生成的uuid
	 * @param urls url网址
	 * @param lastState 上一次状态
	 * @param currentState 当前状态
	 * @param remark 备注
	 * @param afinUrlGuard 操作人id操作人name操作时间
	 * @return
	 */
	public int insertOne(String id,String url,String lastState,String currentState,
			String remark,String optType,AfinUrlGuard afinUrlGuard) {
		
		StringBuilder sqlBuilder = new StringBuilder();
		
		sqlBuilder.append("insert into afin_urlguard_process (id,url,current_state,"
				+ "last_state,remark,opt_id,opt_name,opt_time,opt_type)"+
				" values(?,?,?,?,?,?,?,?,?)");
		Object[] params = new Object[]{id,url,currentState,lastState,remark,afinUrlGuard.getOptId(),
				afinUrlGuard.getOptName(),afinUrlGuard.getOptTime(),optType};
		return this.getJdbcTemplate().update(sqlBuilder.toString(), params);
	}
	
	
	
	/**
	 * 根据url和当前状态查看上一次状态
	 * @param url url网址
	 * @param state 当前状态
	 * @return
	 */
	public AfinUrlGuardProcess queryByUrlAndState(String url,String state) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("select * from afin_urlguard_process ")
				  .append(" where url=? and current_state =?")
				  .append(" and opt_time = " + 
				  		"	(select MAX(opt_time)" + 
				  		"	from afin_urlguard_process" + 
				  		"	where url=? and current_state =?)" ) ;
		Object[] o = new Object[] {url,state,url,state};
		AfinUrlGuardProcess queryForObject = this.getJdbcTemplate().queryForObject(sqlBuilder.toString(), new AfinUrlGuardProcessRowMapper(),o);
		
		return queryForObject;
	}
	

	/**
	 * 获取每天网址卫士的指定状态的数据总数
	 * @return
	 */
	public int getCountByCondition(String customerNumber,String state){
		StringBuffer sqlBuffer=new StringBuffer("Select Count(1) from afin_urlguard urlguard,afin_urlguard_process process");
		sqlBuffer.append(" where urlguard.customer_no =? and process.current_state=? and urlguard.url=process.url and process.opt_time BETWEEN ? AND ?");
		
		Object[] params=new Object[]{customerNumber,state,new Timestamp(DateUtil.getBeforeDay(new Date()).getTime()),new Timestamp(new Date().getTime())};
		return this.getJdbcTemplate().queryForInt(sqlBuffer.toString(),params);
	}
	
	/**
	 * 根据安全网址库判断推送的是否是恶意网址
	 * @param url
	 * @return
	 */
	public int getAfinUrlGuardUrl(String url) {
		StringBuffer sql=new StringBuffer();
		sql.append("select count(1) from afin_urlguard_safty_storeroom where url=?");
		Object[] o = new Object[] {url};
 		try {
			int queryForInt = this.getJdbcTemplate().queryForInt(sql.toString(), o);
			return queryForInt;
		} catch (DataAccessException e) {
			return 0;
		}
	}
	
	/**
	 * 添加网址库
	 * @param a
	 * @return
	 * url,counterfeit_domain,snapshot,customer_no,customer_name,check_time,
	 * intercept_count,invalid_time, site_state,server_ip,server_location, 
	 * register_person,register_mail, register_phone,partner_no,partner_name,
	 * push_time,state,opt_time,opt_id,opt_name
	 */
	public int addAfinUrlGuardUrl(AfinUrlGuard a) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("INSERT INTO `afin_urlguard`(url,counterfeit_domain,snapshot,customer_no,"
				+ "customer_name,check_time,intercept_count,invalid_time, site_state,server_ip,"
				+ "server_location,register_person,register_mail, register_phone,partner_no,"
				+ "partner_name,push_time,state,opt_time,opt_id,opt_name) "
				+ "VALUES (?, ?, ?, ?, ?, now(), ?, now(), ?, ?, ?, ?, ?, ?, ?, ?, now(), ?,now(), ?, ?)");
		Object[] params = new Object[]{a.getUrl(), a.getCounterfeitDomain(), a.getSnapshot(), 
				a.getCustomerNo(), a.getCustomerName(),a.getInterceptCount(), a.getSiteState(), 
				a.getServerIp(), a.getServerLocation(), a.getRegisterPerson(), a.getRegisterMail(), 
				a.getRegisterPhone(), a.getPartnerNo(), a.getPartnerName(), a.getState(), a.getOptId(),
				a.getOptName()};
		try {
			return this.getJdbcTemplate().update(sqlBuilder.toString(),params);
		} catch (DataAccessException e) {
			return 0;
		}
	}
	/**
	 * 判断白名单添加日志状态
	 * @param id
	 * @param url
	 * @param state
	 * @param opt_type
	 * @return
	 * id,url,current_state,last_stat,remark,opt_id,opt_name,opt_time,opt_type
	 */
	public int addAfinUrlGuardProcessUrl(String id, String url, String state, String opt_type) {
		StringBuilder sqlBuilder = new StringBuilder();
		String opt_name="平台";
		sqlBuilder.append("insert into afin_urlguard_process (id,url,current_state,opt_name,"
				+ "opt_time,opt_type,remark) values(?,?,?,?,now(),?,'')");
		Object[] params = new Object[]{id, url, state, opt_name,opt_type};
		try {
			return this.getJdbcTemplate().update(sqlBuilder.toString(), params);
		} catch (DataAccessException e) {
			return 0;
		}
	}
	/**
	 * 网址卫士导出通用
	 * @param url
	 * @param snapshot
	 * @param customer_no
	 * @param push_timeStart
	 * @param push_timeEnd
	 * @param partner_no
	 * @param opt_timeStart
	 * @param opt_timeEnd
	 * @param state
	 * @return
	 */
	public List<AfinUrlGuardCode> ExportAfinUrlGuardList(String url, String snapshot, String customer_no,
			String push_timeStart, String push_timeEnd, String partner_no, String opt_timeStart, String opt_timeEnd,
			String state) {
		StringBuilder strWhere = new StringBuilder();
		List<Object> list = new ArrayList<>();
		strWhere.append("select * from afin_urlguard,afin_urlguard_state_code where state=code ");
		if (!"".equals(state) && state != null&& !"undefined".equals(state)) {
			String[] split = state.split(",");
			strWhere.append(" and state in ( ");
			int i = 0;
			for (String string : split) {
				i++;
				if(i<split.length) {
					strWhere.append("?,");	
				}else {
					strWhere.append("? ");
				}
				list.add(string);
			}
			strWhere.append("  ) ");
		}
		if (!"".equals(url) && url != null&& !"undefined".equals(url)) {
			strWhere.append(" and url = ? ");
			list.add(url);
		}
		if (!"".equals(snapshot) && snapshot != null&& !"undefined".equals(snapshot)) {
			strWhere.append(" and snapshot = ? ");
			list.add(snapshot);
		}
		if (!"".equals(customer_no) && customer_no != null&& !"undefined".equals(customer_no)) {
			strWhere.append(" and customer_name like ? ");
			list.add("%" + customer_no + "%");
		}
		if (!"".equals(partner_no) && partner_no != null&& !"undefined".equals(partner_no)) {
			strWhere.append(" and partner_no like ? ");
			list.add("%" + partner_no + "%");
		}
		if (!"".equals(push_timeStart) && push_timeStart != null && !"undefined".equals(push_timeStart)) {
			strWhere.append(" and push_time>=?");
			list.add(push_timeStart);
		}
		if (!"".equals(push_timeEnd) && push_timeEnd != null && !"undefined".equals(push_timeEnd)) {
			strWhere.append(" and push_time<=?");
			list.add(push_timeEnd);
		}
		if (!"undefined".equals(opt_timeStart)&&!"".equals(opt_timeStart) && opt_timeStart != null) {
			strWhere.append(" and opt_time>=?");
			list.add(opt_timeStart);
		}
		if (!"undefined".equals(opt_timeEnd)&&!"".equals(opt_timeEnd) && opt_timeEnd != null ) {
			strWhere.append(" and opt_time<=?");
			list.add(opt_timeEnd);
		}
		return this.getJdbcTemplate().query(strWhere.toString(),new AfinUrlGuardCodeRowMapper(),list.toArray());
	}
	
	/**
	 * 网址卫士导出通用查url
	 * @param url
	 * @param snapshot
	 * @param customer_no
	 * @param push_timeStart
	 * @param push_timeEnd
	 * @param partner_no
	 * @param opt_timeStart
	 * @param opt_timeEnd
	 * @param state
	 * @return
	 */
	public Map<String,Object> exportAfinUrlGuardUrl(String url, String snapshot, String customer_no,
			String push_timeStart, String push_timeEnd, String partner_no, String opt_timeStart, String opt_timeEnd,
			String state) {
		StringBuilder strWhere = new StringBuilder();
		List<Object> list = new ArrayList<>();
		strWhere.append("select GROUP_CONCAT(url) urls from afin_urlguard,afin_urlguard_state_code where state=code ");
		if (!"".equals(state) && state != null&& !"undefined".equals(state)) {
			String[] split = state.split(",");
			strWhere.append(" and state in ( ");
			int i = 0;
			for (String string : split) {
				i++;
				if(i<split.length) {
					strWhere.append("?,");	
				}else {
					strWhere.append("? ");
				}
				list.add(string);
			}
			strWhere.append("  ) ");
		}
		if (!"".equals(url) && url != null&& !"undefined".equals(url)) {
			strWhere.append(" and url = ?");
			list.add(url);
		}
		if (!"".equals(snapshot) && snapshot != null&& !"undefined".equals(snapshot)) {
			strWhere.append(" and snapshot = ? ");
			list.add(snapshot);
		}
		if (!"".equals(customer_no) && customer_no != null&& !"undefined".equals(customer_no)) {
			strWhere.append(" and customer_name like ? ");
			list.add("%" + customer_no + "%");
		}
		if (!"".equals(partner_no) && partner_no != null&& !"undefined".equals(partner_no)) {
			strWhere.append(" and partner_no like ? ");
			list.add("%" + partner_no + "%");
		}
		if (!"".equals(push_timeStart) && push_timeStart != null && !"undefined".equals(push_timeStart)) {
			strWhere.append(" and push_time>=?");
			list.add(push_timeStart);
		}
		if (!"".equals(push_timeEnd) && push_timeEnd != null && !"undefined".equals(push_timeEnd)) {
			strWhere.append(" and push_time<=?");
			list.add(push_timeEnd);
		}
		if (!"undefined".equals(opt_timeStart)&&!"".equals(opt_timeStart) && opt_timeStart != null) {
			strWhere.append(" and opt_time>=?");
			list.add(opt_timeStart);
		}
		if (!"undefined".equals(opt_timeEnd)&&!"".equals(opt_timeEnd) && opt_timeEnd != null ) {
			strWhere.append(" and opt_time<=?");
			list.add(opt_timeEnd);
		}
		return this.getJdbcTemplate().queryForMap(strWhere.toString(),list.toArray());
	}
	
	/**
	 * 查询所有待拦截状态的网址
	 * @param state
	 * @return
	 */
	public List<AfinUrlGuard> queryByState(String state){
		
		String sql = "select * from afin_urlguard where state=?";
		
		Object[] params = new Object[] {state};
		
		return this.getJdbcTemplate().query(sql, params, new AfinUrlGuardRowMapper());
	}
	/**
	 * 查询所有安全网址库的网址
	 * @param state
	 * @return
	 */
	public List<AfinUrlGuardSaftyStoreroom> queryBySafteState(){
		String sql = "select * from afin_urlguard_safty_storeroom ";
		return this.getJdbcTemplate().query(sql, new AfinUrlGuardSaftyStoreroomRowMapper());
	}

}



