package com.shlr.gprs.vo;

import java.util.List;

import com.shlr.gprs.domain.Users;

/**
 * @author Administrator
 * 
 * 
 */

public class UsersVO extends Users{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7828718480806541390L;
	
	private List<String> agentList;

	public List<String> getAgentList() {
		return agentList;
	}

	public void setAgentList(List<String> agentList) {
		this.agentList = agentList;
	}
	
}
