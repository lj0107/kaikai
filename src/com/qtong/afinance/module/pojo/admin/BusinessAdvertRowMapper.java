package com.qtong.afinance.module.pojo.admin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

public class BusinessAdvertRowMapper implements RowMapper<BusinessAdvert> {

	@Override
	public BusinessAdvert mapRow(ResultSet rs, int arg1) throws SQLException {

		BusinessAdvert advert = new BusinessAdvert();
		advert.setId(rs.getString("id"));
		advert.setAdvert_address(rs.getString("advert_address"));
		advert.setAdvert_model(rs.getString("advert_model"));
		advert.setAdvert_pic(rs.getString("advert_pic"));
		advert.setAdvert_size(rs.getString("advert_size"));
		advert.setAdvert_sort(rs.getString("advert_sort"));
		advert.setAdvert_states(rs.getString("advert_states"));
		advert.setAdvert_title(rs.getString("advert_title"));
		advert.setAdvert_url(rs.getString("advert_url"));
		advert.setCreate_time(rs.getString("create_time"));
		advert.setOnline_time(rs.getString("online_time"));
		advert.setUpdate_time(rs.getString("update_time"));
		advert.setCustom_time(rs.getString("custom_time"));
		return advert;
	}

}
