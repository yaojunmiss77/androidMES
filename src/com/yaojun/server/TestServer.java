package com.yaojun.server;

import com.yaojun.activity.LoginActivity;
import com.yaojun.mes.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class TestServer extends Service{
	
	static final int NOTIFICATION_ID = 0x123;
	NotificationManager nm;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.d("fdsafdsafdsa","我现在开机了");
		
Intent intent1=new Intent(TestServer.this,LoginActivity.class);
		
		PendingIntent pi=PendingIntent.getActivity(TestServer.this,0,intent1,0);
			
			// 获取系统的NotificationManager服务
					nm = (NotificationManager) 
						getSystemService(NOTIFICATION_SERVICE);
					
					
					Notification notify = new Notification.Builder(this)
							// 设置打开该通知，该通知自动消失
							.setAutoCancel(true)
							// 设置显示在状态栏的通知提示信息
							.setTicker("有新消息")
							// 设置通知的图标
							.setSmallIcon(R.drawable.devic)
							// 设置通知内容的标题
							.setContentTitle("一条新通知")
							// 设置通知内容
							.setContentText("恭喜你，您加薪了，工资增加20%!")
							// // 设置使用系统默认的声音、默认LED灯
							 .setDefaults(Notification.DEFAULT_SOUND
							 |Notification.DEFAULT_LIGHTS)
							// 设置通知的自定义声音
							.setWhen(System.currentTimeMillis())
							// 设改通知将要启动程序的Intent
							.setContentIntent(pi).build();
						// 发送通知
						nm.notify(NOTIFICATION_ID, notify);
	}



	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	
	
}
