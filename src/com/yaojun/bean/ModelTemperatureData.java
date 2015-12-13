package com.yaojun.bean;

import java.util.Date;

public class ModelTemperatureData {
	
	private int id;
	private String deviceNumber;
	private java.util.Date date;
	private double temperature;
	public ModelTemperatureData() {
		super();
	}
	/**
	 * 
	 * @param deviceNumber 对应设备的编号
	 * @param date 当前的时间
	 * @param temperature  当前的温度
	 */
	public ModelTemperatureData(String deviceNumber, Date date, double temperature) {
		super();
		this.deviceNumber = deviceNumber;
		this.date = date;
		this.temperature = temperature;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDeviceNumber() {
		return deviceNumber;
	}
	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}
	public java.util.Date getDate() {
		return date;
	}
	public void setDate(java.util.Date date) {
		this.date = date;
	}
	public double getTemperature() {
		return temperature;
	}
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	

}
