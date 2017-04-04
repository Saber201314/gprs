package com.shlr.gprs.vo;

import java.util.List;

/**
* @author xucong
* @version 创建时间：2017年4月4日 下午8:21:56
* 
*/
public class QueryChannelResourceDO {
	private Integer id;
	private Integer channelId;
	private List<Integer> idList;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public List<Integer> getIdList() {
		return idList;
	}
	public void setIdList(List<Integer> idList) {
		this.idList = idList;
	}
}
