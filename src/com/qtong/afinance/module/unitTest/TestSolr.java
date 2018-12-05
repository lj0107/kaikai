package com.qtong.afinance.module.unitTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.SolrInputField;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.qtong.afinance.module.pojo.search.Search;
import com.qtong.afinance.module.pojo.search.SearchResult;
import com.qtong.afinance.module.service.portal.NewsService;
import com.qtong.afinance.module.service.search.SearchService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:WebContent/WEB-INF/applicationContext.xml",
		"file:WebContent/WEB-INF/applicationContext-redis.xml" })
public class TestSolr {

	//private static final String URL = "http://localhost:8080/solr/new_core";

	//private static HttpSolrClient solrClient = new HttpSolrClient.Builder(URL).build();
	
	@Autowired
	private NewsService newsService;
	
	@Autowired
	private HttpSolrClient solrClient;
	@Autowired
	private SearchService searchService;
	
	
	@Test
	public void add() throws SolrServerException, IOException {
		
		newsService.getNewsListForSolr();
	}
	
	
	@Test
	public void get() throws Exception {
		SearchResult result = searchService.search("解决方案", 1, 10);
		Integer count=0;
		for (Search it : result.getSearchList()) {
			System.out.println("结果："+count+it.getContent());
			System.out.println("结果："+count+it.getTitle());
			System.out.println("结果："+count+it.getSource());
			System.out.println("结果："+count+it.getType());
			
			count++;
		}
	}
	
	
	
	@Test
	public void addData() throws SolrServerException, IOException {
		//Map<String, SolrInputField> filedMap = new HashMap<String, SolrInputField>();
		Search search1 = new Search();
		Search search2 = new Search();
		Search search3 = new Search();
		Search search4 = new Search();
		Search search5 = new Search();
		Search search6 = new Search();
		Search search7 = new Search();
		Search search8 = new Search();
		Search search9 = new Search();
		search1.setId("a");
		search1.setTitle("乾坤大数据");
		search1.setSource("");
		search1.setType("产品");
		search1.setContent("依托于中国移动的海量数据资源，为金融行业客户提供面向应用场景的多种数据能力和服务，助力客户完善风险防控手段，为精细化运营提供保障。");
		search1.setImgUrl("../img/dsfdsgf.png");
		search1.setUrl("../big_data");
		
		search2.setId("b");
		search2.setTitle("和卫士");
		search2.setSource("");
		search2.setType("产品");
		search2.setContent("基于中国移动网络定位技术，为银行及支付机构提供交易用户的位置比对服务。金融行业客户可利用位置信息最大程度的校验交易用户的真实身份，发觉可疑交易行为，提前进行预警，保障持卡人财产安全，提升金融机构的风险防控水平。");
		search2.setImgUrl("../img/fghhs.png");
		search2.setUrl("../heweishi");
		
		search3.setId("c");
		search3.setTitle("网址卫士");
		search3.setSource("");
		search3.setType("产品");
		search3.setContent("网址卫士，基于中国移动内容网络能力，为银行及支付机构提供恶意网址的智能发现、智能分析、智能拦截等服务。");
		search3.setImgUrl("../img/tewdw.png");
		search3.setUrl("../site_guard");
		
		search4.setId("d");
		search4.setTitle("基础资源解决方案");
		search4.setSource("");
		search4.setType("解决方案");
		search4.setContent("凭借对行业客户需求的深刻理解及全面的产品体系，中国移动以强大的基础网络能力、一站式服务能力、全球最大的4G移动通信网络、TD-LTE在全球的广泛应用、云计算/IDC资源等为核心，为客户提供业界领先的基础资源解决方案，满足客户需求，提升用户体验。");
		search4.setImgUrl("../img/jczy_01.png");
		search4.setUrl("../basicResources");
		
		search5.setId("e");
		search5.setTitle("企业办公解决方案");
		search5.setSource("");
		search5.setType("解决方案");
		search5.setContent("凭借对行业客户需求的深刻理解及全面的产品体系，中国移动企业办公为解决客户日常内部沟通、协同办公、移动办公、会议组织需求，提供一体化解决方案。");
		search5.setImgUrl("../img/qybg_01.png");
		search5.setUrl("../businessOffice");
		
		search6.setId("f");
		search6.setTitle("营销推广解决方案");
		search6.setSource("");
		search6.setType("解决方案");
		search6.setContent("依托海量大数据资源，中国移动为行业客户定制面向不同客户群体的解决方案。");
		search6.setImgUrl("../img/yxtg_01.png");
		search6.setUrl("../marketing");
		
		search7.setId("g");
		search7.setTitle("客户服务解决方案");
		search7.setSource("");
		search7.setType("解决方案");
		search7.setContent("基于金融智能工作台、呼叫中心、位置服务、和对讲、视频监控等产品，面向不同应用场景，提供一体化解决方案。");
		search7.setImgUrl("../img/khfw_01.png");
		search7.setUrl("../customerService");
		
		search8.setId("h");
		search8.setTitle("渠道优化解决方案");
		search8.setSource("");
		search8.setType("解决方案");
		search8.setContent("通过行业WLAN、渠道共享等服务，为行业客户拓展服务渠道，延伸业务触点，建设智慧网点提供保障，实现跨界合作。");
		search8.setImgUrl("../img/qdyh_01.png");
		search8.setUrl("../channelOptimization");
		
		search9.setId("i");
		search9.setTitle("安全风控解决方案");
		search9.setSource("");
		search9.setType("解决方案");
		search9.setContent("用户数据、位置信息是中国移动的特有资源，脱敏后能够丰富行业客户的风险控制手段，完善征信模型。SIM卡能力的开放有助于行业客户保障移动端的交易安全。");
		search9.setImgUrl("../img/aqfx_01.png");
		search9.setUrl("../securityControl");
		List<Search>  searchs = new ArrayList<Search>();
		searchs.add(search1);
		searchs.add(search2);
		searchs.add(search3);
		searchs.add(search4);
		searchs.add(search5);
		searchs.add(search6);
		searchs.add(search7);
		searchs.add(search8);
		searchs.add(search9);
		
		for (Search search : searchs) {
			//创建一个文档对象
		    SolrInputDocument document = new SolrInputDocument();
		    //添加域
		    document.addField("id", search.getId());
		    document.addField("afin_source", search.getSource());
		    document.addField("afin_content", search.getContent());
		    document.addField("afin_img_url", search.getImgUrl());
		    document.addField("afin_title", search.getTitle());
		    document.addField("afin_url", search.getUrl());
		    document.addField("afin_type", search.getType());
		    document.addField("afin_date_time", search.getDateTime());
		    //添加到索引库
		    solrClient.add(document);
		}
	    //提交
	    solrClient.commit();
		
	}
	
	/*@Test
	public void getData() throws SolrServerException, IOException {
		//Map<String, SolrInputField> filedMap = new HashMap<String, SolrInputField>();
		
	    //创建一个查询对象
	    SolrQuery query = new SolrQuery();
		
	    
	    query.setQuery("*:*");
	    //执行查询
	    QueryResponse response = solrClient.query(query);
	    //取查询结果
	    SolrDocumentList solrDocumentList = response.getResults();
	    for (SolrDocument solrDocument : solrDocumentList) {
	         System.out.println(solrDocument.get("id"));
	         System.out.println(solrDocument.get("afin_source"));
	         System.out.println(solrDocument.get("afin_content"));
	    }
	    
	    
	    
		
	}*/
}








