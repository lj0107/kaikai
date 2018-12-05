package com.qtong.afinance.module.pojo.search;

import java.util.List;

import com.qtong.afinance.module.pojo.recentnews.BusinessRecentnews;

/**
 * 搜索服务返回的结果
 *
 */
public class SearchResult {
	private List<Search> searchList;//最新动态
	private Long recordCount;//总数据数
	private int pageCount;//页大小
	private int curPage;//页码
	
	public Long getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(Long recordCount) {
		this.recordCount = recordCount;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	public List<Search> getSearchList() {
		return searchList;
	}
	public void setSearchList(List<Search> searchList) {
		this.searchList = searchList;
	}
}

