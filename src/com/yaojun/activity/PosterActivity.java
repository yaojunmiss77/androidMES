package com.yaojun.activity;

import com.yaojun.mes.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class PosterActivity extends Activity{
	
	private final int SPLASH_DISPLAY_LENGHT = 2000; // 延迟3秒
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.poster);	
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				Intent intent =new Intent(PosterActivity.this,LoginActivity.class);
				startActivity(intent);
				finish();
				
			}
		}, SPLASH_DISPLAY_LENGHT);
	}

}
