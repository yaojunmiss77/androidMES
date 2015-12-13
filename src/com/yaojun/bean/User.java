package com.yaojun.bean;

@SuppressWarnings("serial")
public class User implements java.io.Serializable{
	
	private int id;
	private String name;
	private String number;
	private String password;
	public User() {
		super();
	}
	/**
	 * @param name 用户的姓名
	 * @param number 用户的工号
	 * @param password 用户的密码
	 */
	public User(String name, String number, String password) {
		super();
		this.name = name;
		this.number = number;
		this.password = password;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	

}
