package com.qtong.afinance.module.dao.partners;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.qtong.afinance.core.dao.BaseJdbcDao;
import com.qtong.afinance.core.dao.PageData;
import com.qtong.afinance.core.dao.TableInfo;
import com.qtong.afinance.module.pojo.partners.AfinPartner;
import com.qtong.afinance.module.pojo.partners.AfinPartnerRowMapper;

@Repository
public class AfinPartnerDao extends BaseJdbcDao{

	public PageData queryByPage(PageData pageData) {
		String tableName = TableInfo.afin_admin_partners;
		String fieldSelect = "";
		String strWhere = "";
		Object[]  params = new Object[]{};
		
		PageData queryPage = this.queryPage(pageData, tableName,"id", fieldSelect, strWhere, params, new AfinPartnerRowMapper());
		
		return queryPage;
	}

	public int deletePartnersById(int id) {
		StringBuffer sqlBuffer=new StringBuffer("delete from ");
		sqlBuffer.append(TableInfo.afin_admin_partners);
		sqlBuffer.append(" where id=?");
		Object[] params = new Object[]{id};
        return this.getJdbcTemplate().update(sqlBuffer.toString(),params);
	}

	public int insertPartners(AfinPartner bp) {
		StringBuffer sqlBuffer=new StringBuffer("insert into ");
		sqlBuffer.append(TableInfo.afin_admin_partners);
		sqlBuffer.append("(id,name,Contact_person,Contact_number,Contact_email,Cooperation_content) values(?,?,?,?,?,?)");
		Object[] params = new Object[]{bp.getId(),bp.getName(),bp.getContactPerson(),bp.getContactNumber(),bp.getContactEmail(),bp.getCooperationContent()};
        return this.getJdbcTemplate().update(sqlBuffer.toString(),params);
	}

	public AfinPartner selectPartnersById(int id) {
		StringBuffer sqlBuffer=new StringBuffer("select * from ");
		sqlBuffer.append(TableInfo.afin_admin_partners);
		sqlBuffer.append(" where id=?");
        Object[] params = new Object[]{id};	
        AfinPartner businessPartners = (AfinPartner) this.getJdbcTemplate().queryForObject(sqlBuffer.toString(),new AfinPartnerRowMapper(),params);	
	    return businessPartners;
	}

	public int updateRecentNews(AfinPartner bps) {
		StringBuffer sqlBuffer=new StringBuffer("update ");
		sqlBuffer.append(TableInfo.afin_admin_partners);
		sqlBuffer.append(" set name=?,contact_person=?,contact_number=?,contact_email=?,cooperation_content=? where id=?");
		Object[] params = new Object[]{bps.getName(),bps.getContactPerson(),bps.getContactNumber(),bps.getContactEmail(),bps.getCooperationContent(),bps.getId()};
		return this.getJdbcTemplate().update(sqlBuffer.toString(), params);
	}

	public int selectPartnersName(String name) {
		StringBuffer sqlBuffer=new StringBuffer("select * from ");
		sqlBuffer.append(TableInfo.afin_admin_partners);
		sqlBuffer.append(" where name = ? ");	
		Object[] params = new Object[]{name};
		try {
			List<Map<String,Object>> queryForObject = this.getJdbcTemplate().queryForList(sqlBuffer.toString(),params);
			return queryForObject.size();
		} catch (Exception e) {
			return 0;
		}
		
	}

	public List<Map<String, Object>> getSiteGuardInterface() {
		StringBuilder sqlBuffer = new StringBuilder("select id,name from ");
		sqlBuffer.append(TableInfo.afin_admin_partners);
		sqlBuffer.append(" where filed_code='3'");
		 List<Map<String, Object>> queryForList = this.getJdbcTemplate().queryForList(sqlBuffer.toString());		
		return queryForList;
	}
	
	/**
	 * 查询所有合作伙伴 下拉框接口
	 * @return
	 */
	public List<Map<String, Object>> getAllPartner() {
		String sql="select id,name from afin_partner";
		 List<Map<String, Object>> queryForList = this.getJdbcTemplate().queryForList(sql);		
		return queryForList;
	}

	/**
	 * 根据id去查询产品表有没有关联
	 * @param id
	 * @return
	 */
	public int getProductById(int id) {
		StringBuffer s = new StringBuffer();
		s.append("select count(1) from afin_product where afin_product.partner_id = ?");
		Object[] params = new Object[]{id};
		try {
			return this.getJdbcTemplate().queryForInt(s.toString(),params);
		} catch (DataAccessException e) {
			return 0;
		}
	}

	/**
	 * 根据id去查接口表有没有关联
	 * @param id
	 * @return
	 */
	public int getInterfaceById(int id) {
		StringBuffer s = new StringBuffer();
		s.append("select count(1) from afin_interface where afin_interface.partner_id="+id);
		Object[] params = new Object[]{id};
		try {
			return this.getJdbcTemplate().queryForInt(s.toString(),params);
		} catch (DataAccessException e) {
			return 0;
		}
	}
}
