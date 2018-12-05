package com.qtong.afinance.core.dao;

import java.util.List;

public class PageData {
	private int pageSize = 10; 		// 页大小
	private int pageIndex; 		// 当前页号
	private int pageCount = 1; 		// 总页数
	private int rowCount = 0; 		// 总行数
	private int pageNext;	 		// 下一页
	private int pagePre; 			// 上一页
	private List<?> lst;				// 数据
	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
		
        //下一页
		setPageNext();
		
        //上一页
		setPagePre();
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount() {
		this.pageCount = rowCount % getPageSize()==0 ? rowCount / getPageSize() : rowCount/getPageSize()+1;
		if(rowCount==0){//无记录比较特殊
			this.pageCount=1;
		}
		if (pageIndex<1) {
			setPageIndex(1);
		}
		else if (pageIndex>pageCount) {
			setPageIndex(pageCount);
		}
		else {
			setPageIndex(pageIndex);
		}
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
		
        //设置总页数
		setPageCount();
	}
	public int getPageNext() {
		return pageNext;
	}
	public void setPageNext() {
		this.pageNext = this.pageIndex+1;
		if (this.pageNext > pageCount) {
			this.pageNext = pageCount;
		}
	}
	public int getPagePre() {
		return pagePre;
	}
	public void setPagePre() {
		this.pagePre = this.pageIndex - 1;
		if (this.pagePre < 1) {
			this.pagePre = 1;
		}
	}
	public List<?> getLst() {
		return lst;
	}
	public void setLst(List<?> lst) {
		this.lst = lst;
	}
}
