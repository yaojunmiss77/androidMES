package com.yaojun.json;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.yaojun.bean.User;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import tool.ToastUtil;

public class JsonThread extends Thread{
	private String urls;
	Handler handler;
	Context context;
	public JsonThread(String urls,Handler handler,Context context){
		this.urls = urls;
		this.handler=handler;
		this.context=context;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		InputStream inputStream = null;
		ByteArrayOutputStream outputStream = null;
		try {
			URL url = new URL(urls);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			if(connection.getResponseCode()==200){
				inputStream = connection.getInputStream();
			}
				
			outputStream = new ByteArrayOutputStream();
			int len = 0;
			byte[] bytes = new byte[1024];
		    while((len=inputStream.read(bytes))!=-1){
		    	outputStream.write(bytes, 0, len);
		    }
		    byte[] bs = outputStream.toByteArray();
		    String msg = new String(bs);
			
			/*ObjectInputStream ois=new ObjectInputStream(inputStream);
			User user = null;
			try {
				user = (User)ois.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String msg=user.getName();*/
		    Message message = Message.obtain();
		    message.obj = msg;
		    handler.sendMessage(message);
		    
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			ToastUtil.showMessage(this.context, "网络出现故障，请检查你的网络");
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Looper.prepare();
			ToastUtil.showMessage(this.context, "网络出现故障，请检查你的网络");
			Looper.loop();
			e.printStackTrace();
			//ToastUtil.showMessage(this.context, "网络出现故障，请检查你的网络");
		}
	}
}
