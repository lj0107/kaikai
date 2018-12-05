package com.qtong.afinance.module.service.portal;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qtong.afinance.core.dao.PageData;
import com.qtong.afinance.core.util.DateUtil;
import com.qtong.afinance.module.dao.portal.NewsDao;
import com.qtong.afinance.module.pojo.recentnews.BusinessRecentnews;
/**
 * 最新动态的Service层
 *
 */
@Service("portalRecentNewsService")
@Transactional
public class NewsService {
	
	@Autowired
	private HttpSolrClient solrClient;
	@Autowired
	private NewsDao businessRecentnewsDao;
/**
 * 门户-最新动态
 * @return
 */
	public String getRecentNewsList(String count) {
		PageData recentNewsList = businessRecentnewsDao.getRecentNewsList(count);
		JSON.DEFFAULT_DATE_FORMAT = "yyyy年MM月dd日";
		String jsonString = JSON.toJSONString(recentNewsList, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);
		return jsonString;		
	}
	/**
	 *  首页-最新动态（前四条）
	 * @return
	 */
		public String getRecentNewsDetails1() {
			List<BusinessRecentnews> recentNewsList = businessRecentnewsDao.getRecentNewsDetails1();
			JSON.DEFFAULT_DATE_FORMAT = "yyyy年MM月dd日";
			String jsonString = JSON.toJSONString(recentNewsList, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);
			return jsonString;		
		}
	/**
	 * 门户-最新动态详情
	 * @return
	 */
	public String getRecentNewsDetails(int id) {
			BusinessRecentnews recentNewsDetails = businessRecentnewsDao.getRecentNewsDetails(id);
			JSON.DEFFAULT_DATE_FORMAT = "yyyy年MM月dd日";
			String jsonString = JSON.toJSONString(recentNewsDetails, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);
			return jsonString;
	}
	
	/**
	 * 将最新动态的数据全部导入Solr
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	public void getNewsListForSolr() throws SolrServerException, IOException {
		List<BusinessRecentnews> list = businessRecentnewsDao.getRecentNewsList();
		
		for (BusinessRecentnews news : list) {
			
		    //创建一个文档对象
		    SolrInputDocument document = new SolrInputDocument();
		    //添加域
		    document.addField("id", news.getId());
		    document.addField("afin_title", news.getTitle());
		    document.addField("afin_source", news.getSource());
		    document.addField("afin_img_url", news.getImgUrl());
		    document.addField("afin_date_time", DateUtil.toStr(news.getCreateTime()));
		    document.addField("afin_type", "最新动态");
		    
		    String content = delHTMLTag(news.getContent());
		    
		    document.addField("afin_content", content);
		    
		    //添加到索引库
		    solrClient.add(document);
		    
	     }
		    
		//提交
		solrClient.commit();
			
	}
	
	
	
	
	/**
	 * 去html标签
	 * @param htmlStr
	 * @return
	 */
	private String delHTMLTag(String htmlStr){ 
	    String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式 
	    String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式 
	    String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式 
	     
	    Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
	    Matcher m_script=p_script.matcher(htmlStr); 
	    htmlStr=m_script.replaceAll(""); //过滤script标签 
	     
	    Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE); 
	    Matcher m_style=p_style.matcher(htmlStr); 
	    htmlStr=m_style.replaceAll(""); //过滤style标签 
	     
	    Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
	    Matcher m_html=p_html.matcher(htmlStr); 
	    htmlStr=m_html.replaceAll(""); //过滤html标签 
	
	    return htmlStr.trim(); //返回文本字符串 
	} 
	
	
}
