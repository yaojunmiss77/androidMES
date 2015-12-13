package com.yaojun.activity;

import java.util.StringTokenizer;

import com.yaojun.mes.R;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;
import tool.ToastUtil;

public class MonitorActivity extends TabActivity{
	
	private String deviceNumber;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.monitor);
		
		Intent intent=getIntent();
		Bundle bundle=intent.getExtras();
		deviceNumber=bundle.getString("deviceNumber");
	
		//先转义成字符串
		  StringTokenizer content = new StringTokenizer(deviceNumber,"：");
		  //相应的取出下一个字符串
		  content.nextToken();
		  //显示出当前的设备的编号
		  deviceNumber=content.nextToken().toString();
		
		TabHost tabHost=getTabHost();
		tabHost.addTab(tabHost.newTabSpec("tab1")
				.setIndicator("设备状态"
						).setContent(new Intent(this,DeviceStatusActivity.class).putExtra("deviceNumber", deviceNumber)));
		tabHost.addTab(tabHost.newTabSpec("tab1")
				.setIndicator("模温")
						.setContent(new Intent(this,ModelTemperatureChartActivity.class).putExtra("deviceNumber", deviceNumber)));
		tabHost.addTab(tabHost.newTabSpec("tab1")
				.setIndicator("充型")
						.setContent(new Intent(this,FillChartActivity.class).putExtra("deviceNumber", deviceNumber)));
	
	}

}
