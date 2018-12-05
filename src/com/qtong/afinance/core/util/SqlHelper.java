package com.qtong.afinance.core.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于辅助拼接生成SQL的工具类
 *
 */
public class SqlHelper {
	private String selectClause ="SELECT * "; // Select子句
	private String fromClause; // From子句
	private String whereClause = ""; // Where子句
	private String orderByClause = ""; // OrderBy子句

	private List<Object> parameters = new ArrayList<Object>(); // 参数列表

	/**
	 * 生成From子句，默认的别名为't'
	 * 
	 * @param tableName
	 */
	public SqlHelper(String tableName) {
		this.fromClause = " FROM " + tableName;
	}

	/**
	 * 生成From子句，使用指定的别名'
	 * 
	 * @param tableName
	 * @param alias 别名
	 */
	public SqlHelper(String tableName, String alias) {
		this.fromClause = " FROM " + tableName + " " + alias;
	}

	/**
	 * 拼接Select子句
	 * 
	 * @param condition
	 * @param params
	 */
	public SqlHelper addResult(String result) {
		if(result!=null&&!result.isEmpty()){
			selectClause="SELECT "+result;
		}
		return this;
	}
	/**
	 * 拼接Where子句
	 * 
	 * @param condition
	 * @param params
	 */
	public SqlHelper addCondition(String condition, Object... params) {
		// 拼接
		if (whereClause.length() == 0) {
			whereClause = " WHERE " + condition;
		} else {
			whereClause += " AND " + condition;
		}
		
		// 保存参数
		if (params != null && params.length > 0) {
			for (Object obj : params) {
				parameters.add(obj);
			}
		}
		
		return this;
	}

	/**
	 * 如果第1个参数为true，则拼接Where子句
	 * 
	 * @param append
	 * @param condition
	 * @param params
	 */
	public SqlHelper addCondition(boolean append, String condition, Object... params) {
		if (append) {
			addCondition(condition, params);
		}
		return this;
	}

	/**
	 * 拼接Where like子句
	 * 
	 * @param condition
	 * @param params
	 */
	public SqlHelper addConditionLike(String condition, Object... params) {
		// 拼接
		if (whereClause.length() == 0) {
			whereClause = " WHERE " + condition;
		} else {
			whereClause += " AND " + condition;
		}
		
		// 保存参数
		if (params != null && params.length > 0) {
			for (Object obj : params) {
				parameters.add("%"+obj+"%");
			}
		}
		
		return this;
	}

	/**
	 * 如果第1个参数为true，则拼接Where like子句
	 * 
	 * @param append
	 * @param condition
	 * @param params
	 */
	public SqlHelper addConditionLike(boolean append, String condition, Object... params) {
		if (append) {
			addConditionLike(condition, params);
		}
		return this;
	}
	
	/**
	 * 拼接OrderBy子句
	 * 
	 * @param propertyName 属性名
	 * @param isAsc true表示升序，false表示降序
	 */
	public SqlHelper addOrder(String propertyName, boolean isAsc) {
		if (orderByClause.length() == 0) {
			orderByClause = " ORDER BY " + propertyName + (isAsc ? " ASC" : " DESC");
		} else {
			orderByClause += ", " + propertyName + (isAsc ? " ASC" : " DESC");
		}
		return this;
	}

	/**
	 * 如果第1个参数为true，则拼接OrderBy子句
	 * 
	 * @param append
	 * @param propertyName 属性名
	 * @param isAsc true表示升序，false表示降序
	 */
	public SqlHelper addOrder(boolean append, String propertyName, boolean isAsc) {
		if (append) {
			addOrder(propertyName, isAsc);
		}
		return this;
	}

	/**
	 * 获取生成的查询数据列表的SQL语句
	 * 
	 * @return
	 */
	public String getQueryListSql() {
		
		return selectClause + fromClause + whereClause + orderByClause;
	}

	/**
	 * 获取生成的查询总记录数的SQL语句（没有OrderBy子句）
	 * 
	 * @return
	 */
	public String getQueryCountSql() {
		return "SELECT COUNT(1) " + fromClause + whereClause;
	}

	/**
	 * 获取参数列表，与SQL过滤条件中的'?'一一对应
	 * 预处理成数组类型
	 * @return
	 */
	public Object[] getParameters() {
		
		return parameters.toArray();
	}
}
