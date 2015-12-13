package com.yaojun.bean;

import java.util.Date;

public class FillPressureData {
	
	private int id;
	private String deviceNumber;
	private java.util.Date date;
	private double pressure;
	public FillPressureData() {
		super();
	}
	/**
	 * 
	 * @param deviceNumber 对应设备的编号
	 * @param date 当前的时间
	 * @param pressure 当前的压力
	 */
	public FillPressureData(String deviceNumber, Date date, double pressure) {
		super();
		this.deviceNumber = deviceNumber;
		this.date = date;
		this.pressure = pressure;
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
	public double getPressure() {
		return pressure;
	}
	public void setPressure(double pressure) {
		this.pressure = pressure;
	}
	

}
