package com.yaojun.bean;

public class Device{

	private int id;
	private String number;
	private String name;
	private String type;
	private String date;
	public Device() {
		super();
	}
	/**
	 * @param number String 设备出厂编号
	 * @param name String 设备名称
	 * @param type String 设备型号
	 * @param date String 设备生产日期
	 */
	public Device(String number, String name, String type, String date) {
		super();
		this.number = number;
		this.name = name;
		this.type = type;
		this.date = date;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
}
