package com.yaojun.server;

import java.io.IOException;
import java.util.StringTokenizer;

import com.yaojun.activity.AlarmActivity;
import com.yaojun.mes.R;
import com.yaojun.socket.ClientThread;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class AlarmService extends IntentService{
	
	ClientThread clientThread;
	NotificationManager nm;
	

	public AlarmService() {
		super("IntentService");
		// TODO Auto-generated constructor stub
	}
	
	Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			//ToastUtil.showMessage(getApplicationContext(),msg.obj.toString());
			Log.d("yaojunLog", msg.obj.toString());
			//先转义成字符串
			  StringTokenizer content = new StringTokenizer(msg.obj.toString(),",;");
			  if(Integer.parseInt(content.nextToken().toString())>=9)
			  {
				  AssetManager assetManager=getAssets();
				  AssetFileDescriptor fileDescriptor;
				try {
					fileDescriptor = assetManager.openFd("alarm.mp3");
					MediaPlayer mediaPlayer = new MediaPlayer();
				      mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),
				                                fileDescriptor.getStartOffset(),
				                                fileDescriptor.getLength());
		           mediaPlayer.prepare();
		           mediaPlayer.start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				nm=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
				//创建一个启动其他Activity的Intent
				Intent intent=new Intent(AlarmService.this,AlarmActivity.class);
				Bundle b=new Bundle();
				b.putString("content","我感觉该设备有危险了");
				intent.putExtras(b);
				PendingIntent pi=PendingIntent.getActivity(AlarmService.this,
						0, intent, 0);
				Notification notify=new Notification.Builder(AlarmService.this).
						setAutoCancel(true).
						setTicker("有新报警任务").
						setSmallIcon(R.drawable.five).
						setContentTitle("一条新报警通知").
						setContentText("该设备的温度值已经超标了").
						setDefaults(Notification.DEFAULT_SOUND).
						setDefaults(Notification.DEFAULT_LIGHTS |Notification.DEFAULT_VIBRATE).
						setContentIntent(pi).build();
				
				//现在开始发送通知
				nm.notify(0X123,notify);
			  }
			    super.handleMessage(msg);
		}

	};

	@SuppressWarnings("unused")
	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		
		Log.d("yaojunLog", "我进来了");
		
	/*	Looper.prepare();*/
		

		
		clientThread=new ClientThread(handler);
		new Thread(clientThread).start();
		
		try {
			Thread.sleep(1000);
			Message msg=new Message();
			msg.what=0x345;
			msg.obj="监控";
			clientThread.revHandler.sendMessage(msg);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
/*		Looper.loop();*/
		
	}

}
