package com.yaojun.server;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import tool.ToastUtil;

public class OnbindService extends Service{
	
	private IBinder binder=new MyBind();
	
	public class MyBind extends Binder
	{
		public OnbindService getOnbindService(){
			
			return OnbindService.this;
		}
		
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		Log.d("yaojunLog","bind服务已经开始创建了");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		
		Log.d("yaojunLog","startCommdand已经开始创建了");
	
		return super.onStartCommand(intent, flags, startId);
	}
		

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		
		Log.d("yaojunLog", "bind服务已经销毁了");
		super.onDestroy();
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		Log.d("yaojunLog","bind已经unbind了");
		return super.onUnbind(intent);
	}

	@Override
	public void onRebind(Intent intent) {
		// TODO Auto-generated method stub
		Log.d("yaojunLog","bind重新rebind了");
		super.onRebind(intent);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		Log.d("yaojunLog","bind已经onbind了");
		return binder;
	}
	
	
	public void hello(Context context)
	{
		ToastUtil.showMessage(context, "我现在进到这个服务当中来了");
	}

}
