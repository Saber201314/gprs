package com.shlr.gprs.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shlr.gprs.domain.Users;
import com.shlr.gprs.listenner.WebApplicationContextManager;
import com.shlr.gprs.services.UserService;

/**
 * @author xucong
 * @version 创建时间：2017年4月4日 下午2:10:22
 * User缓存类
 */
public class UsersCache {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	private Map<Integer, Users> idMap = new HashMap<Integer, Users>();
	private Map<String, Users> usernameMap = new HashMap<String, Users>();
	
	public static UsersCache getInstance(){
		return UsersCacheHolder.usersCache;
		
	}
	static class UsersCacheHolder{
		static UsersCache usersCache=new UsersCache();
	}
	
	public void load() {
		long start = System.currentTimeMillis();
		logger.info("{} initialization started",this.getClass().getSimpleName());
		
		UserService userService = (UserService) WebApplicationContextManager.getApplicationContext().getBean(UserService.class);
		List<Users> list =userService.list();
		for (Users users : list) {
			idMap.put(Integer.valueOf(users.getId()), users);
			usernameMap.put(users.getUsername(), users);
		}
		
		long end = System.currentTimeMillis();
		logger.info("{} initialization completed in {} ms ",this.getClass().getSimpleName(),end-start);
	}
	public void updateCache(Users users){
		idMap.put(users.getId(), users);
		usernameMap.put(users.getUsername(), users);
	}
	public Users getById(Integer id){
		return idMap.get(id);
	}
	public Users getByAccount(String account){
		return usernameMap.get(account);
	}
}
