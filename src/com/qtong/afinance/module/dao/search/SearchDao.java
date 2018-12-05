package com.qtong.afinance.module.dao.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.qtong.afinance.module.pojo.search.Search;
import com.qtong.afinance.module.pojo.search.SearchResult;

@Repository
public class SearchDao {

	@Autowired
	private HttpSolrClient solrClient;
	
    /**
     * 门户搜索
     * @param query 查询条件
     * @return
     * @throws Exception
     */
	public SearchResult search(SolrQuery query) throws Exception{
		//执行查询
		QueryResponse response=solrClient.query(query);
        //取查询结果列表
        SolrDocumentList solrDocumentList = response.getResults();
        List<Search> searchList=new ArrayList<>();
        for (SolrDocument solrDocument : solrDocumentList) {
			//创建一个BusinessRecentafin对象
        	Search search=new Search();
        	search.setId((String) solrDocument.get("id"));
        	search.setTitle((String) solrDocument.get("afin_title"));
        	search.setSource((String) solrDocument.get("afin_source"));
        	search.setImgUrl((String) solrDocument.get("afin_img_url"));
        	
        	String url=(String) solrDocument.get("afin_url");
        	if(url!=null&&!url.equals("")){
        		search.setUrl(url);
        	}else {
				search.setUrl("../details?id="+search.getId());
			}
        	search.setContent((String) solrDocument.get("afin_content"));
        	search.setType((String) solrDocument.get("afin_type"));
        	search.setDateTime((String) solrDocument.get("afin_date_time"));
        	
        	
        	//取高亮显示
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            List<String> list = highlighting.get(solrDocument.get("id")).get("afin_content");
            String content = "";
            if (list != null && list.size() > 0) {
                 //取高亮后的结果
            	content = list.get(0);
            } else {
            	content = (String) solrDocument.get("afin_content");
            }
            search.setContent(content);
            //添加到列表
            searchList.add(search);
        	
		}
        
        SearchResult result = new SearchResult();
        result.setSearchList(searchList);
        //查询结果总数量
        result.setRecordCount(solrDocumentList.getNumFound());
        return result;
	}
}

