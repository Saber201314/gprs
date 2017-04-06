package com.shlr.gprs.services;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.shlr.gprs.cache.UsersCache;
import com.shlr.gprs.dao.UserMapper;
import com.shlr.gprs.domain.Users;

import tk.mybatis.mapper.entity.Example;

/**
* @author xucong
* @version 创建时间：2017年4月3日 下午2:50:40
* 
*/
@Service
public class UserService implements DruidStatInterceptor{
	@Resource
	UserMapper userMapper;
	
	public Users findByUsernameAndPassword(String username,String password){
		Users users=new Users();
		users.setUsername(username);
		users.setPassword(password);
		Users result = userMapper.selectOne(users);
		Example example=new Example(Users.class);
		example.selectProperties(username);
		if (result!=null) {
			return result;
		}
		return null;
		
	}
	public List<Users> list(){
		return userMapper.selectAll();
		
	}
	/**
	 * 获取当前用户
	 * @param user
	 * @return
	 */
	public Users getCurrentUser(HttpSession session){
		
		
		Users user = (Users) session.getAttribute("user");
		if (user==null) {
			return null;
		}
		Users users = UsersCache.idMap.get(user.getId());
		if (users!=null) {
			return users;
		}
		return null;
	}
	
}
