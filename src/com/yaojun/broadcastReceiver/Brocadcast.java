package com.yaojun.broadcastReceiver;

import com.yaojun.server.TestServer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import tool.ToastUtil;

public class Brocadcast extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		ToastUtil.showMessage(context, "我已经开机了哟");
		
		/*Intent Iintent=new Intent(context,TestServer.class);
		Iintent.setAction("com.yaojun.server.FirstServer");
		context.startService(Iintent);*/
		
		//实现开机自动开启app的例子
		 //启动应用，参数为需要自动启动的应用的包名 
		   Intent intent1 = context.getPackageManager().getLaunchIntentForPackage("com.yaojun.mes"); 
		   
		   context.startActivity(intent1 );

	}

}
