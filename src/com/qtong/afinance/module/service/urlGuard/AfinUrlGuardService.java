package com.qtong.afinance.module.service.urlGuard;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import java.util.HashMap;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qtong.afinance.core.dao.PageData;
import com.qtong.afinance.core.util.DateUtil;
import com.qtong.afinance.core.util.ExportExcel;
import com.qtong.afinance.core.util.SnowflakeIdWorker;
import com.qtong.afinance.module.dao.urlGuard.AfinUrlGuardDao;
import com.qtong.afinance.module.pojo.urlGuard.AfinUrlGuard;
import com.qtong.afinance.module.pojo.urlGuard.AfinUrlGuardCode;
import com.qtong.afinance.module.pojo.urlGuard.AfinUrlGuardSaftyStoreroom;
import com.qtong.afinance.module.pojo.urlGuard.AfinUrlGuardSpiteStoreroom;
import com.qtong.afinance.module.pojo.urlGuard.AfinUrlGuardProcess;


import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
/**
 * 网址卫士service
 * 
 *
 */
@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED)
@Service
public class AfinUrlGuardService {

	@Autowired
	private AfinUrlGuardDao afinUrlGuardDao;

	/**
	 * 网址卫士通用查询
	 * 
	 * @param reqMess
	 * @return
	 * 
	 */
	@Transactional(propagation =Propagation.NOT_SUPPORTED)
	public String getAfinUrlGuardList(String reqMess) {
		Map<String, List<Map<String, Object>>> map = new HashMap<>();
		JSONObject parseObject = JSON.parseObject(reqMess);
		String pageIndex = parseObject.getString("pageIndex").toString();
		//url
		String url = parseObject.getString("url").toString();
		//快照
		String snapshot = parseObject.getString("snapshot").toString();
		//所属客户编码
		String customer_no = parseObject.getString("customer_no").toString();
		//推送时间
		String push_timeStart = parseObject.getString("push_timeStart").toString();
		String push_timeEnd = parseObject.getString("push_timeEnd").toString();
		//推送者编码
		String partner_no = parseObject.getString("partner_no").toString();
		//提交时间或者审核时间
		String opt_timeStart = parseObject.getString("opt_timeStart").toString();
		String opt_timeEnd = parseObject.getString("opt_timeEnd").toString();
		String state = parseObject.getString("state").toString();
		List<Map<String, Object>> afinUrlGuardList = afinUrlGuardDao.getAfinUrlGuardList(pageIndex, url, snapshot,
				customer_no, push_timeStart, push_timeEnd, partner_no, opt_timeStart, opt_timeEnd, state);
		map.put("afinUrlGuardList", afinUrlGuardList);

		// 判断url是否为空如果不为空查询日志
		if (!"".equals(url) && null != url) {
			List<Map<String, Object>> afinUrlGuardProcessList = afinUrlGuardDao.getAfinUrlGuardProcess(url);
			map.put("afinUrlGuardProcessList", afinUrlGuardProcessList);
		}
		//查询总数
		List<Map<String, Object>> count = afinUrlGuardDao.getAfinUrlGuardCount(url, snapshot,
				customer_no, push_timeStart, push_timeEnd, partner_no, opt_timeStart, opt_timeEnd, state);
		map.put("count", count);
		String jsonString = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect,
				SerializerFeature.WriteDateUseDateFormat);
		return jsonString;
	}

	
	/**
	 * 获取每天网址卫士的指定状态的数据总数
	 * @return
	 */
	public int getCountByCondition(String customerNumber,String state){
		return afinUrlGuardDao.getCountByCondition(customerNumber, state);
	}
	
	/**
	 * 根据网址修改状态
	 * @param urls 网址集合
	 * @param lastState 上一次的状态
	 * @param currentState 当前状态
	 * @param remark 备注 
	 * @param optId 操作人id
	 * @param optName 操作人姓名
	 * @return
	 */
	public int updateState(List<String> urls,String lastState,String currentState,String remark,String optType,String optId,String optName) {
		
		//根据url查询当前状态 日志表中的上一状态
		//AfinUrlGuard queryByURL = afinUrlGuardDao.queryByURL(url);
		//String lastState = queryByURL.getState();
		
		Date date = new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		String strDate=sdf.format(date);  
		
		//更新网址卫士信息主表
		int updateCount = afinUrlGuardDao.updateState(urls, currentState, optId, optName,date);
		int insertCount = 0;
		if(updateCount>0) {
			List<String> ids = new ArrayList<String>();
			SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
			for(int i=0;i<urls.size();i++) {
				// 生成主键ID
				long code = idWorker.nextId();
				String id = String.valueOf(code);
				ids.add(id);
			}
			//日志表中当前状态 state
			//将操作信息插入日志表
			AfinUrlGuard afinUrlGuard = new AfinUrlGuard();
			afinUrlGuard.setOptId(optId);
			afinUrlGuard.setOptName(optName);
			afinUrlGuard.setOptTime(strDate);
			insertCount =afinUrlGuardDao.insert(ids, urls, lastState, currentState, remark, optType, afinUrlGuard);
		}
		
		return insertCount;
	}

	/**
	 * 批量处理不通过 误报
	 * @param urls 网址集合
	 * @param remark 备注
	 * @param optId 操作人id
	 * @param optName 操作人name
	 * @return
	 */
	public int updateNState(List<String> urls,String currentState,String remark,String opt_type,String optId,String optName) {
		List<AfinUrlGuardProcess> list = new ArrayList<AfinUrlGuardProcess>();//初始化list
		
		SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
		int count = 0;
		
		for (int i=0;i<urls.size();i++) {

			//String currentState = map.get(url);//当前状态
			String url = urls.get(i);
			
			AfinUrlGuardProcess queryByUrlAndState = afinUrlGuardDao.queryByUrlAndState(url, currentState);
			String lastState = queryByUrlAndState.getLastState();//上一次状态
			
			Date date = new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			String strDate=sdf.format(date);
			//更新网址卫士信息主表
			int updateCount = afinUrlGuardDao.updateOneState(url, lastState, optId, optName,date);
			
			
			long code = idWorker.nextId();
			String id = String.valueOf(code);//生成的主键UUID
			
			//将操作信息插入日志表
			AfinUrlGuard afinUrlGuard = new AfinUrlGuard();
			afinUrlGuard.setOptId(optId);
			afinUrlGuard.setOptName(optName);
			afinUrlGuard.setOptTime(strDate);
			int insertOne = afinUrlGuardDao.insertOne(id, url,  currentState, lastState, remark, opt_type, afinUrlGuard);
		
			if(updateCount == 1 && insertOne == 1) {
				count++;
			}
		
		}
		
		return count;
		
	}
	
	
	/**
	 * 批量处理通过 误报
	 * @param urls 网址集合
	 * @param remark 备注
	 * @param optId 操作人id
	 * @param optName 操作人name
	 * @return
	 */
	public int updateYState(List<String> urls,String currentState,String remark,String opt_type,String optId,String optName) {
		List<AfinUrlGuardProcess> list = new ArrayList<AfinUrlGuardProcess>();//初始化list
		
		SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
		int count = 0;
		
		for (int i=0;i<urls.size();i++) {

			//String currentState = map.get(url);//当前状态
			String url = urls.get(i);
			
			AfinUrlGuardProcess queryByUrlAndState = afinUrlGuardDao.queryByUrlAndState(url, currentState);
			String lastState = queryByUrlAndState.getLastState();//上一次状态
			
			String upState = "";
			
			if(lastState.equals("105") || lastState.equals("106")) {
				upState = "203";
			}else {
				upState = "205";
			}
			
			Date date = new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			String strDate=sdf.format(date);
			//更新网址卫士信息主表
			int updateCount = afinUrlGuardDao.updateOneState(url, upState, optId, optName,date);
			
			
			long code = idWorker.nextId();
			String id = String.valueOf(code);//生成的主键UUID
			
			//将操作信息插入日志表
			AfinUrlGuard afinUrlGuard = new AfinUrlGuard();
			afinUrlGuard.setOptId(optId);
			afinUrlGuard.setOptName(optName);
			afinUrlGuard.setOptTime(strDate);
			int insertOne = afinUrlGuardDao.insertOne(id, url,  currentState, upState, remark, opt_type, afinUrlGuard);
		
			if(updateCount == 1 && insertOne == 1) {
				count++;
			}
		
		}
		
		return count;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	

	/**
	 * 下拉框：状态
	 * @return
	 */
	@Transactional(propagation =Propagation.NOT_SUPPORTED)
	public String selectSateList() {
		List<Map<String, Object>> list = afinUrlGuardDao.selectSateList();
		String jsonString = JSON.toJSONString(list);
		return jsonString;
	}
	/**
	 * 恶意网址列表
	 * @param jsonstr
	 * @return
	 */
	@Transactional(propagation =Propagation.NOT_SUPPORTED)
	public String selectSpiteList(String jsonstr) {
		JSONObject  jasonObject = JSONObject.parseObject(jsonstr);
		java.lang.String pageIndex = jasonObject.get("pageIndex").toString();
		Object snapshotObj = jasonObject.get("snapshot");
		String snapshot="";
		if(!"".equals(snapshotObj)&&snapshotObj!=null) {
			snapshot = snapshotObj.toString();
		}
		Object urlObj = jasonObject.get("url");
		String url="";
		if(!"".equals(urlObj)&&urlObj!=null) {
			url = urlObj.toString();
		}
		Object customerNameObj = jasonObject.get("customerName");
		String customerName="";
		if(!"".equals(customerNameObj)&&customerNameObj!=null) {
			customerName = customerNameObj.toString();
		}
		Object partnerNameObj = jasonObject.get("partnerName");
		String partnerName="";
		if(!"".equals(partnerNameObj)&&partnerNameObj!=null) {
			partnerName = partnerNameObj.toString();
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
		 PageData selectSpiteList = afinUrlGuardDao.selectSpiteList(pageIndex,snapshot,url,customerName,partnerName,firstTime,secondTime);
		 JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";	
		 String jsonString = JSON.toJSONString(selectSpiteList, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);
		 return jsonString;
	}
	/**
	 * 恶意网址列表-导出
	 * @param url
	 * @param partnerName
	 * @param snapshot
	 * @param customerName
	 * @param firstTime
	 * @param secondTime
	 * @return
	 */
	public String exportSpiteList(String url, String partnerName, String snapshot, String customerName,
			String firstTime, String secondTime) {
		//导出数据录入
		String[] rowsName = new String[]{"序号","网址","快照","所属客户","推送者","推送时间","进度状态"};
		List<Object[]>  dataList = new ArrayList<Object[]>();
		Object[] objs = null;
		
		int index=1;
		List<AfinUrlGuardSpiteStoreroom> exportSpiteList = afinUrlGuardDao.exportSpiteList(snapshot,url,customerName,partnerName,firstTime,secondTime);//内容list
	for (AfinUrlGuardSpiteStoreroom afinUrlGuardSpiteStoreroom : exportSpiteList) {
			objs = new Object[rowsName.length];
			objs[0] = index;
			objs[1] = afinUrlGuardSpiteStoreroom.getUrl();
			objs[2]=afinUrlGuardSpiteStoreroom.getSnapshot();
			objs[3]=afinUrlGuardSpiteStoreroom.getCustomerName();
			objs[4]=afinUrlGuardSpiteStoreroom.getPartnerName();
			objs[5]=afinUrlGuardSpiteStoreroom.getCreateTime();
			objs[6]=afinUrlGuardSpiteStoreroom.getState();
			dataList.add(objs);
			index++;
		}
		ExportExcel export=new ExportExcel("网址卫士-恶意网址", rowsName, dataList);
		String result=JSON.toJSONString(export);
		return result;
	}

	/**
	 * 1.安全网址-列表
	 * @return resp 
	 */
	@Transactional(propagation =Propagation.NOT_SUPPORTED)
	public String selectSaftyList(String jsonstr) {
		JSONObject  jasonObject = JSONObject.parseObject(jsonstr);
		java.lang.String pageIndex = jasonObject.get("pageIndex").toString();
		Object customerNameObj = jasonObject.get("customerName");
		String customerName="";
		if(!"".equals(customerNameObj)&&customerNameObj!=null) {
			customerName = customerNameObj.toString();
		}
		Object firstTimeObj = jasonObject.get("firstTime");
		String firstTime="";
		if(!"".equals(firstTimeObj)&&firstTimeObj!=null) {
			firstTime = firstTimeObj.toString();
		}
		Object secondTimeObj = jasonObject.get("secondTime");
		String secondTime="";
		if(!"".equals(secondTimeObj)&&secondTimeObj!=null) {
			secondTime= secondTimeObj.toString();
		}	
		 PageData selectSpiteList = afinUrlGuardDao.selectSaftyList(pageIndex,customerName,firstTime,secondTime);
		 JSON.DEFFAULT_DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";
		 String jsonString = JSON.toJSONString(selectSpiteList, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);
		 return jsonString;
	}
	/**
	 * 2.1安全网址-查看详情
	 * @return resp 
	 */
	@Transactional(propagation =Propagation.NOT_SUPPORTED)
	public String selectByUrlList(String jsonstr) {
		JSONObject  jasonObject = JSONObject.parseObject(jsonstr);
		java.lang.String pageIndex = jasonObject.get("pageIndex").toString();
		Object urlObj = jasonObject.get("url");
		String url="";
		if(!"".equals(urlObj)&&urlObj!=null) {
			url = urlObj.toString();
		}
		String customerNo = jasonObject.get("customerNo").toString();
		Object firstTimeObj = jasonObject.get("firstTime");
		String firstTime="";
		if(!"".equals(firstTimeObj)&&firstTimeObj!=null) {
			firstTime = firstTimeObj.toString();
		}
		Object secondTimeObj = jasonObject.get("secondTime");
		String secondTime="";
		if(!"".equals(secondTimeObj)&&secondTimeObj!=null) {
			secondTime= secondTimeObj.toString();
		}	
		 PageData selectSpiteList = afinUrlGuardDao.selectByUrlList(pageIndex,url,customerNo,firstTime,secondTime);
		 JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";	
		 String jsonString = JSON.toJSONString(selectSpiteList, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);
		 return jsonString;
	}
	/**
	 *  2.2安全网址-查看详情-导出
	 * @param url
	 * @param customerNo
	 * @param customerName
	 * @param firstTime
	 * @param secondTime
	 * @return
	 */
	public String exportByUrlList(String url, String customerNo, String customerName, String firstTime,
			String secondTime) {
		//导出数据录入
				String[] rowsName = new String[]{"序号","安全网址","入库时间"};
				List<Object[]>  dataList = new ArrayList<Object[]>();
				Object[] objs = null;
				
				int index=1;
				 List<AfinUrlGuardSaftyStoreroom> exportByUrlList = afinUrlGuardDao.exportByUrlList(url,customerNo,firstTime,secondTime);//内容list
				for (AfinUrlGuardSaftyStoreroom afinUrlGuardSaftyStoreroom : exportByUrlList) {
					objs = new Object[rowsName.length];
					objs[0] = index;
					objs[1] = afinUrlGuardSaftyStoreroom.getUrl();
					objs[2]= DateUtil.toStr(afinUrlGuardSaftyStoreroom.getCreateTime(), DateUtil.YYYY_MM_DD_HH_MM_SS);
					dataList.add(objs);
					index++;
				}
				ExportExcel export=new ExportExcel("客户名称："+customerName, "安全网址",rowsName, dataList);
				String result=JSON.toJSONString(export);
				return result;
			}
	/**
	 * 3.安全网址-普通删除
	 * @return resp 
	 */
	@Transactional(propagation =Propagation.NOT_SUPPORTED)
	public int deleteByUrl(String url) {
		int i=afinUrlGuardDao.deleteByUrl(url);
		return 0;
	}
	/**
	 * 4.安全网址-批量删除
	 * @return resp 
	 */
	@Transactional(propagation =Propagation.NOT_SUPPORTED)
	public int deleteList(String jsonstr) {
		String d[] = jsonstr.split(",");
		List<String> list = new ArrayList<String>();	         
		for (int i = 0; i < d.length; i++) {
		     list.add(d[i]);	             
		}
		int i=afinUrlGuardDao.deleteList(list);					
		int cpunt=0;
		if(i==1) {
		 cpunt=d.length;
		}
		return cpunt;		
	}
	/**
	 * 5.安全网址-导入网址
	 * @return resp 
	 */
	public int insertUrl(String reqMess) {
		JSONObject  jasonObject = JSONObject.parseObject(reqMess);
		java.lang.String body = jasonObject.get("body").toString();
		String d[] = body.split(",");
		List<AfinUrlGuardSaftyStoreroom> list = new ArrayList<AfinUrlGuardSaftyStoreroom>();	         
		for (int i = 0; i < d.length; i++) {	
			java.lang.String head = jasonObject.get("head").toString();
			AfinUrlGuardSaftyStoreroom afinUrlGuardSaftyStoreroom=JSON.parseObject(head,AfinUrlGuardSaftyStoreroom.class);
			afinUrlGuardSaftyStoreroom.setUrl(d[i]);
			list.add(afinUrlGuardSaftyStoreroom);
		}
		
		int i=afinUrlGuardDao.insertUrl(list);		
		return i;
	}
	
	/**
	 * 接受json串list集合
	 * 和安全网址作比较通过就不是恶意网址
	 * 不通过就是恶意网址
	 * @return resp
	 */
	@Transactional(propagation =Propagation.NOT_SUPPORTED)
	public int saveUrl(String str) {
		SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
		//JSONArray parseArray = JSON.parseArray(str);
		List<AfinUrlGuard> parseArray2 = JSON.parseArray(str, AfinUrlGuard.class);
		String state="";
		int s = 0;
		int r = 0;
		String opt_type = "白名单判断";
		//循环取推送过来的值
		for (AfinUrlGuard a : parseArray2) {
			int i = afinUrlGuardDao.getAfinUrlGuardUrl(a.getUrl());
			if(i>0) {
				//安全库存在恶意网址
				state="103";
			}else {
				//是恶意网址
				state="101";
				
			}
			a.setState(state);
			//添加审核网址（恶意通过或者不是恶意不通过）
			s = afinUrlGuardDao.addAfinUrlGuardUrl(a);
			//添加日志表id,url,current_state,last_stat,remark,opt_id,opt_name,opt_time,opt_type
			long code = idWorker.nextId();
			String id = String.valueOf(code);//生成的主键UUID
			if(s>0) {
				r = afinUrlGuardDao.addAfinUrlGuardProcessUrl(id,a.getUrl(),state,opt_type);
			}
		}
		//如果全部添加成功就是成功return 1;
		if(s>0&&r>0) {
			return 1;
		}
		//只要有失败就是失败 return 0;
		return 0;
	}
	/**
	 * 网址卫视通用导出
	 * @param url
	 * @param snapshot
	 * @param push_timeStart
	 * @param push_timeEnd
	 * @param partner_no
	 * @param opt_timeStart
	 * @param opt_timeEnd
	 * @param customer_no
	 * @param state
	 * @return
	 */
	public String exportWZWSList(String url, String snapshot, String push_timeStart, String push_timeEnd,
			String partner_no, String opt_timeStart, String opt_timeEnd, String customer_no, String state) {
	
		//导出数据录入
				String[] rowsName = new String[]{"序号","网址","快照","所属客户","推送时间","推送者"};
				List<Object[]>  dataList = new ArrayList<Object[]>();
				Object[] objs = null;
				
				int index=1;
				List<AfinUrlGuardCode> afinUrlGuardList = afinUrlGuardDao.ExportAfinUrlGuardList(url, snapshot,
						customer_no, push_timeStart, push_timeEnd, partner_no, opt_timeStart, opt_timeEnd, state);
			for (AfinUrlGuardCode afinUrlGuardCode : afinUrlGuardList) {
					objs = new Object[rowsName.length];
					objs[0] = index;
					objs[1] = afinUrlGuardCode.getUrl();
					objs[2]=afinUrlGuardCode.getSnapshot();
					objs[3]=afinUrlGuardCode.getCustomerName();
					objs[4]=DateUtil.toStr(DateUtil.toDate(afinUrlGuardCode.getPushTime()), DateUtil.YYYY_MM_DD_HH_MM_SS);
					objs[5]=afinUrlGuardCode.getPartnerName();
					
					dataList.add(objs);
					index++;
				}
				ExportExcel export=new ExportExcel("网址卫士", rowsName, dataList);
				String result=JSON.toJSONString(export);
				return result;
			}
	/**
	 * 恶意网址库导出
	 * @param url
	 * @param snapshot
	 * @param push_timeStart
	 * @param push_timeEnd
	 * @param partner_no
	 * @param opt_timeStart
	 * @param opt_timeEnd
	 * @param customer_no
	 * @param state
	 * @return
	 */
	public String exportEYIList(String url, String snapshot, String push_timeStart, String push_timeEnd,
			String partner_no, String opt_timeStart, String opt_timeEnd, String customer_no, String state) {
	
		//导出数据录入
				String[] rowsName = new String[]{"序号","网址","快照","所属客户","推送者","入库时间","进度状态"};
				List<Object[]>  dataList = new ArrayList<Object[]>();
				Object[] objs = null;
				
				int index=1;
				List<AfinUrlGuardCode> afinUrlGuardList = afinUrlGuardDao.ExportAfinUrlGuardList(url, snapshot,
						customer_no, push_timeStart, push_timeEnd, partner_no, opt_timeStart, opt_timeEnd, state);
			for (AfinUrlGuardCode afinUrlGuardCode : afinUrlGuardList) {
					objs = new Object[rowsName.length];
					objs[0] = index;
					objs[1] = afinUrlGuardCode.getUrl();
					objs[2]=afinUrlGuardCode.getSnapshot();
					objs[3]=afinUrlGuardCode.getCustomerName();
					objs[4]=DateUtil.toStr(DateUtil.toDate(afinUrlGuardCode.getPushTime()), DateUtil.YYYY_MM_DD_HH_MM_SS);
					objs[5]=afinUrlGuardCode.getPartnerName();
					objs[6]=afinUrlGuardCode.getDisplay();
					
					dataList.add(objs);
					index++;
				}
				ExportExcel export=new ExportExcel("网址卫士", rowsName, dataList);
				String result=JSON.toJSONString(export);
				return result;
			}
	/**
	 * 网址查询的导出
	 * @param url
	 * @param snapshot
	 * @param push_timeStart
	 * @param push_timeEnd
	 * @param partner_no
	 * @param opt_timeStart
	 * @param opt_timeEnd
	 * @param customer_no
	 * @param state
	 * @return
	 */
	public String exportWZCXList(String url, String snapshot, String push_timeStart, String push_timeEnd,
			String partner_no, String opt_timeStart, String opt_timeEnd, String customer_no, String state) {
	
		//导出数据录入
				String[] rowsName = new String[]{"序号","网址","快照","所属客户","推送者","推送时间","进度状态"};
				List<Object[]>  dataList = new ArrayList<Object[]>();
				Object[] objs = null;
				
				int index=1;
				List<AfinUrlGuardCode> afinUrlGuardList = afinUrlGuardDao.ExportAfinUrlGuardList(url, snapshot,
						customer_no, push_timeStart, push_timeEnd, partner_no, opt_timeStart, opt_timeEnd, state);
			for (AfinUrlGuardCode afinUrlGuardCode : afinUrlGuardList) {
					objs = new Object[rowsName.length];
					objs[0] = index;
					objs[1] = afinUrlGuardCode.getUrl();
					objs[2]=afinUrlGuardCode.getSnapshot();
					objs[3]=afinUrlGuardCode.getCustomerName();
					objs[4]=afinUrlGuardCode.getPartnerName();
					objs[5]=DateUtil.toStr(DateUtil.toDate(afinUrlGuardCode.getPushTime()), DateUtil.YYYY_MM_DD_HH_MM_SS);
					objs[6]=afinUrlGuardCode.getDescription();
					dataList.add(objs);
					index++;
				}
				ExportExcel export=new ExportExcel("网址卫士", rowsName, dataList);
				String result=JSON.toJSONString(export);
				return result;
			}
	/**
	 * 网址卫士导出通用查url
	 * @param reqMess
	 * @return
	 */
	@Transactional(propagation =Propagation.NOT_SUPPORTED)
	public String exportAfinUrlGuardUrl(String reqMess) {
		JSONObject parseObject = JSON.parseObject(reqMess);
		//url
		String url = parseObject.getString("url").toString();
		//快照
		String snapshot = parseObject.getString("snapshot").toString();
		//所属客户编码
		String customer_no = parseObject.getString("customer_no").toString();
		//推送时间
		String push_timeStart = parseObject.getString("push_timeStart").toString();
		String push_timeEnd = parseObject.getString("push_timeEnd").toString();
		//推送者编码
		String partner_no = parseObject.getString("partner_no").toString();
		//提交时间或者审核时间
		String opt_timeStart = parseObject.getString("opt_timeStart").toString();
		String opt_timeEnd = parseObject.getString("opt_timeEnd").toString();
		String state = parseObject.getString("state").toString();
		//查询总数
		Map<String, Object> map = afinUrlGuardDao.exportAfinUrlGuardUrl(url, snapshot, customer_no, push_timeStart, push_timeEnd, partner_no, opt_timeStart, opt_timeEnd, state);
		
		String jsonString = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect,
				SerializerFeature.WriteDateUseDateFormat);
		return jsonString;
	}

	/**
	 * 查询所有待拦截网址
	 * @return
	 */
	public JSONObject queryAllIntercepedUrl(String state){
		JSONObject jsonObj = new JSONObject();
		List<AfinUrlGuard> queryByState = afinUrlGuardDao.queryByState(state);
		jsonObj.put("list",queryByState);
		return jsonObj;
	}

	/**
	 * 查询所有安全网址库的网址
	 * @return
	 */
	public JSONObject querySafteAllIntercepedUrl(){
		JSONObject jsonObj = new JSONObject();
		List<AfinUrlGuardSaftyStoreroom> queryByState = afinUrlGuardDao.queryBySafteState();
		jsonObj.put("list",queryByState);
		return jsonObj;
	}

	
}

