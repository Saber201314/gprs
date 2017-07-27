package com.shlr.gprs.utils;

import java.beans.PropertyEditorSupport;
import java.util.Date;

import com.xiaoleilu.hutool.date.DateUtil;

/**
 * @author Administrator
 */

public class DateConvert extends PropertyEditorSupport{
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
        Date date = null;  
        date = DateUtil.parse(text, "yyyy-MM-dd");
        setValue(date);  
	}
}
