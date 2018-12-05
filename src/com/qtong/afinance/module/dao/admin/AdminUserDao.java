package com.qtong.afinance.module.dao.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.qtong.afinance.core.dao.BaseJdbcDao;
import com.qtong.afinance.core.dao.PageData;
import com.qtong.afinance.core.dao.TableInfo;
import com.qtong.afinance.module.pojo.admin.AdminUser;
import com.qtong.afinance.module.pojo.admin.AdminUserRowMapper;



/**
 * 用户dao
 */
@Repository
public class AdminUserDao extends BaseJdbcDao{
	
	final Logger logger = LoggerFactory.getLogger(AdminUserDao.class);
	
	/**
	 * 验证登录用户
	 * @param username
	 * @param password
	 * @return
	 */
	public AdminUser getUser(String mobile,String password){
		StringBuffer sqlBuffer=new StringBuffer("select * from ");
		sqlBuffer.append(TableInfo.afin_admin_user);
		sqlBuffer.append(" where mobile=? and password=? and state=?"); 	
		Object[] params = new Object[]{mobile,password,0};
		logger.info(sqlBuffer.toString());
		try {
			return this.getJdbcTemplate().queryForObject(sqlBuffer.toString(),new AdminUserRowMapper(),params);
		} catch (EmptyResultDataAccessException e) {
		}
		return null;
		
	}
	
	
	/**
	 * 根据用户账号查询用户列表
	 * @param mobile
	 * @return
	 */
	public List<AdminUser> getUsers(String mobile){
		StringBuilder sqlBuffer = new StringBuilder("select * from ");
		sqlBuffer.append(TableInfo.afin_admin_user);
		sqlBuffer.append(" where mobile=? and state=?"); 
		Object[] params = new Object[]{mobile,0};
		logger.info(sqlBuffer.toString());
		
		List<AdminUser> query=this.getJdbcTemplate().query(sqlBuffer.toString(), new AdminUserRowMapper(),params);
		
		return query;
	}
	
	
	/**
	 * 根据用户id 查用户角色名
	 * @param id
	 * @return
	 */
	public List<AdminUser> queryRoleNames(int id){
		
		StringBuilder sql = new StringBuilder("select user.*,role.name as role_name from ");				
		sql.append(TableInfo.afin_admin_user).append(" as user left join ")
		   .append(TableInfo.afin_admin_user_role).append(" as ur on user.id = ur.user_id left join ")
		   .append(TableInfo.afin_admin_role).append(" as role on ur.role_id = role.id")
		   .append(" where user.id=?");
		
		Object[] params = new Object[]{id};
		return this.getJdbcTemplate().query(sql.toString(), new AdminUserRowMapper(),params);
	} 
	
	/**
	 * 用户管理分页
	 * 查所有管理员的id
	 * @param pageData
	 * @param name
	 * @param department
	 * @param mobile
	 * @param state
	 * @return
	 */
	public PageData queryAllAdminUser(PageData pageData,String name,String department,String job,String mobile,String state){
		
		StringBuilder sql = new StringBuilder("SELECT DISTINCT(t.id),t.* FROM(select u.* from ");				
		sql.append(TableInfo.afin_admin_user).append(" as u left join afin_admin_user_role r  on u.id=r.user_id")
		   .append(" where 1=1 ");
		
		StringBuilder sqlCount = new StringBuilder("select count(1) from ");
		sqlCount.append(TableInfo.afin_admin_user)
		        .append(" where 1=1 ");
		   	
		List<Object> list = new ArrayList<>();
		
		
		
		if (name!=null && name.trim().length()>0) {
			sql.append(" and name like ? ");			
			sqlCount.append(" and name like ? ");
			list.add("%" + name + "%");
		}
		if (department!=null && department.trim().length()>0) {
			sql.append(" and department like ? ");			
			sqlCount.append(" and department like ? ");
			list.add("%" + department + "%");
		}
		if (job!=null && job.trim().length()>0) {
			sql.append(" and job like ? ");			
			sqlCount.append(" and job like ? ");
			list.add("%" + job + "%");
		}
		if (mobile!=null && mobile.trim().length()>0) {
			sql.append(" and mobile like ? ");			
			sqlCount.append(" and mobile like ? ");
			list.add("%" + mobile + "%");
		}
		if (state!=null && state.trim().length()>0) {
			sql.append(" and state=? ");			
			sqlCount.append(" and state= ? ");
			list.add(state);
		}
		
		
		pageData.setRowCount(this.getJdbcTemplate().queryForObject(sqlCount.toString(),list.toArray(),Integer.class).intValue());
		
		sql.append(" order by r.role_id desc ) t").append(" LIMIT ").append(((pageData.getPageIndex()-1)*pageData.getPageSize())).append(" , ").append(pageData.getPageSize());
		
		
		pageData.setLst(this.getJdbcTemplate().query(sql.toString(), new AdminUserRowMapper(),list.toArray()));
		
		logger.info(sql.toString());
		return pageData;
	} 
	

