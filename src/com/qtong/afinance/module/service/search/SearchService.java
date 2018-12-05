package com.qtong.afinance.module.service.search;

import javax.transaction.Transactional;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qtong.afinance.module.dao.search.SearchDao;
import com.qtong.afinance.module.pojo.search.SearchResult;

@Transactional
@Service
public class SearchService {
	
    @Autowired
    private SearchDao searchDao;
    
    /**
     * 门户搜索
     * @param queryString 查询条件
     * @param pageIndex 当前页
     * @param rows 每页显示条数
     * @return
     * @throws Exception
     */
    public SearchResult search(String queryString, int pageIndex, int rows) throws Exception {
        //创建查询条件
        SolrQuery query = new SolrQuery();
        //设置查询条件
        query.setQuery(queryString);
        //设置分页条件
        query.setStart((pageIndex-1)*rows);
        query.setRows(rows);
        //设置默认搜索域
        query.set("df", "afin_keywords");
        //设置高亮
        query.setHighlight(true);
        query.addHighlightField("afin_content");
        query.setHighlightSimplePre("<font class=\"c_h_f_red\">");
        query.setHighlightSimplePost("</font>");
        query.setHighlightFragsize(90);
        //执行查询
        SearchResult searchResult = searchDao.search(query);
        //计算总页数
        Long recordCount = searchResult.getRecordCount();
        int pageCount = (int) (recordCount / rows);
        if (recordCount % rows > 0) {
             pageCount++;
        }
        searchResult.setPageCount(pageCount);
        searchResult.setCurPage(pageIndex);
        return searchResult;
    }
}