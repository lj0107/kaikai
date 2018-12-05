package com.qtong.afinance.module.service.admin;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qtong.afinance.core.dao.PageData;
import com.qtong.afinance.module.dao.admin.BusinessRecentnewsDao;
import com.qtong.afinance.module.pojo.recentnews.BusinessRecentnews;

/**
 * 最新动态的Service层
 *
 */
@Service("adminRecentNewsService")
@Transactional
public class RecentNewsService {
	@Autowired
	private HttpSolrClient solrClient;
	@Autowired
	private BusinessRecentnewsDao businessRecentnewsDao;
	/**
	 * -增加
	 * @return
	 */
	public int insertCoPartner(BusinessRecentnews businessRecentnews) {	
		 Timestamp ts = new Timestamp(System.currentTimeMillis());   		 
		 try {   
			 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			 String createTime = df.format(new Date());// new Date()为获取当前系统时间	      
	         ts = Timestamp.valueOf(createTime);   
	         if(businessRecentnews.getOnlineStatus().equals(0)) {			
	        	   businessRecentnews.setOnlineTime(ts);// new Date()为获取当前系统时间	
	        	   businessRecentnews.setCreateTime(ts);
	 		}else {
	 		   businessRecentnews.setCreateTime(ts);
	 		}
	        } catch (Exception e) {   
	            e.printStackTrace();   
	        }  
		
		int id = businessRecentnewsDao.insertCoPartner(businessRecentnews);
		businessRecentnews.setId(id);
		String content = businessRecentnews.getContent();
		String delHTMLTag = RecentNewsService.delHTMLTag(content);
		businessRecentnews.setContent(delHTMLTag);
		try {
			if(businessRecentnews.getOnlineStatus()==0) {
				this.getNewsListForSolr(businessRecentnews);
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return 1;
	}
	
	
	/**
	 * -普通删除
	 * @return
	 */
	public int deleteById(int id) {
		int i=businessRecentnewsDao.deleteById(id);		
		//同步Solr中信息
		try {
			solrClient.deleteById(id+"");
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				solrClient.commit();
			} catch (SolrServerException | IOException e) {
				e.printStackTrace();
			}
		}
		return i;		
	}
	/**
	 * -批量删除
	 * @return
	 */
	public int deleteAll(String id) {
		//id = id.substring(1,id.length() - 1);
		String d[] = id.split(",");
		List<String> list = new ArrayList<String>();	         
		for (int i = 0; i < d.length; i++) {
		     list.add(d[i]);	             
		}
		int i=businessRecentnewsDao.deleteAll(list);	
		int count=0;
		if(i==1) {
			count=d.length;
		}
		//同步Solr中信息
		try {
			solrClient.deleteById(list);
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				solrClient.commit();
			} catch (SolrServerException | IOException e) {
				e.printStackTrace();
			}
		}
		return count;		
	}
	/**
	 * -通过id查询
	 * @param id
	 * @return
	 */
	public String selectCoPById(int id) {
		BusinessRecentnews businessRecentnews = businessRecentnewsDao.selectCoPById(id);
		JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
		String BusinessStr = JSON.toJSONString(businessRecentnews, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);		
		return BusinessStr;
	}
	/**
	 * -更改
	 * @param coPartner
	 * @return
	 */
	public int updateRecentNews(BusinessRecentnews businessRecentnews) {
		int updateRecentNews = businessRecentnewsDao.updateRecentNews(businessRecentnews);
		String content = businessRecentnews.getContent();
		String delHTMLTag = RecentNewsService.delHTMLTag(content);
		businessRecentnews.setContent(delHTMLTag);
		try {
			if(businessRecentnews.getOnlineStatus()==0) {
			this.getNewsListForSolr(businessRecentnews);
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return updateRecentNews;
	}
	/**
	 * 更改上线状态
	 * @param businessRecentnews
	 * @return
	 */
	public int updateState(BusinessRecentnews businessRecentnews) {
		int updateState = businessRecentnewsDao.updateState(businessRecentnews);
		int id = businessRecentnews.getId();
		int state = businessRecentnews.getOnlineStatus();
		if(state==1) {
			//同步Solr中信息
			try {
				solrClient.deleteById(id+"");
			} catch (SolrServerException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					solrClient.commit();
				} catch (SolrServerException | IOException e) {
					e.printStackTrace();
				}
			}
		}else {
			BusinessRecentnews businessRecentnew = businessRecentnewsDao.selectCoPById(id);
			try {
				if(businessRecentnew.getOnlineStatus()==0) {
					this.getNewsListForSolr(businessRecentnew);
				}
			} catch (SolrServerException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return updateState;
		
	}
	/**
	 * 更改置顶状态
	 * @param businessRecentnews
	 * @return
	 */
	public int updateStick(BusinessRecentnews businessRecentnews) {
		int updateStick = businessRecentnewsDao.updateStick(businessRecentnews);
		return updateStick;
	}

	
	/**
	 * 根据条件查询
	 * @param selectByTerm 根据条件查询的字符串包含以下数据
	 * @param byKeyword 关键字模糊查询
	 * @param stickStatus 置顶状态
	 * @param onlineStatus 上线状态
	 * @param pageIndex 当前页号
	 * 根据发布时间查询：
	 * @param firstTime 开始时间
	 * @param secondTime 结束时间
	 * @return
	 */
	public String selectByTermList(String selectByTerm) {
		JSONObject  jasonObject = JSONObject.parseObject(selectByTerm);
		java.lang.String pageIndex = jasonObject.get("pageIndex").toString();
		Object byKeywordObj = jasonObject.get("byKeyword");
		String byKeyword="";
		if(!"".equals(byKeywordObj)&&byKeywordObj!=null) {
			byKeyword = byKeywordObj.toString();
		}
		Object stickStatusObj = jasonObject.get("stickStatus");
		String stickStatus="";
		if(!"".equals(stickStatusObj)&&stickStatusObj!=null) {
			stickStatus = stickStatusObj.toString();
		}
		Object onlineStatusObj = jasonObject.get("onlineStatus");
		String onlineStatus="";
		if(!"".equals(onlineStatusObj)&&onlineStatusObj!=null) {
			onlineStatus = onlineStatusObj.toString();
		}
		Object firstTimeObj = jasonObject.get("firstTime");
		String firstTime="";
		if(!"".equals(firstTimeObj)&&firstTimeObj!=null) {
			firstTime = firstTimeObj.toString();
		}
		Object secondTimeObj = jasonObject.get("secondTime");
		String secondTime="";
		if(!"".equals(secondTimeObj)&&secondTimeObj!=null) {
			secondTime = secondTimeObj.toString();
		}	
		Object onBeginTimeObj = jasonObject.get("onBeginTime");
		String onBeginTime="";
		if(!"".equals(onBeginTimeObj)&&onBeginTimeObj!=null) {
			onBeginTime = onBeginTimeObj.toString();
		}	
		Object onEndTimeObj = jasonObject.get("onEndTime");
		String onEndTime="";
		if(!"".equals(onEndTimeObj)&&onEndTimeObj!=null) {
			onEndTime = onEndTimeObj.toString();
		}	
		PageData pageData = businessRecentnewsDao.selectByTermList(pageIndex,byKeyword,stickStatus,onlineStatus,firstTime,secondTime,onBeginTime,onEndTime);
		JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";	
		String jsonString = JSON.toJSONString(pageData, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);
		return jsonString;
	}	
	/**
	 * 使用正则表达式去掉html标签
	 * @param htmlStr
	 * @return
	 */
	public static String delHTMLTag(String htmlStr){ 
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
	/**
	 * 将最新动态的数据放进solr中
	 * @param news
	 * @throws SolrServerException
	 * @throws IOException
	 */
	public void getNewsListForSolr(BusinessRecentnews news) throws SolrServerException, IOException {
			
		    //创建一个文档对象
		    SolrInputDocument document = new SolrInputDocument();
		    //添加域
		    document.addField("id", news.getId());
		    document.addField("afin_title", news.getTitle());
		    document.addField("afin_source", news.getSource());
		    document.addField("afin_img_url", news.getImgUrl());
		    document.addField("afin_content", news.getContent());
		    document.addField("afin_date_time", news.getCreateTime());
		    //document.addField("afin_online_state", news.getOnlineStatus());//上线状态
		    document.addField("afin_type", "最新动态");
		    
		    //添加到索引库
		    solrClient.add(document);
		    
		//提交
		solrClient.commit();
			
	}
	}
