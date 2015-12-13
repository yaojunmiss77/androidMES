package com.yaojun.socket;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.widget.Toast;

public class ServerThread implements Runnable{
	Context context;
	private String urls;
	
	public ServerThread(String urls,Context context)
	{
		this.urls=urls;
		this.context=context;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		URL url = null;
		try {
			url = new URL(urls);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) url.openConnection();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			if(connection.getResponseCode()==200){
				Toast.makeText(this.context, "我已经开启了服务器", Toast.LENGTH_SHORT).show();;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
