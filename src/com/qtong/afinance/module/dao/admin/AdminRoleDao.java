package com.qtong.afinance.module.dao.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.qtong.afinance.core.dao.BaseJdbcDao;
import com.qtong.afinance.core.dao.PageData;
import com.qtong.afinance.core.dao.TableInfo;
import com.qtong.afinance.core.util.SqlHelper;
import com.qtong.afinance.module.pojo.admin.AdminRole;
import com.qtong.afinance.module.pojo.admin.AdminRoleRowMapper;
@Repository
public class AdminRoleDao extends BaseJdbcDao{
	
	final Logger logger = LoggerFactory.getLogger(AdminUserDao.class);
	
	/**
	 * 角色分页
	 * @param pageIndex 当前页
	 * @return
	 */
	public PageData pageQuery(String pageIndex) {
		PageData pageData = new PageData();
		pageData.setPageIndex(Integer.parseInt(pageIndex));
		

		SqlHelper sqlHelper = new SqlHelper(TableInfo.afin_admin_role)//
			.addOrder("create_time", false);
		
		PageData queryPage = this.queryPage(pageData, sqlHelper, new AdminRoleRowMapper());
		
		return queryPage;	
	}
	
	/**
	 * 新增角色
	 * @param r
	 * @return
	 */
	public int insertRole(AdminRole r){
		StringBuffer sqlBuffer=new StringBuffer("insert into ");
		sqlBuffer.append(TableInfo.afin_admin_role);
		sqlBuffer.append("(name,description,creator,create_time,state) values(?,?,?,?,?)"); 	
		Object[] params = new Object[]{r.getName(),r.getDescription(),r.getCreator(),r.getCreateTime(),r.getState()};
		logger.info(sqlBuffer.toString());
		return this.getJdbcTemplate().update(sqlBuffer.toString(), params);
	}
	
	
	/**
	 * 新增角色并返回主键
	 * @param r
	 * @return 主键Id
	 */
	public Integer insertRoleReturnId(final AdminRole r){
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		this.getJdbcTemplate().update(new PreparedStatementCreator() {
			
			
			@Override
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				StringBuffer sqlBuffer=new StringBuffer("insert into ");
				sqlBuffer.append(TableInfo.afin_admin_role);
				sqlBuffer.append("(name,description,creator,create_time,state) values(?,?,?,?,?)");  
				PreparedStatement ps=conn.prepareStatement(sqlBuffer.toString(),Statement.RETURN_GENERATED_KEYS);
				
				ps.setString(1, r.getName());
				ps.setString(2, r.getDescription());
				ps.setString(3, r.getCreator());
				ps.setTimestamp(4, r.getCreateTime());
				ps.setInt(5, r.getState());
                return ps;
			}
		},keyHolder);
		