	/**
	 * 用户管理分页
	 * @param pageData
	 * @param name
	 * @param department
	 * @param mobile
	 * @param state
	 * @return
	 */
	public PageData pageQuery(PageData pageData,String name,String department,String mobile,String state){
		
		
		StringBuilder sql = new StringBuilder("select user.*,role.name as role_name from ");				
		sql.append(TableInfo.afin_admin_user).append(" as user left join ")
		   .append(TableInfo.afin_admin_user_role).append(" as ur on user.id = ur.user_id left join ")
		   .append(TableInfo.afin_admin_role).append(" as role on ur.role_id = role.id where ecid!=''");
		
		StringBuilder sqlCount = new StringBuilder(" select count(1) from ");
		sqlCount.append(TableInfo.afin_admin_user).append(" as user left join ")
		   		.append(TableInfo.afin_admin_user_role).append(" as ur on user.id = ur.user_id left join ")
		   		.append(TableInfo.afin_admin_role).append(" as role on ur.role_id = role.id where 1=1");
		List<Object> list = new ArrayList<>();  	
		
		if (name!=null && name.trim().length()>0) {
			sql.append(" and name like ? ");			
			sqlCount.append(" and name like ? ");
			list.add("%" + name + "%");
		}
		if (department!=null && department.trim().length()>0) {
			sql.append(" and department like ? ");			
			sqlCount.append(" and department like ? ");
			list.add("%" + department + "%");
		}
		if (mobile!=null && mobile.trim().length()>0) {
			sql.append(" and mobile like ? ");			
			sqlCount.append(" and mobile like ? ");
			list.add("%" + mobile + "%");
		}
		if (state!=null && state.trim().length()>0) {
			sql.append(" and state="+state+" ");			
			sqlCount.append(" and state="+state+" ");
			list.add(state);
		}
		
		pageData.setRowCount(this.getJdbcTemplate().queryForObject(sqlCount.toString(),Integer.class,list.toArray()).intValue());
		
		sql.append(" order by user.create_time desc ").append(" LIMIT ").append(((pageData.getPageIndex()-1)*pageData.getPageSize())).append(" , ").append(pageData.getPageSize());
		
		
		
		pageData.setLst(this.getJdbcTemplate().query(sql.toString(), new AdminUserRowMapper(),list.toArray()));
		
		logger.info(sql.toString());
		return pageData;
	}
	
