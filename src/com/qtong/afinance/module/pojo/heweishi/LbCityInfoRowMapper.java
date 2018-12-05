package com.qtong.afinance.module.pojo.heweishi;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
/**
 * 位置基地-城市区号RowMapper
 *
 */
public class LbCityInfoRowMapper  implements RowMapper<LbCityInfo>{

	@Override
	public LbCityInfo mapRow(ResultSet rs, int arg1) throws SQLException {
		LbCityInfo lbCityInfo = new LbCityInfo();
		lbCityInfo.setId(rs.getString("id"));
		lbCityInfo.setCitycode(rs.getString("citycode"));
		lbCityInfo.setCityComparId(rs.getString("city_compar_id"));
		return lbCityInfo;
	}

}
