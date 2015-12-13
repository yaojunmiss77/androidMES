package com.yaojun.DB;

public class Tables {
	
	
	//创建当前的设备部表单
	final static String CREATE_TABLE_DEVICE=
			"create table Device("
					+"id integer primary key autoincrement,"
					+"number nvarchar(50),"
					+"name nvarchar(50),"
					+"type nvarchar(50),"
					+ "date nvarchar(50))";
	//创建当前的模温曲线的数据
	final static String CREATE_TABLE_MODELTEMDATA="create table ModelTemData("
			+"id integer primary key autoincrement,"
			+ "deviceNumber nvarchar(30),"
			+ "date datetime,"
			+ "temperature nvarchar(50))";
	//创建当前的充型压力曲线
	final static String CREATE_TABLE_FILLPRESSURE="create table FillPressure("
			+"id integer primary key autoincrement,"
			+ "deviceNumber nvarchar(30),"
			+ "date datetime,"
			+ "pressure nvarchar(50))";
	//创建当前的充型压力曲线
		final static String CREATE_TABLE_USER="create table User("
				+"id integer primary key autoincrement,"
				+ "name nvarchar(30),"
				+ "nubmer nvarchar(30),"
				+ "password nvarchar(30))";
	
}
