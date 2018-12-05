package com.qtong.afinance.core.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.qtong.afinance.core.component.IJedisClient;
import com.qtong.afinance.module.pojo.interf.Interface;

/**
 * 接口管理，数据录入
 *
 */
@Component
public class InitRedis {
	
	@Autowired
	private IJedisClient jedisClient;
	
    @Transactional
    public void init(){
        
    	//获取所有excel数据
    	List<Map<String, Object>> list = readExcel("F:\\临时数据\\redis1.xlsx");//redis表1
        
    	//将数据同步的mySQL和Redis中
        for (Map<String, Object> map : list) {
        	//mySQL同步
        	Interface i = new Interface();
        	i.setInterfaceName((String) map.get("interface_name"));
        	i.setInterfaceUrl((String) map.get("url"));
        	i.setPartnerId(Integer.valueOf((String) map.get("partner_id")));
        	i.setAfinAccount((String) map.get("afin_account"));
        	i.setAfinKey((String) map.get("afin_key"));
        	i.setServiceCode((String) map.get("service_code"));
        	i.setCustomerNumber((String) map.get("customer_number"));
        	i.setAccountType((String) map.get("account_type"));
        	
        	
        	//Redis同步
        	map.remove("interface_name");//redis不保存接口名称
			jedisClient.hset("validate_customer", (String)map.get("afin_account")+(String)map.get("service_code"), JSON.toJSONString(map));
		}
        
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
                    	XSSFCell afin_account = xssfRow.getCell(0);
                    	XSSFCell afin_key = xssfRow.getCell(1);
                    	XSSFCell service_code = xssfRow.getCell(2);
                    	XSSFCell customer_number = xssfRow.getCell(3);
                    	XSSFCell secret_id = xssfRow.getCell(4);
                    	XSSFCell secret_key = xssfRow.getCell(5);
                    	XSSFCell partner_id = xssfRow.getCell(6);
                    	XSSFCell url = xssfRow.getCell(7);
                    	XSSFCell interface_name = xssfRow.getCell(8);
                    	XSSFCell account_type = xssfRow.getCell(9);
                    	
                    	map.put("afin_account", getValue(afin_account));
                    	map.put("afin_key", getValue(afin_key));
                    	map.put("service_code", getValue(service_code));
                    	map.put("customer_number", getValue(customer_number));
                    	map.put("secret_id", getValue(secret_id));
                    	map.put("secret_key", getValue(secret_key));
                    	map.put("partner_id", getValue(partner_id));
                    	map.put("url", getValue(url));
                    	map.put("interface_name", getValue(interface_name));
                    	map.put("account_type", getValue(account_type));
                    	
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
    
    public static void main(String[] args){
        //需要自己获取bean
        ApplicationContext ac=new FileSystemXmlApplicationContext("WebContent/WEB-INF/applicationContext*.xml");
        InitRedis initRedis=(InitRedis)ac.getBean("initRedis");
        initRedis.init();
    }
}



