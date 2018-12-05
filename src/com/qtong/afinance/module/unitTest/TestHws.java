package com.qtong.afinance.module.unitTest;

import java.text.ParseException;
import java.util.Random;

import com.qtong.afinance.core.util.DateUtil;

/**
 * 用于和位士生产数据
 *
 */
public class TestHws {

	public static void main(String[] args) throws ParseException {
		String productOrderId = "SH100000098825";
		String date ="2018-01-01 10:25:26";
		String customerNumber="E0002017122810000003";
		String orderId = "CSHW500000098810";
		int j = 66;
		insert(productOrderId,date,customerNumber,orderId,j);
	}
	
	
	public static void insert(String productOrderId,String date,String customerNumber,String orderId,int j) {
		Random random=new Random();
		
		for(int i=58;i<=j;i++) {
			/**********************afin_lbcmp表***********************************************/
			String chanl_cust_no = "201603061234540"+i;
			
			String tel = getTel();
			
			String randomString = getRandomString(15);
			
			String lbcmp = "INSERT INTO `afin_lbcmp` VALUES "
					+ "('"+chanl_cust_no+"', 'A01', '123456', '123456', '"+chanl_cust_no+"', '"+tel+"', "
					+ "'测试发送短信', '"+productOrderId+"', '"+date+"', '123456', '"+randomString+"', '1');";
			
			
			System.out.println(lbcmp);
			
			/*********************************afin_lbcmp_detail***************************************/
			String refId = getRandomString(random.nextInt(10)+10);
			String lbDetail="";
			if(i%2==0) {
				lbDetail = "INSERT INTO `afin_lbcmp_detail` VALUES ('"+refId+"', 'A01', '"+chanl_cust_no+"', '100', '成功', '24', null, '00', '"+date+"', '城市编码比对', '31');";
			}else {
				lbDetail = "INSERT INTO `afin_lbcmp_detail` VALUES ('"+refId+"', 'A01', '"+chanl_cust_no+"', '100', '失败', null, '比对不成功', '01', '"+date+"', '城市编码比对', '31');";
			}

			System.out.println(lbDetail);
		
			/************************************afin-record*****************************************************/
			String str = DateUtil.toStr(DateUtil.toDate(date,DateUtil.YYYY_MM_DD_HH_MM_SS));
			
			String record = "INSERT INTO `afin_record` VALUES "
					+ "('"+refId+"', null, '"+customerNumber+
					"', '"+orderId+"', '370002', '"+str+"', '"+str+"', '100', null);";

			
			
			System.out.println(record);
			
			
			
			
			//String chanl_cust_no1 = "2016030612345400"+(i-1);
			
			String lbcity = "INSERT INTO `afin_lbcmp_city_msg` VALUES ('"+(i*3)+"', '"+random.nextInt(100)+"', '"+chanl_cust_no+"');";
			String lbcity1 = "INSERT INTO `afin_lbcmp_city_msg` VALUES ('"+((i*3)+1)+"', '"+random.nextInt(100)+"', '"+chanl_cust_no+"');";
			String lbcity2 = "INSERT INTO `afin_lbcmp_city_msg` VALUES ('"+((i*3)+2)+"', '"+random.nextInt(100)+"', '"+chanl_cust_no+"');";
			
			System.out.println(lbcity);
			System.out.println(lbcity1);
			System.out.println(lbcity2);
			
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			
			
			
		}
		
		/*for(int i=16;i<=11;i++) {
			String chanl_cust_no = "2016030612345400"+(i-1);
			
			String lbcity = "INSERT INTO `afin_lbcmp_city_msg` VALUES ('"+(i*3-2)+"', '"+random.nextInt(100)+"', '"+chanl_cust_no+"');";
			String lbcity1 = "INSERT INTO `afin_lbcmp_city_msg` VALUES ('"+((i*3-2)+1)+"', '"+random.nextInt(100)+"', '"+chanl_cust_no+"');";
			String lbcity2 = "INSERT INTO `afin_lbcmp_city_msg` VALUES ('"+((i*3-2)+2)+"', '"+random.nextInt(100)+"', '"+chanl_cust_no+"');";
			
			System.out.println(lbcity);
			System.out.println(lbcity1);
			System.out.println(lbcity2);
		}*/
		
		/*for(int i=0;i<=10;i++) {
			
			String chanl_cust_no = "2016030612345400"+i;
			
			String refId = getRandomString(random.nextInt(10)+10);
			String lbDetail="";
			if(i%2==0) {
				lbDetail = "INSERT INTO `afin_lbcmp_detail` VALUES ('"+refId+"', 'A01', '"+chanl_cust_no+"', '100', '成功', '24', null, '00', '"+date+"', '城市编码比对', '31');";
			}else {
				lbDetail = "INSERT INTO `afin_lbcmp_detail` VALUES ('"+refId+"', 'A01', '"+chanl_cust_no+"', '100', '失败', null, '比对不成功', '01', '"+date+"', '城市编码比对', '31');";
			}

			System.out.println(lbDetail);
		
		}*/
		
		
		
	
		
		
	}
	
	
	//生成手机号
	private static String[] telFirst="134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");  
    private static String getTel() {  
        int index=getNum(0,telFirst.length-1);  
        String first=telFirst[index];  
        String second=String.valueOf(getNum(1,888)+10000).substring(1);  
        String third=String.valueOf(getNum(1,9100)+10000).substring(1);  
        return first+second+third;  
    } 
	
    public static int getNum(int start,int end) {  
        return (int)(Math.random()*(end-start+1)+start);  
    }
    
    
    //生成数字签名
    private static String string = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";   
   
 	private static String getRandomString(int length){
	      StringBuffer sb = new StringBuffer();
	      int len = string.length();
	      for (int i = 0; i < length; i++) {
	          sb.append(string.charAt(getRandom(len-1)));
	      }
	      return sb.toString();
 	}

	private static int getRandom(int i) {
		return (int) Math.round(Math.random() * (i));
	}
 	
    
    
    
}
