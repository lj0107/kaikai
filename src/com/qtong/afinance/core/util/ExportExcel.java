package com.qtong.afinance.core.util;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
/**
 * 导出Excel公共方法
 *
 */
public class ExportExcel{
	
	//显示的导出表的标题
	private String title;
	//导出表的列名
	private String[] rowName ;
	
	//导出sheet的名称
	private String sheetName;
	
	private List<Object[]>  dataList = new ArrayList<Object[]>();
	
	//构造方法，传入要导出的数据
	public ExportExcel(String title,String[] rowName,List<Object[]>  dataList){
		this.dataList = dataList;
		this.rowName = rowName;
		this.title = title;
	}
	
	//构造方法，传入要导出的数据
	public ExportExcel(String title,String sheetName,String[] rowName,List<Object[]>  dataList){
		this.dataList = dataList;
		this.rowName = rowName;
		this.sheetName = sheetName;
		this.title = title;
	}
	public ExportExcel() {
		super();
		// TODO Auto-generated constructor stub
	}
	/*
	 * 导出数据
	 * */
	public void  export(OutputStream out) throws Exception{
		
		
		try{
			HSSFWorkbook workbook = new HSSFWorkbook();  					// 创建工作簿对象
			HSSFSheet sheet =null;											// 创建工作表
			if(sheetName!=null&&!sheetName.equals("")){
				sheet = workbook.createSheet(sheetName);		 			
			}else {
				sheet = workbook.createSheet(title==null?"sheet":title);
			}
			
			// 产生表格标题行
			HSSFRow rowm = (HSSFRow) sheet.createRow(0);
			HSSFCell cellTiltle = rowm.createCell(0);
	        
	        //sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面  - 可扩展】
			HSSFCellStyle columnTopStyle = (HSSFCellStyle) this.getColumnTopStyle(workbook);//获取列头样式对象
			
	        
    		
			// 定义所需列数
			int columnNum;
			HSSFRow rowRowName;
	        if(title!=null) {
	        	sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, (rowName.length-1)));  
	        	cellTiltle.setCellStyle(columnTopStyle);
	        	cellTiltle.setCellValue(title);
	        	// 定义所需列数
	        	columnNum = rowName.length;
	        	rowRowName = (HSSFRow) sheet.createRow(2);				// 在索引2的位置创建行(最顶端的行开始的第二行)
	        }else {
	        	// 定义所需列数
	        	columnNum = rowName.length;
	        	rowRowName = (HSSFRow) sheet.createRow(0);				// 在索引2的位置创建行(最顶端的行开始的第一行)
			}
			