		return (Integer) keyHolder.getKey().intValue();
		
	}
	
	
	
	/**
	 * 根据角色名称查询角色
	 * @param name
	 * @return
	 */
	public List<AdminRole> getRolesByName(String name){
		StringBuilder sqlBuffer = new StringBuilder("select * from ");
		sqlBuffer.append(TableInfo.afin_admin_role);
		sqlBuffer.append(" where name=?"); 
		Object[] params = new Object[]{name};
		logger.info(sqlBuffer.toString());
		
		List<AdminRole> query=this.getJdbcTemplate().query(sqlBuffer.toString(), new AdminRoleRowMapper(),params);
		
		return query;
	}
	
	/**
	 * 修改角色
	 * @param r
	 * @return
	 */
	public int updateRole(AdminRole r){
		StringBuffer sqlBuffer=new StringBuffer("update ");
		sqlBuffer.append(TableInfo.afin_admin_role);
		sqlBuffer.append(" set name=?,description=?,update_time=? where id=?"); 	
		Object[] params = new Object[]{r.getName(),r.getDescription(),r.getUpdateTime(),r.getId()};
		logger.info(sqlBuffer.toString());
		return this.getJdbcTemplate().update(sqlBuffer.toString(), params);
	}
	
	/**
	 * 根据id查角色
	 * @param id
	 * @return
	 */
	public AdminRole selRoleById(String id){
		StringBuffer sqlBuffer=new StringBuffer("select * from ");
		sqlBuffer.append(TableInfo.afin_admin_role);
		sqlBuffer.append(" where id=?");
        Object[] params = new Object[]{id};
        return this.getJdbcTemplate().queryForObject(sqlBuffer.toString(),new AdminRoleRowMapper(),params);
	}
	
	/**
	 * 删除角色
	 * @param id
	 * @return
	 */
	public int deleteRole(String id){
		StringBuffer sqlBuffer=new StringBuffer("delete from ");
		sqlBuffer.append(TableInfo.afin_admin_role);
		sqlBuffer.append(" where id=?"); 	
		Object[] params = new Object[]{id};
		logger.info(sqlBuffer.toString());
		return this.getJdbcTemplate().update(sqlBuffer.toString(), params);	
	}
	
	
	/**
	 * 新增用户-角色关系
	 * @param userId
	 * @param roleId
	 * @return
	 */
	public int addUserRoleRel(String userId,String roleId){
		StringBuffer sqlBuffer=new StringBuffer("insert into ");
		sqlBuffer.append(TableInfo.afin_admin_user_role);
		sqlBuffer.append("(user_id,role_id) values(?,?)"); 	
		Object[] params = new Object[]{userId,roleId};
		logger.info(sqlBuffer.toString());
		return this.getJdbcTemplate().update(sqlBuffer.toString(), params);
	}
	
	
	/**
	 * 删除用户-角色关系
	 * @param userId
	 * @return
	 */
	public int removeUserRoleRelByUserId(String userId){
		StringBuffer sqlBuffer=new StringBuffer("delete from ");
		sqlBuffer.append(TableInfo.afin_admin_user_role);
		sqlBuffer.append(" where user_id=?"); 	
		Object[] params = new Object[]{userId};
		logger.info(sqlBuffer.toString());
		return this.getJdbcTemplate().update(sqlBuffer.toString(), params);	
	}
	
	
	/**
	 * 删除用户-角色关系
	 * @param roleId
	 * @return
	 */
	public int removeUserRoleRelByRoleId(String roleId){
		StringBuffer sqlBuffer=new StringBuffer("delete from ");
		sqlBuffer.append(TableInfo.afin_admin_user_role);
		sqlBuffer.append(" where role_id=?"); 	
		Object[] params = new Object[]{roleId};
		logger.info(sqlBuffer.toString());
		return this.getJdbcTemplate().update(sqlBuffer.toString(), params);	
	}
	
	
	/**
	 * 批量插入 角色-权限关系
	 * @param roleId
	 * @param resId
	 * @return
	 */
	public int addRoleRes(final String roleId,final List resId){
		StringBuffer sqlBuffer=new StringBuffer("insert into ");
		sqlBuffer.append(TableInfo.afin_admin_role_res);
		sqlBuffer.append("(res_id,role_id) values(?,?)"); 			
		this.getJdbcTemplate().batchUpdate(sqlBuffer.toString(), new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, resId.get(i).toString());
				ps.setString(2, roleId);				
			}
					
			@Override
			public int getBatchSize() {
				return resId.size();
			}
		 });
		logger.info(sqlBuffer.toString());
		return 0;
	}
	
	/**
	 * 删除角色-权限关系
	 * @param roleId
	 * @return
	 */
	public int removeRoleResRel(String roleId){
		StringBuffer sqlBuffer=new StringBuffer("delete from ");
		sqlBuffer.append(TableInfo.afin_admin_role_res);
		sqlBuffer.append(" where role_id=?"); 	
		Object[] params = new Object[]{roleId};
		logger.info(sqlBuffer.toString());
		return this.getJdbcTemplate().update(sqlBuffer.toString(), params);	
	}
	
	/**
	 * 根据roleId查角色下 所有user（id和name）
	 * @param roleId
	 * @return
	 */
	public List selUserByRoleId(String roleId){
		StringBuffer sqlBuffer=new StringBuffer("select u.id,u.name,u.mobile from ");
		sqlBuffer.append(TableInfo.afin_admin_user);
		sqlBuffer.append(" u inner join "); 	
		sqlBuffer.append(TableInfo.afin_admin_user_role);
		sqlBuffer.append(" r on u.id = r.user_id where r.role_id=? "); 
		Object[] params = new Object[]{roleId};
		logger.info(sqlBuffer.toString());
		return this.getJdbcTemplate().queryForList(sqlBuffer.toString(), params);
	}
	
	/**
	 * 无该角色权限的user（id和name）
	 * @return
	 */
	public List selUserIdAndName(String roleId){
		StringBuffer sqlBuffer=new StringBuffer("select u.id,u.name,u.mobile from ");
		sqlBuffer.append(TableInfo.afin_admin_user);
		sqlBuffer.append(" u where u.ecid is null and u.id not in (select ur.user_id from "); 
		sqlBuffer.append(TableInfo.afin_admin_user_role);
		sqlBuffer.append(" ur where ur.role_id = ?) "); 
		Object[] params = new Object[]{roleId};
		logger.info(sqlBuffer.toString());
		return this.getJdbcTemplate().queryForList(sqlBuffer.toString(), params);
	}
}
