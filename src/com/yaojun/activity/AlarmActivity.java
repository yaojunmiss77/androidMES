package com.yaojun.activity;

import com.yaojun.mes.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class AlarmActivity extends Activity{
	TextView alarmContent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		Intent intent=getIntent();
		
		String content=(String)intent.getSerializableExtra("content");
		
		setContentView(R.layout.alarm);
		alarmContent=(TextView)findViewById(R.id.alarmContent);
		
		alarmContent.setText(content);
		
	}

}
