package com.qtong.afinance.module.unitTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.qtong.afinance.core.util.DateUtil;
import com.qtong.afinance.core.util.SnowflakeIdWorker;
import com.qtong.afinance.module.pojo.bill.Bill;
import com.qtong.afinance.module.pojo.statistics.OrderStats;
import com.qtong.afinance.module.service.bill.PortalBillService;
import com.qtong.afinance.module.service.statistics.OrderStatsService;

/**
 * 统计数据录入
 *
 */
@Component
public class InitStats {
	
	@Autowired
	private OrderStatsService statsService;
	@Autowired
	private PortalBillService billService;
	
	 @Test
    public  void importData(){
        //需要自己获取bean
        @SuppressWarnings("resource")
		ApplicationContext ac=new FileSystemXmlApplicationContext("WebContent/WEB-INF/applicationContext*.xml");
        InitStats initStats=(InitStats)ac.getBean("initStats");
        initStats.init();
    }
	    
	
	
	
    @Transactional
    public void init(){
        
    	//获取所有excel数据
    	List<Map<String, Object>> list = readExcel("E:\\临时数据\\浦发数据.xlsx");//浦发统计数据
    	
    	String productOrderId="100000166686";//产品订购关系ID
		String productCode="370004";//产品编码
		String productName="ID与手机号匹配情况标签";//产品名称
		String customerNumber="E0002017103110010884";//客户编号   	
		Date minDayDateTime = new Date();
		Double feeCount = 0.00;
		
    	//将统计数据添加到 统计表中
        for (Map<String, Object> map : list) {
        	
        	OrderStats stats=new OrderStats();
        	stats.setProductOrderId(productOrderId);
        	stats.setProductCode(productCode);
        	stats.setProductName(productName);
        	stats.setCustomerNumber(customerNumber);
        	
        	minDayDateTime = DateUtil.getMaxDayDateTime(DateUtil.toDate((String)map.get("record_time"),DateUtil.YYYY_MM_DD));
        	stats.setRecordTime( new Timestamp((minDayDateTime).getTime()));
        	
        	Double d=Double.parseDouble( (String) map.get("count"));
        	stats.setCount(d.intValue());
        	Double fee = Double.parseDouble((String) map.get("count"))*0.68;
        	stats.setFee(fee);
        	
        	feeCount += fee;
        	
        	//添加统计表
        	statsService.insert(stats);
        	
		}
        /**
         * 添加账单表
         */
        List<Bill> bills = new ArrayList<Bill>();
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);//生成主键
		Bill bill = new Bill();
		long code = idWorker.nextId();
		String id = String.valueOf(code);//生成id
		bill.setBillNumber(id);//设置账单id
		bill.setProductOrderId(productOrderId);//产品订购关系ID
		String orderTime = "2017-10-26 11:09:43";
		Date orderDate = DateUtil.toDate(orderTime,DateUtil.YYYY_MM_DD_HH_MM_SS);
		bill.setOrderTime( new Timestamp((orderDate).getTime()));
		bill.setCustomerNumber(customerNumber);
		bill.setCustomerName("上海浦东发展银行股份有限公司信用卡中心 ");
		bill.setProductName("数据标签");
		bill.setFee(feeCount);
			
		Timestamp ts = new Timestamp(System.currentTimeMillis());   
        try {   
            ts = Timestamp.valueOf(DateUtil.toStr(DateUtil.getMaxMonthDate(minDayDateTime),DateUtil.YYYY_MM_DD_HH_MM_SS));   
        } catch (Exception e) {   
            e.printStackTrace();   
        }  
		bill.setBillTime(ts);
		
		bills.add(bill);
		
		billService.insertBills(bills);
        
    }
	
   

    /**
     * 读取后缀为xlsx的excel文件的数据
     * @param path
     * @return
     */
    @SuppressWarnings("resource")
	private List<Map<String,Object>> readExcel(String path) {

        XSSFWorkbook xssfWorkbook = null;
        try {
            InputStream is = new FileInputStream(path);
            xssfWorkbook = new XSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        List<Map<String, Object>> list=new ArrayList<>();
        if(xssfWorkbook!=null){
            // Read the Sheet
            for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
                XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
                if (xssfSheet == null) {
                    continue;
                }
                // Read the Row
                for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                    XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                    if (xssfRow != null) {
                    	Map<String, Object> map=new HashMap<>();
                    	XSSFCell record_time = xssfRow.getCell(0);
                    	XSSFCell count = xssfRow.getCell(1);
                    	//XSSFCell fee = xssfRow.getCell(2);
                    	//XSSFCell fee2 = xssfRow.getCell(4);
                    	
                    	map.put("record_time", getValue(record_time));
                    	map.put("count", getValue(count));
                    	//map.put("fee", getValue(fee));
                    	//map.put("fee2", getValue(fee2));
                    	
                    	
                        list.add(map);
                    }
                }
            }
        }
        return list;
    }
    
    
    /**
     * 判断后缀为xlsx的excel文件的数据类型
     * @param xssfRow
     * @return
     */
    @SuppressWarnings("static-access")
    private String getValue(XSSFCell xssfRow) {
        if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
            return String.valueOf(xssfRow.getBooleanCellValue());
        } else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
            return String.valueOf(xssfRow.getNumericCellValue());
        } else {
            return String.valueOf(xssfRow.getStringCellValue());
        }
    }
    
  
}



