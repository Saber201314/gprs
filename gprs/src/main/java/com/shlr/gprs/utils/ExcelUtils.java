package com.shlr.gprs.utils;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

public class ExcelUtils {
	/**
	 * 初始化Excel单元格样式
	 * @param wb
	 * @param sheet
	 * @param style
	 */
	public static void initPoiRowStyle(SXSSFWorkbook wb,Sheet sheet,CellStyle style){		
		
		Font font = wb.createFont();
		font.setColor(HSSFFont.COLOR_NORMAL);
		font.setFontName("黑体"); //字体
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //宽度	
		
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式	
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		style.setFont(font);
		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
		
	}
}
