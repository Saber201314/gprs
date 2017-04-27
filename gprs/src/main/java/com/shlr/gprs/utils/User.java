package com.shlr.gprs.utils;

/**
* @author xucong
* @version 创建时间：2017年4月28日 上午12:14:48
* 
*/
public class User {
	private String name;
	private String pwd;
	
	
	
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the pwd
	 */
	public String getPwd() {
		return pwd;
	}

	/**
	 * @param pwd the pwd to set
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public User(){
		
	}
	
	private User(Builder builder){
		this.name=builder.name;
		this.pwd=builder.pwd;
	}
	
	
	public static class Builder{
		String name;
		String pwd;
		public Builder(){
			
		}
		public Builder name(String name){
			this.name=name;
			return this;
		}
		public Builder pwd(String pwd){
			this.pwd=pwd;
			return this;
		}
		public User build(){
			return new User(this);
		}
		
		
	}
}
