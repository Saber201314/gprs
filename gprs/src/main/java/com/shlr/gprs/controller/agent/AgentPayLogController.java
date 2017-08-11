package com.shlr.gprs.controller.agent;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.shlr.gprs.domain.PayLog;
import com.shlr.gprs.domain.Users;
import com.shlr.gprs.services.PayLogService;
import com.shlr.gprs.services.UserService;
import com.shlr.gprs.utils.ExcelUtils;
import com.shlr.gprs.utils.TimeUtls;
import com.shlr.gprs.vo.PayLogFmt;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;


@Controller
@RequestMapping("/agent")
public class AgentPayLogController {
	@Resource
	UserService userService;
	@Resource
	PayLogService payLogService;
	
	@RequestMapping(value="/payLogList.action")
	@ResponseBody
	public String payLogList(HttpSession session,
			@RequestParam(value="pageNo",required=false,defaultValue="1")String pageNo,
			@RequestParam(value="memo",required=false)String memo,
			@RequestParam(value="from",required=false)String from,
			@RequestParam(value="to",required=false)String to,
			@RequestParam(value="type")String type,
			Model model){
		Users currentUser = userService.getCurrentUser(session);
		Example example=new Example(PayLog.class,true,false);
		Criteria createCriteria = example.createCriteria();
		
//		createCriteria.andEqualTo("account", currentUser.getUsername());
		
		Calendar calendar=Calendar.getInstance();
		Date dfrom = new Date();
		Date dto = new Date();
		if (StringUtils.isEmpty(from)) {
			calendar.setTime(new Date());
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			dfrom = calendar.getTime();
		}else{
			dfrom=TimeUtls.timeStr2DateByDefault(from);
		}
		if (StringUtils.isEmpty(to)) {
			calendar.setTime(new Date());
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			calendar.set(Calendar.SECOND, -1);
			dto=calendar.getTime();
		}else{
			dto=TimeUtls.timeStr2DateByDefault(to);
		}
		createCriteria.andBetween("optionTime", dfrom, dto);
		
		if (!StringUtils.isEmpty(type)&&!"-1".equals(type)) {
			createCriteria.andEqualTo("type", type);
		}
		if (!StringUtils.isEmpty(memo)) {
			createCriteria.andLike("memo", "%"+memo+"%");
		}
		example.setOrderByClause(" option_time desc,id desc");
		
		List<PayLog> listByExampleAndPage = payLogService.listByExampleAndPage(example, Integer.valueOf(pageNo) );
		Page<PayLog> page=(Page<PayLog>) listByExampleAndPage;
		
		JSONObject result=new JSONObject();
		result.put("list", listByExampleAndPage);
		result.put("total", page.getTotal());
		result.put("pages", page.getPages());
		result.put("pageno", page.getPageNum());
		
		return result.toJSONString();
	}
	
	@RequestMapping(value="/exportExcelPayLogDatas.action")
	public void exportExcelChargeLogDatas(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			@RequestParam(value = "account", required = false) String account,
			@RequestParam(value = "from", required = false) String from,
			@RequestParam(value = "to", required = false) String to) throws UnsupportedEncodingException{
		Users currentUser = userService.getCurrentUser(session);
		if(currentUser == null){
			return;
		}
		int type = currentUser.getType();
		if(type != 1 && type != 2){
			return;
		}
		if(from == null || to == null){
			return;
		}
		Example example=new Example(PayLog.class,true,false);
		Criteria createCriteria = example.createCriteria();

		createCriteria.andBetween("optionTime", from, to);
		List<PayLog> listByExample = payLogService.listByExample(example);
		
		SXSSFWorkbook wb = new SXSSFWorkbook(100); 
		Sheet sheet = wb.createSheet();     //工作表对象  
	    Row nRow = sheet.createRow(0);      //行对象  
	    CellStyle style=wb.createCellStyle();//单元格样式
	       
	    ExcelUtils.initPoiRowStyle(wb, sheet, style);
	    
	    String[] titles=new String[]{"编号","类别","扣费金额（元）","当前余额（元）","明细","扣费时间"};
	    for (int i = 0; i < titles.length; i++) {
	    	Cell c = nRow.createCell(i);
	    	c.setCellValue(titles[i]);
	    	c.setCellStyle(style);
	    	sheet.setColumnWidth(i, 25*256);
		}
	    
	    CellStyle cellStyle = wb.createCellStyle();
	    cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式	
	    cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
	    
		for (int j = 0, size = listByExample.size(); j < size; j++) {
			PayLog temp = listByExample.get(j);
			Row row = sheet.createRow(j+1);  
			//行对象  
		    Cell c0 = row.createCell(0);    
		    c0.setCellValue(temp.getId());
		    c0.setCellStyle(cellStyle);
		    
		    Cell c1 = row.createCell(1); 
		    String mtype ="";
		    if(temp.getType() == 1){
		    	mtype = "冲扣值";
		    }else if(temp.getType() == 2){
		    	mtype = "充值扣费";
		    }else if(temp.getType() == 3){
		    	mtype = "失败退款";
		    }
		    c1.setCellValue(mtype);
		    c1.setCellStyle(cellStyle);
		    
		    Cell c2 = row.createCell(2); 
		    c2.setCellValue(temp.getMoney());
		    c2.setCellStyle(cellStyle);
		    Cell c3 = row.createCell(3);   
		    c3.setCellValue(temp.getBalance());
		    c3.setCellStyle(cellStyle);
		    Cell c4 = row.createCell(4);   
		    c4.setCellValue(temp.getMemo());
		    c4.setCellStyle(cellStyle);
		    Cell c5 = row.createCell(5);   
		    c5.setCellValue(TimeUtls.date2StrByDefault(temp.getOptionTime()));
		    c5.setCellStyle(cellStyle);
		}
		String filename="消费明细记录.xlsx";
		try {
	        response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes(),"iso-8859-1")  );  
	        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charest=UTF-8");  
	        response.setBufferSize(2048);
			OutputStream os=response.getOutputStream();
			wb.write(os);
			wb.close();
			os.flush();
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
