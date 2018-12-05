package com.qtong.afinance.module.dao.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.qtong.afinance.core.dao.BaseJdbcDao;
import com.qtong.afinance.core.dao.TableInfo;
import com.qtong.afinance.module.pojo.admin.AdminResources;
import com.qtong.afinance.module.pojo.admin.AdminResourcesRowMapper;
@Repository
public class AdminResourcesDao extends BaseJdbcDao{
	
	final Logger logger = LoggerFactory.getLogger(AdminUserDao.class);
	

	
	/**
	 * 查user拥有权限
	 * @return
	 */
	public List<AdminResources> selUserRes(String userId){
		StringBuffer sqlBuffer=new StringBuffer("select DISTINCT res.* from ");
		sqlBuffer.append(TableInfo.afin_admin_resources);
		sqlBuffer.append(" res inner join ");
		sqlBuffer.append(TableInfo.afin_admin_role_res);
		sqlBuffer.append(" rres on res.id=rres.res_id inner join ");
		sqlBuffer.append(TableInfo.afin_admin_user_role);
		sqlBuffer.append(" ur on rres.role_id=ur.role_id where ur.user_id=? and res.state=? order by ord");
		Object[] params = new Object[]{userId,0};	
		logger.info(sqlBuffer.toString());
		return this.getJdbcTemplate().query(sqlBuffer.toString(),params,new AdminResourcesRowMapper());		
	}
	
	/**
	 * 查给定userId、mark的权限
	 * @return
	 */
/*	public List<AdminResources> selUserRes(String userId,Integer mark){
		StringBuffer sqlBuffer=new StringBuffer("select DISTINCT res.* from ");
		sqlBuffer.append(TableInfo.afin_admin_resources);
		sqlBuffer.append(" res inner join ");
		sqlBuffer.append(TableInfo.afin_admin_role_res);
		sqlBuffer.append(" rres on res.id=rres.res_id inner join ");
		sqlBuffer.append(TableInfo.afin_admin_user_role);
		sqlBuffer.append(" ur on rres.role_id=ur.role_id where ur.user_id=? and res.mark =? and res.state=?");
		Object[] params = new Object[]{userId,mark,0};	
		logger.info(sqlBuffer.toString());
		return this.getJdbcTemplate().query(sqlBuffer.toString(),params,new AdminResourcesRowMapper());		
	}*/
	
	/**
	 * 查role拥有权限
	 * @param roleId
	 * @return
	 */
	public List<AdminResources> selRoleRes(String roleId){
		StringBuffer sqlBuffer=new StringBuffer("select res.* from ");
		sqlBuffer.append(TableInfo.afin_admin_resources);
		sqlBuffer.append(" res left join ");
		sqlBuffer.append(TableInfo.afin_admin_role_res);
		sqlBuffer.append(" rres on res.id=rres.res_id where rres.role_id=? and res.state=?");		
		Object[] params = new Object[]{roleId,0};	
		logger.info(sqlBuffer.toString());
		return this.getJdbcTemplate().query(sqlBuffer.toString(),params,new AdminResourcesRowMapper());		
	}
	
	/**
	 * 查所有res
	 * @return
	 */
	public List<AdminResources> selAll(){
		StringBuffer sqlBuffer=new StringBuffer("select * from ");
		sqlBuffer.append(TableInfo.afin_admin_resources);		
		Object[] params = new Object[]{};	
		logger.info(sqlBuffer.toString());
		return this.getJdbcTemplate().query(sqlBuffer.toString(),params,new AdminResourcesRowMapper());		
	}  
	
	
	/**
	 * 新增权限
	 * @param resources
	 * @return 主键Id
	 */
	public Integer insertReturnId(final AdminResources resources){
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		this.getJdbcTemplate().update(new PreparedStatementCreator() {
			
			
			@Override
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				StringBuffer sqlBuffer=new StringBuffer("insert into ");
				sqlBuffer.append(TableInfo.afin_admin_resources);
				sqlBuffer.append("(name,lev,type,parent_id,ord,url,creator,description,mark,create_time,update_time,state) values(?,?,?,?,?,?,?,?,?,?,?,?)"); 
				PreparedStatement ps=conn.prepareStatement(sqlBuffer.toString(),Statement.RETURN_GENERATED_KEYS);
				
				ps.setString(1, resources.getName());
				ps.setInt(2, resources.getLev());
				ps.setString(3, resources.getType());
				ps.setString(4, resources.getParentId());
				ps.setInt(5, resources.getOrd());
				ps.setString(6, resources.getUrl());
				ps.setString(7, resources.getCreator());
				ps.setString(8, resources.getDescription());
				if(resources.getMark()==null){
					ps.setNull(9, Types.NULL);
				}else {
					ps.setInt(9, resources.getMark());
				}
				ps.setTimestamp(10, resources.getCreateTime());
				ps.setTimestamp(11, resources.getUpdateTime());
				ps.setInt(12, resources.getState());
                return ps;
			}
		},keyHolder);
		
		return (Integer) keyHolder.getKey().intValue();
		
	}
	
}
