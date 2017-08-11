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
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.WebApplicationContext;

import com.define.util.DefineCollectionUtil;
import com.github.pagehelper.PageRowBounds;
import com.shlr.gprs.cache.UsersCache;
import com.shlr.gprs.dao.UserMapper;
import com.shlr.gprs.domain.PricePaper;
import com.shlr.gprs.domain.Users;
import com.shlr.gprs.vo.UsersVO;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
* @author xucong
* @version 创建时间：2017年4月3日 下午2:50:40
* 
*/
@Service
public class UserService implements DruidStatInterceptor{
	@Resource
	UserMapper userMapper;
	@Resource
	PricePaperService pricePaperService;
	
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
		
		Example example=new Example(Users.class,true,false);
		Criteria createCriteria = example.createCriteria();
		
		createCriteria.andEqualTo("username", username);
		createCriteria.andEqualTo("password", password);
		
		List<Users> selectByExample = userMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(selectByExample)&&selectByExample.size()>0) {
				return selectByExample.get(0);
		}
		return null;
	}
	/**
	 * 验证账号是否存在
	 * @param username
	 * @return
	 */
	public boolean validateUsername(String username){
		Users users=new Users();
		users.setUsername(username);
		
		Example example=new Example(Users.class,true,false);
		Criteria createCriteria = example.createCriteria();
		
		createCriteria.andEqualTo("username", username);
		
		int selectCountByExample = userMapper.selectCountByExample(example);
		if (selectCountByExample > 0) {
				return true;
		}
		return false;
	}
	public Users findByUsername(String username){
		Users users=new Users();
		users.setUsername(username);
		
		Example example=new Example(Users.class,true,false);
		Criteria createCriteria = example.createCriteria();
		
		createCriteria.andEqualTo("username", username);
		
		List<Users> selectByExample = userMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(selectByExample)&&selectByExample.size()>0) {
			return selectByExample.get(0);
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
	 * 新增用户
	 * @param user
	 * @return
	 */
	public Integer add(Users user){
		return userMapper.insertSelective(user);
	}
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	public Integer updateUserByPK(Users user){
		int updateByPrimaryKey = userMapper.updateByPrimaryKeySelective(user);
		return updateByPrimaryKey;
	}
	public Integer updateUserPassword(Integer id,String password){
		return userMapper.updatePassword(id,password);
	}
	/**
	 * 更新余额
	 * @param userId
	 * @param money
	 * @return
	 */
	public Integer updateMoney(int userId, double money){
		return userMapper.updatebalance(userId, money);
	}
	public Double getBalance(int userId){
		return userMapper.getBalance(userId);
	}
	/**
	 * 根据ID查找
	 * @param userid
	 * @return
	 */
	public Users findById(Integer userid){
		return userMapper.selectByPrimaryKey(userid);
	}
	/**
	 * 根据条件查询
	 * @return
	 */
	public List<Users> listByExample(Example example){
		List<Users> selectByExampleAndRowBounds = userMapper.selectByExample(example);
		for (Users users : selectByExampleAndRowBounds) {
			List<PricePaper> listAll = pricePaperService.listAll();
			for (PricePaper pricePaper : listAll) {
				if(pricePaper.getId() == users.getPaperId()){
					users.setPaper(pricePaper);
				}
				
			}
			
		}
		return selectByExampleAndRowBounds;
	}
	
	public List<Users> listByExampleAndPage(Example example,Integer pageNo){
		List<Users> selectByExampleAndRowBounds = userMapper.selectByExampleAndRowBounds(example, new PageRowBounds((pageNo-1)*30, 30));
		List<PricePaper> listAll = pricePaperService.listAll();
		for (Users users : selectByExampleAndRowBounds) {
			for (PricePaper pricePaper : listAll) {
				if(pricePaper.getId() == users.getPaperId()){
					users.setPaper(pricePaper);
				}
				
			}
		}
		return selectByExampleAndRowBounds;
	}
	
	
//	private List<Users> enrichAgentList(List<Users> parentAgentList) {
//		if (DefineCollectionUtil.isEmpty(parentAgentList)) {
//			return null;
//		}
//		List accoutList = new ArrayList();
//		Map parentMap = new HashMap();
//		for (Users user : parentAgentList) {
//			user.setUserList(null);
//			accoutList.add(user.getUsername());
//			parentMap.put(user.getUsername(), user);
//		}
//		UsersVO userVO = new UsersVO();
//		userVO.setAgentList(accoutList);
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
//		return null;
//	}
	
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
		Users users = findById(user.getId());
		if (users!=null) {
			return users;
		}
		return null;
	}
	
	
}
