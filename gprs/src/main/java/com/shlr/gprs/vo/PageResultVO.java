package com.shlr.gprs.vo;

import java.util.List;

/**
* @author xucong
* @version 创建时间：2017年4月12日 下午10:08:30
* 
*/
public class PageResultVO<T> {
	private Long allRecord;
	private Integer allPage;
	private Integer pageNo;
	private List<T> list;
	/**
	 * @return the allRecord
	 */
	public Long getAllRecord() {
		return allRecord;
	}
	/**
	 * @param allRecord the allRecord to set
	 */
	public void setAllRecord(Long allRecord) {
		this.allRecord = allRecord;
	}
	/**
	 * @return the allPage
	 */
	public Integer getAllPage() {
		return allPage;
	}
	/**
	 * @param allPage the allPage to set
	 */
	public void setAllPage(Integer allPage) {
		this.allPage = allPage;
	}
	/**
	 * @return the pageNo
	 */
	public Integer getPageNo() {
		return pageNo;
	}
	/**
	 * @param pageNo the pageNo to set
	 */
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	/**
	 * @return the list
	 */
	public List<T> getList() {
		return list;
	}
	/**
	 * @param list the list to set
	 */
	public void setList(List<T> list) {
		this.list = list;
	}
	
}