	/**
	 * 新增后台管理员
	 * @param u
	 * @return
	 */
	public int insertAdminUser(AdminUser u){
		StringBuffer sqlBuffer=new StringBuffer("insert into ");
		sqlBuffer.append(TableInfo.afin_admin_user);
		sqlBuffer.append("(mobile,password,name,mail,job,department,description,creator,create_time,updpwd_time,state,update_time) values(?,?,?,?,?,?,?,?,?,?,?,?)"); 	
		Object[] params = new Object[]{u.getMobile(),u.getPassword(),u.getName(),u.getMail(),u.getJob(),u.getDepartment(),
				u.getDescription(),u.getCreator(),u.getCreateTime(),u.getUpdpwdTime(),u.getState(),u.getUpdateTime()};
		logger.info(sqlBuffer.toString());
		return this.getJdbcTemplate().update(sqlBuffer.toString(), params);		
	}
	
	
	/**
	 * 新增后台管理员并返回主键
	 * @param u
	 * @return 主键Id
	 */
	public Integer insertUserReturnId(final AdminUser u){
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		this.getJdbcTemplate().update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				StringBuffer sqlBuffer=new StringBuffer("insert into ");
				sqlBuffer.append(TableInfo.afin_admin_user);
				sqlBuffer.append("(mobile,password,name,mail,job,department,description,creator,create_time,update_time,state) values(?,?,?,?,?,?,?,?,?,?,?)"); 	
				PreparedStatement ps=conn.prepareStatement(sqlBuffer.toString(),Statement.RETURN_GENERATED_KEYS);
				
				ps.setString(1, u.getMobile());
				ps.setString(2, u.getPassword());
				ps.setString(3, u.getName());
				ps.setString(4, u.getMail());
				ps.setString(5, u.getJob());
				ps.setString(6, u.getDepartment());
				ps.setString(7, u.getDescription());
				ps.setString(8, u.getCreator());
				ps.setTimestamp(9, u.getCreateTime());
				ps.setTimestamp(10, u.getCreateTime());
				ps.setInt(11, u.getState());
                return ps;
			}
		},keyHolder);
		
		return (Integer) keyHolder.getKey().intValue();
		
	}
	
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public AdminUser selAdminUserById(String id){
		StringBuffer sqlBuffer=new StringBuffer("select * from ");
		sqlBuffer.append(TableInfo.afin_admin_user);
		sqlBuffer.append(" where id=?");
		Object[] params = new Object[]{id};
        return this.getJdbcTemplate().queryForObject(sqlBuffer.toString(),new AdminUserRowMapper(),params);
	}
	
	
	/**
	 * 更新后台管理员
	 * @param u
	 * @return
	 */
	public int updateAdminUser(AdminUser u){
		StringBuffer sqlBuffer=new StringBuffer("update ");
		sqlBuffer.append(TableInfo.afin_admin_user);
		sqlBuffer.append(" set mobile=?,password=?,name=?,mail=?,job=?,department=?,description=?,update_time=?,updpwd_time=? where id=?"); 	
		Object[] params = new Object[]{u.getMobile(),u.getPassword(),u.getName(),u.getMail(),u.getJob(),u.getDepartment(),
				u.getDescription(),u.getUpdateTime(),u.getUpdpwdTime(),u.getId()};
		logger.info(sqlBuffer.toString());
		return this.getJdbcTemplate().update(sqlBuffer.toString(), params);		
	}
	/**
	 * 更新后台管理员
	 * @param u
	 * @return
	 */
	public int update(AdminUser u){
		
		StringBuffer sqlBuffer=new StringBuffer("update ");
		sqlBuffer.append(TableInfo.afin_admin_user);
		sqlBuffer.append(" set mobile=?,name=?,mail=?,job=?,department=?,description=?,update_time=? where id=?"); 	
		Object[] params = new Object[]{u.getMobile(),u.getName(),u.getMail(),u.getJob(),u.getDepartment(),
				u.getDescription(),u.getUpdateTime(),u.getId()};
		logger.info(sqlBuffer.toString());
		return this.getJdbcTemplate().update(sqlBuffer.toString(), params);		
	}
	/**
	 * 更新后台管理员密码
	 * @param u
	 * @return
	 */
	public int updatePassword(AdminUser u){
		
		StringBuffer sqlBuffer=new StringBuffer("update ");
		sqlBuffer.append(TableInfo.afin_admin_user);
		sqlBuffer.append(" set password=?,updpwd_time=?,update_time=? where id=?"); 	
		Object[] params = new Object[]{u.getPassword(),u.getUpdpwdTime(),u.getUpdateTime(),u.getId()};
		logger.info(sqlBuffer.toString());
		return this.getJdbcTemplate().update(sqlBuffer.toString(), params);		
	}
	
	/**
	 * 禁用/启用 后台管理员
	 * @param id
	 * @return
	 */
	public int changeUserState(String id,String state,Timestamp updateTime){
		StringBuffer sqlBuffer=new StringBuffer("update ");
		sqlBuffer.append(TableInfo.afin_admin_user);
		sqlBuffer.append(" set state=?,update_time=? where id=?"); 	
		Object[] params = new Object[]{state,updateTime,id};
		logger.info(sqlBuffer.toString());
		return this.getJdbcTemplate().update(sqlBuffer.toString(), params);	
	}
	
	/**
	 * 删除后台管理员
	 * @param id
	 * @return
	 */
	public int deleteAdminUser(String id){
		StringBuffer sqlBuffer=new StringBuffer("delete from ");
		sqlBuffer.append(TableInfo.afin_admin_user);
		sqlBuffer.append(" where id=?"); 	
		Object[] params = new Object[]{id};
		logger.info(sqlBuffer.toString());
		return this.getJdbcTemplate().update(sqlBuffer.toString(), params);	
	}

	
	
	
	/**
	 * 新增客户账号（门户登录）
	 * @param u
	 * @return
	 */
	public int insertProtalUser(AdminUser u){
		StringBuffer sqlBuffer=new StringBuffer("insert into ");
		sqlBuffer.append(TableInfo.afin_admin_user);
		sqlBuffer.append("(mobile,password,name,ecid,creator,create_time,state) values(?,?,?,?,?,?,?)"); 	
		Object[] params = new Object[]{u.getMobile(),u.getPassword(),u.getName(),u.getEcid(),u.getCreator(),u.getCreateTime(),u.getState()};
		logger.info(sqlBuffer.toString());
		return this.getJdbcTemplate().update(sqlBuffer.toString(), params);
	}
	
	/**
	 * 根据姓名查找管理员
	 * @param name
	 * @return
	 */
	public List<AdminUser> queryByName(String name) {
		
		StringBuffer sqlBuffer=new StringBuffer("select * from afin_admin_user where name=? ");

		Object[] params = new Object[] {name};
		
		return this.getJdbcTemplate().query(sqlBuffer.toString(),params,new AdminUserRowMapper());
	}
	
	/**
	 * 根据手机号查找管理员
	 * @param mobile
	 * @return
	 */
	public List<AdminUser> queryByMobile(String mobile) {
		
		StringBuffer sqlBuffer=new StringBuffer("select * from afin_admin_user where mobile=? ");
		
		Object[] params = new Object[] {mobile};
		
		return this.getJdbcTemplate().query(sqlBuffer.toString(),params,new AdminUserRowMapper());
	}
}
