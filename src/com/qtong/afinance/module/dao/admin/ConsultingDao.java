package com.qtong.afinance.module.dao.admin;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.qtong.afinance.core.dao.BaseJdbcDao;
import com.qtong.afinance.core.dao.PageData;
import com.qtong.afinance.core.dao.TableInfo;
import com.qtong.afinance.core.util.SqlHelper;
import com.qtong.afinance.module.pojo.consulting.Consulting;
import com.qtong.afinance.module.pojo.consulting.ConsultingRowMapper;

/**
 * 后台管理-项目咨询DAO层
 *
 */
@Repository("adminConsultingDao")
public class ConsultingDao extends BaseJdbcDao{
	final org.slf4j.Logger logger = LoggerFactory.getLogger(ConsultingDao.class);

	/**************************************后台管理-项目咨询********************************************************/
	
	/**
	 * 查询所有项目咨询列表
	 * @return 项目咨询列表
	 */
	public List<Consulting> queryAll(){
		StringBuilder sql = new StringBuilder("select * from ");
		
		sql.append(TableInfo.afin_consulting);
		
		List<Consulting> query = this.getJdbcTemplate().query(sql.toString(), new ConsultingRowMapper());
		
		return query;
	} 
	
	/**
	 * 根据id查看详情
	 * @param Consulting
	 */
	public Consulting queryById(int id) {
		Consulting o = null;
		StringBuffer sqlBuffer=new StringBuffer("select * from ");
		sqlBuffer.append(TableInfo.afin_consulting);
		sqlBuffer.append(" where id=?");
        Object[] params = new Object[]{id};
        o = this.getJdbcTemplate().queryForObject(sqlBuffer.toString(),new ConsultingRowMapper(),params);
		return o;
	}
	
	/**
	 * 根据id修改处理状态
	 * @return 修改的记录条数
	 */
	public int updateState(int id,String result,Timestamp updateTime) {
		StringBuffer sqlBuffer=new StringBuffer("Update ");
		sqlBuffer.append(TableInfo.afin_consulting);
		sqlBuffer.append(" set state=0,result=?,update_time=? where id=?");
		Object[] params = new Object[]{result,updateTime,id};
        return this.getJdbcTemplate().update(sqlBuffer.toString(),params);
	}
	

	/**
	 * 根据条件分页查询项目咨询信息列表
	 * @param pageData 分页数据
	 * @param Consulting 封装的实体
	 * @param keyWords 关键字查询
	 * @return
	 */
	public PageData queryByPage(PageData pageData,String state,String product,String job,String companyName,String name) {
	
		String tableName = TableInfo.afin_consulting;//表名
		
		SqlHelper sqlHelper = new SqlHelper(tableName)//
				.addCondition(!"3".equals(state) && !"".equals(state), "state=?", state)
				.addCondition(product!=null && !"".equals(product), "product=?", product)
				.addConditionLike(companyName!=null && !"".equals(companyName), "company_name like ?", companyName)
				.addConditionLike(job!=null && !"".equals(job), "job like ?", job)
				.addConditionLike(name!=null && !"".equals(name), "name like ?", name)
				.addOrder("create_time",false);
		
		
		PageData queryPage = this.queryPage(pageData, sqlHelper, new ConsultingRowMapper());
		
		return queryPage;//返回查询结果列表
	}
	
	/**
	 * 批量删除
	 * @param id集合
	 * @return 删除的条数
	 */
	public int deleteAll(final List id) {
		StringBuffer sb=new StringBuffer("delete from ");
		sb.append(TableInfo.afin_consulting);
		sb.append(" where id=?"); 	
		this.getJdbcTemplate().batchUpdate(sb.toString(), new BatchPreparedStatementSetter() {			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setInt(1, Integer.parseInt(id.get(i).toString()));		
			}					
			@Override
			public int getBatchSize() {
				return id.size();
			}
		});
		return id.size();
	}
	
}
