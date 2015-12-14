package com.yaojun.activity;

import java.util.StringTokenizer;

import com.yaojun.mes.R;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

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
		  
		 View deviceStatusView = (View) LayoutInflater.from(this).inflate(R.layout.tabweight, null);  
	     TextView text0 = (TextView) deviceStatusView.findViewById(R.id.tab_label);  
	     text0.setText("设备状态");  
	     
	     View modelTemView = (View) LayoutInflater.from(this).inflate(R.layout.tabweight, null);  
	     TextView text1 = (TextView) modelTemView.findViewById(R.id.tab_label);  
	     text1.setText("模温状态");  
	     
	     View fillView = (View) LayoutInflater.from(this).inflate(R.layout.tabweight, null);  
	     TextView text2 = (TextView) fillView.findViewById(R.id.tab_label);  
	     text2.setText("充型状态");  
		
		TabHost tabHost=getTabHost();
		
		tabHost.addTab(tabHost.newTabSpec("tab1")
				.setIndicator("设备状态",getResources().getDrawable(R.drawable.tab_device)).setContent(new Intent(this,DeviceStatusActivity.class).putExtra("deviceNumber", deviceNumber)));
		tabHost.addTab(tabHost.newTabSpec("tab1")
				.setIndicator("模温状态",getResources().getDrawable(R.drawable.tab_temperature))
						.setContent(new Intent(this,ModelTemperatureChartActivity.class).putExtra("deviceNumber", deviceNumber)));
		tabHost.addTab(tabHost.newTabSpec("tab1")
				.setIndicator("充型状态",getResources().getDrawable(R.drawable.tab_freezing))
						.setContent(new Intent(this,FillChartActivity.class).putExtra("deviceNumber", deviceNumber)));
	
	}

}