			// 将列头设置到sheet的单元格中
			for(int n=0;n<columnNum;n++){
				HSSFCell  cellRowName = rowRowName.createCell(n);				//创建列头对应个数的单元格
				cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING);				//设置列头单元格的数据类型
				HSSFRichTextString text = new HSSFRichTextString(rowName[n]);
    			cellRowName.setCellValue(text);									//设置列头单元格的值
    			cellRowName.setCellStyle(columnTopStyle);						//设置列头单元格样式
    		}
			
    		//将查询出的数据设置到sheet对应的单元格中
			for(int i=0;i<dataList.size();i++){
				
				Object[] obj = dataList.get(i);//遍历每个对象
				HSSFRow row = (HSSFRow) sheet.createRow(i+3);//创建所需的行数
	
				for(int j=0; j<obj.length; j++){
					HSSFCell  cell = null;   //设置单元格的数据类型					
					cell = row.createCell(j,HSSFCell.CELL_TYPE_STRING);
					Boolean isNum = false;//data是否为数值型
		            Boolean isInteger=false;//data是否为整数
		            Boolean isPercent=false;//data是否为百分数
		        
					HSSFCellStyle style = (HSSFCellStyle) this.getStyle(workbook);					//单元格样式对象
					if(!"".equals(obj[j]) && obj[j] != null){
						 //判断data是否为数值型
	                    isNum = obj[j].toString().matches("^(-?\\d+)(\\.\\d+)?$");
	                    //判断data是否为整数（小数部分是否为0）
	                    isInteger=obj[j].toString().matches("^([0-9]{1,}[.][0-9]*)$");
	            
	                    //判断data是否为百分数（是否包含“%”）
	                    isPercent=obj[j].toString().contains("%");				//设置单元格的值
					}
					 //如果单元格内容是数值类型，涉及到金钱（金额、本、利），则设置cell的类型为数值型，设置data的类型为数值类型
	                if (isNum && !isPercent) {
	                    HSSFDataFormat df = workbook.createDataFormat(); // 此处设置数据格式
	                    if (!isInteger) {
	                    	if(obj[j].toString().length()<10) {
	                    	style.setDataFormat(df.getBuiltinFormat("##0"));//数据格式只显示整数
	                    	 // 设置单元格格式
		                    cell.setCellStyle(style);
		                    // 设置单元格内容为double类型
		                    cell.setCellValue(Double.parseDouble(obj[j].toString()));
	                    	}else {
	                    	
	                    		cell.setCellStyle(style);
	    	                    // 设置单元格内容为字符型
	    	                	cell.setCellValue(obj[j].toString());
	                    	}
	                    }else{
	                    	style.setDataFormat(df.getBuiltinFormat("##0.00"));//保留两位小数点
	                    	 // 设置单元格格式
		                    cell.setCellStyle(style);
		                    // 设置单元格内容为double类型
		                    cell.setCellValue(Double.parseDouble(obj[j].toString()));
	                    }                   
	                   
	                    
	                } else {
	                	cell.setCellStyle(style);
	                    // 设置单元格内容为字符型
	                	cell.setCellValue(obj[j].toString());

	                }
				}
			}
			//让列宽随着导出的列长自动适应
			for (int colNum = 0; colNum < columnNum; colNum++) {
	            int columnWidth = sheet.getColumnWidth(colNum) / 256;
	            for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
	            	HSSFRow currentRow;
	                //当前行未被使用过
	                if (sheet.getRow(rowNum) == null) {
	                    currentRow = (HSSFRow) sheet.createRow(rowNum);
	                } else {
	                    currentRow = (HSSFRow) sheet.getRow(rowNum);
	                }
	                if (currentRow.getCell(colNum) != null) {
	                    Cell currentCell = currentRow.getCell(colNum);
	                    if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
	                        int length = currentCell.getStringCellValue().getBytes().length;
	                        if (columnWidth < length) {
	                            columnWidth = length;
	                        }
	                    }
	                }
	            }
	            if(colNum == 0){
	            	sheet.setColumnWidth(colNum, (columnWidth-2) * 256);
	            }else{
	            	sheet.setColumnWidth(colNum, (columnWidth+4) * 256);
	            }
	        }
			
			if(workbook !=null){
				workbook.write(out);
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	/*
	 * 导出数据
	 * */
	public void  export(List<Map<String, String>> list,String name,OutputStream out) throws Exception{
		
		
		try{
			Workbook workbook = new HSSFWorkbook();  					// 创建工作簿对象
			HSSFSheet sheet =(HSSFSheet) workbook.createSheet(name);											// 创建工作表
			
			
	        //sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面  - 可扩展】
			HSSFCellStyle columnTopStyle = (HSSFCellStyle) this.getColumnTopStyle(workbook);//获取列头样式对象
			HSSFCellStyle style = (HSSFCellStyle) this.getStyle(workbook);					//单元格样式对象
			HSSFCellStyle columnParamsStyle = (HSSFCellStyle) this.getColumnParamsStyle(workbook);//参数样式对象
	        
	        
	        
			// 定义所需列数
			int columnNum = 2;

	        for (int i = 0; i < list.size(); i++) {
	        	HSSFRow row = sheet.createRow(i);//创建所需的行数

				Map<String, String> map = list.get(i);
				String key=map.get("key");
				String value=map.get("value");
				if(!"".equals(value)&&value!=null){
					HSSFCell  cell = null;   //设置单元格的数据类型
					cell = row.createCell(0,HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(key);//设置单元格的值
					cell.setCellStyle(columnTopStyle);//设置单元格样式
					cell = row.createCell(1,HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(value);//设置单元格的值
					cell.setCellStyle(style);//设置单元格样式
				}else {
					
					// 产生表格标题行
					HSSFCell tiltle = row.createCell(0);
			        
			        sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 1));  
			        tiltle.setCellStyle(columnParamsStyle);
			        tiltle.setCellValue(key);
				}
			}
			
			
			//让列宽随着导出的列长自动适应
			for (int colNum = 0; colNum < columnNum; colNum++) {
	            int columnWidth = sheet.getColumnWidth(colNum) / 256;
	            for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
	            	HSSFRow currentRow;
	                //当前行未被使用过
	                if (sheet.getRow(rowNum) == null) {
	                    currentRow = sheet.createRow(rowNum);
	                } else {
	                    currentRow = sheet.getRow(rowNum);
	                }
	                if (currentRow.getCell(colNum) != null) {
	                	HSSFCell currentCell = currentRow.getCell(colNum);
	                    if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
	                        int length = currentCell.getStringCellValue().getBytes().length;
	                        if (columnWidth < length) {
	                            columnWidth = length;
	                        }
	                    }
	                }
	            }
	            if(colNum == 0){
	            	sheet.setColumnWidth(colNum, (columnWidth-2) * 256);
	            }else{
	            	sheet.setColumnWidth(colNum, (columnWidth+4) * 256);
	            }
	        }
			
			if(workbook !=null){
				
				
				workbook.write(out);
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	/* 
	 * 列头单元格样式
	 */    
  	public CellStyle getColumnTopStyle(Workbook workbook) {
  		
  		  // 设置字体
  		HSSFFont font = (HSSFFont) workbook.createFont();
    	  //设置字体大小
    	  font.setFontHeightInPoints((short)11);
    	  //字体加粗
    	  font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    	  //设置字体名字 
    	  font.setFontName("Courier New");
    	  //设置样式; 
    	  HSSFCellStyle style = (HSSFCellStyle) workbook.createCellStyle();
    	  //设置底边框; 
    	  style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    	  //设置底边框颜色;  
    	  style.setBottomBorderColor(HSSFColor.BLACK.index);
    	  //设置左边框;   
    	  style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
    	  //设置左边框颜色; 
    	  style.setLeftBorderColor(HSSFColor.BLACK.index);
    	  //设置右边框; 
    	  style.setBorderRight(HSSFCellStyle.BORDER_THIN);
    	  //设置右边框颜色; 
    	  style.setRightBorderColor(HSSFColor.BLACK.index);
    	  //设置顶边框; 
    	  style.setBorderTop(HSSFCellStyle.BORDER_THIN);
    	  //设置顶边框颜色;  
    	  style.setTopBorderColor(HSSFColor.BLACK.index);
    	  //在样式用应用设置的字体;  
    	  style.setFont(font);
    	  //设置自动换行; 
    	  style.setWrapText(false);
    	  //设置水平对齐的样式为居中对齐;  
    	  style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    	  //设置垂直对齐的样式为居中对齐; 
    	  style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
    	  
    	  return style;
    	  
  	}
	/* 
	 * 参数单元格样式
	 */    
  	public CellStyle getColumnParamsStyle(Workbook workbook) {
  		
  		  // 设置字体
  		HSSFFont font = (HSSFFont) workbook.createFont();
    	  //设置字体大小
    	  font.setFontHeightInPoints((short)11);
    	  //字体加粗
    	  font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    	  //设置字体名字 
    	  font.setFontName("Courier New");
    	  //设置样式; 
    	  HSSFCellStyle style = (HSSFCellStyle) workbook.createCellStyle();
    	  //设置底边框; 
    	  style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    	  //设置底边框颜色;  
    	  style.setBottomBorderColor(HSSFColor.BLACK.index);
    	  //设置左边框;   
    	  style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
    	  //设置左边框颜色; 
    	  style.setLeftBorderColor(HSSFColor.BLACK.index);
    	  //设置右边框; 
    	  style.setBorderRight(HSSFCellStyle.BORDER_THIN);
    	  //设置右边框颜色; 
    	  style.setRightBorderColor(HSSFColor.BLACK.index);
    	  //设置顶边框; 
    	  style.setBorderTop(HSSFCellStyle.BORDER_THIN);
    	  //设置顶边框颜色;  
    	  style.setTopBorderColor(HSSFColor.BLACK.index);
    	  //在样式用应用设置的字体;  
    	  style.setFont(font);
    	  //设置自动换行; 
    	  style.setWrapText(false);
    	  //设置水平对齐的样式为居中对齐;  
    	  style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
    	  //设置垂直对齐的样式为居中对齐; 
    	  style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
    	  
    	  return style;
    	  
  	}
  	/*  
	 * 列数据信息单元格样式
	 */  
  	public CellStyle getStyle(Workbook workbook) {
	  	  // 设置字体
  		HSSFFont font = (HSSFFont) workbook.createFont();
	  	  //设置字体大小
	  	  //font.setFontHeightInPoints((short)10);
	  	  //字体加粗
	  	  //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	  	  //设置字体名字 
	  	  font.setFontName("Courier New");
	  	  //设置样式; 
	  	HSSFCellStyle style = (HSSFCellStyle) workbook.createCellStyle();
	  	  //设置底边框; 
	  	  style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	  	  //设置底边框颜色;  
	  	  style.setBottomBorderColor(HSSFColor.BLACK.index);
	  	  //设置左边框;   
	  	  style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	  	  //设置左边框颜色; 
	  	  style.setLeftBorderColor(HSSFColor.BLACK.index);
	  	  //设置右边框; 
	  	  style.setBorderRight(HSSFCellStyle.BORDER_THIN);
	  	  //设置右边框颜色; 
	  	  style.setRightBorderColor(HSSFColor.BLACK.index);
	  	  //设置顶边框; 
	  	  style.setBorderTop(HSSFCellStyle.BORDER_THIN);
	  	  //设置顶边框颜色;  
	  	  style.setTopBorderColor(HSSFColor.BLACK.index);
	  	  //在样式用应用设置的字体;  
	  	  style.setFont(font);
	  	  //设置自动换行; 
	  	  style.setWrapText(false);
	  	  //设置水平对齐的样式为居中对齐;  
	  	  style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	  	  //设置垂直对齐的样式为居中对齐; 
	  	  style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	  	 
	  	  return style;
  	
  	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String[] getRowName() {
		return rowName;
	}
	public void setRowName(String[] rowName) {
		this.rowName = rowName;
	}
	public List<Object[]> getDataList() {
		return dataList;
	}
	public void setDataList(List<Object[]> dataList) {
		this.dataList = dataList;
	}
	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
}
