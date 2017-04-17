package com.shlr.gprs.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.catalina.User;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.define.util.DefineCollectionUtil;
import com.github.pagehelper.PageRowBounds;
import com.shlr.gprs.cache.UsersCache;
import com.shlr.gprs.dao.UserMapper;
import com.shlr.gprs.domain.Users;
import com.shlr.gprs.vo.UsersVO;

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
	
	/**
	 * 根据账号密码查找用户
	 * @param username
	 * @param password
	 * @return
	 */
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
	/**
	 * 获取所有用户
	 * @return
	 */
	public List<Users> list(){
		return userMapper.selectAll();
		
	}
	/**
	 * 根据条件查询
	 * @return
	 */
	public List<Users> listByCondition(Users record){
		return userMapper.select(record);
	}
	
	public List<Users> listByExampleAndPage(Example example,Integer pageNo){
		return userMapper.selectByExampleAndRowBounds(example, new PageRowBounds((pageNo-1)*30, 30));
	}
	
	
	private List<Users> enrichAgentList(List<Users> parentAgentList) {
		if (DefineCollectionUtil.isEmpty(parentAgentList)) {
			return null;
		}
		List accoutList = new ArrayList();
		Map parentMap = new HashMap();
		for (Users user : parentAgentList) {
			user.setUserList(null);
			accoutList.add(user.getUsername());
			parentMap.put(user.getUsername(), user);
		}
		UsersVO userVO = new UsersVO();
		userVO.setAgentList(accoutList);
//		List<Users> list = this.userService.queryList(queryUsersDO);
//		for (Users user : list) {
//			Users agent = (Users) parentMap.get(user.getAgent());
//			List tempList = agent.getUserList();
//			if (tempList == null) {
//				tempList = new ArrayList();
//				agent.setUserList(tempList);
//			}
//			tempList.add(user);
//		}
		return null;
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
	public Integer updateUserByPK(Users user){
		int updateByPrimaryKey = userMapper.updateByPrimaryKey(user);
		return updateByPrimaryKey;
	}
	
}
