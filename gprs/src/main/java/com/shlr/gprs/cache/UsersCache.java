package com.shlr.gprs.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.shlr.gprs.domain.Users;
import com.shlr.gprs.listenner.WebApplicationContextManager;
import com.shlr.gprs.services.UserService;

/**
 * @author xucong
 * @version 创建时间：2017年4月4日 下午2:10:22
 * 
 */
public class UsersCache {
	public static Map<Integer, Users> idMap = new HashMap<Integer, Users>();
	public static Map<String, Users> usernameMap = new HashMap<String, Users>();
	

	
	public static void load() {
		
		UserService userService = (UserService) WebApplicationContextManager.getApplicationContext().getBean(UserService.class);
		
		List<Users> list =userService.list();
		for (Users users : list) {
			idMap.put(Integer.valueOf(users.getId()), users);
			usernameMap.put(users.getUsername(), users);
		}
	}
}
