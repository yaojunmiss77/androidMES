package com.yaojun.activity;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import com.yaojun.mes.R;
import com.yaojun.socket.ClientThread;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.Toast;
import tool.MyChartView;
import tool.ToastUtil;
import tool.Tools;
import tool.MyChartView.Mstyle;

@SuppressLint({ "HandlerLeak", "SimpleDateFormat" })
public class ModelTemperatureChartActivity extends Activity{
	
	Spinner selectRoad;
	Handler handler;
	ClientThread clientThread;
	MyChartView tu;
	LinkedHashMap<Double, Double> map;
	Double key=8.0;
	Double value=0.0;
	Tools tool=new Tools();
	private String deviceNumber;
	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat sdf = new SimpleDateFormat( "yyyy/MM/dd HH:mm:ss" ); 
	SimpleDateFormat sdf1 = new SimpleDateFormat( "HHmmss" ); 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.model_temperature_new);

		tu= (MyChartView)findViewById(R.id.modelTemChart);
		selectRoad=(Spinner)findViewById(R.id.select_road);
		
		
		
		handler=new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				
				//先转义成字符串
				StringTokenizer content = new StringTokenizer(msg.obj.toString(),",;");
				
				double dataY=Double.parseDouble(content.nextToken().toString());
				Double times = null;
				try {
					
					times=Double.parseDouble(sdf1.format(sdf.parse(content.nextToken().toString())));
					
					Log.d("yaojunLog", "当前时间为:"+String.valueOf(times));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(map.size()>=6)
					for(double key : map.keySet())
					{
						map.remove(key);
						
						break;
					}
					
				
				map.put(times, dataY);

				randmap(map, dataY);		
				super.handleMessage(msg);
			}
			
		};
		
		Intent intent=getIntent();
		deviceNumber=intent.getStringExtra("deviceNumber");
		ToastUtil.showMessage(ModelTemperatureChartActivity.this, deviceNumber);
		
		map=new LinkedHashMap<Double, Double>();
		
		tu= (MyChartView)findViewById(R.id.modelTemChart);
				tu.SetTuView(map,50,10,"","",false);
				
				map.clear();
			
				map.put((double)1, (double) 0);
		    	map.put((double)2,(double) 0);
		    	map.put((double)3, (double) 0);
		    	map.put((double)4, (double) 0);
		    	map.put((double)5, (double) 0);
		    	map.put((double)6, (double) 0);
		    	/*map.put((double)7, (double) 0);*/
		    	tu.setTotalvalue(50);
		    	tu.setPjvalue(10);
		    	tu.setMap(map);
				tu.setMargint(20);
				tu.setMarginb(50);
				tu.setMstyle(Mstyle.Line);
		
		clientThread=new ClientThread(handler);
		new Thread(clientThread).start();
		
		try {
			Thread.sleep(1000);
			Message msg=new Message();
			msg.what=0x345;
			msg.obj="模温:0".toString();
			clientThread.revHandler.sendMessage(msg);
			
		} catch (Exception e) {
			// TODO: handle exception
			
			e.printStackTrace();
		}
		
		selectRoad.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				
				try {
					clientThread.closeSocket();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				clientThread=new ClientThread(handler);
				new Thread(clientThread).start();
				
				try {
					Thread.sleep(1000);
					Message msg=new Message();
					msg.what=0x345;
					msg.obj="模温:".toString()+String.valueOf(position);
					clientThread.revHandler.sendMessage(msg);
					
				} catch (Exception e) {
					// TODO: handle exception
					
					e.printStackTrace();
				}
				
				
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		try {
			clientThread.closeSocket();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		try {
			clientThread.closeSocket();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		try {
			clientThread.closeSocket();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	private void randmap(HashMap< Double,Double> mp,Double d)
	{
		ArrayList<Double> dz=tool.getintfrommap(mp);
		Double[] dvz=new Double[mp.size()];
		int t=0;
		@SuppressWarnings("rawtypes")
		Set set= mp.entrySet();   
        @SuppressWarnings("rawtypes")
		Iterator iterator = set.iterator();
		 while(iterator.hasNext())
		{  
			@SuppressWarnings("rawtypes")
			Map.Entry mapentry  = (Map.Entry)iterator.next();   
			dvz[t]=(Double)mapentry.getValue();
			t+=1;
		} 
		/* for(int j=0;j<dz.size()-1;j++)
		 {
			 mp.put(dz.get(j), mp.get(dz.get(j+1)));
		 }*/
		 mp.put((Double)dz.get(mp.size()-1), d);
		 tu.postInvalidate();
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.chart, menu);
		return true;
	}	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
	        // TODO Auto-generated method stub
	        if(item.getItemId() == R.id.menu_settings)
	        {
	        	if (false == tu.isDrawingCacheEnabled()) 
	    		{  
	                tu.setDrawingCacheEnabled(true);
	            }  
	        	Bitmap bitmap = tu.getDrawingCache();  
	        	Tools tool=new Tools();
	        	try {
					Boolean b=tool.saveFile(bitmap, " ");
					if(b)
					Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();	
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        if(item.getItemId() == R.id.menu_ch)
	        {
	        	tu.setMstyle(Mstyle.Curve);
	        	tu.setIsylineshow(true);
	        	tu.postInvalidate();
	        }
	        if(item.getItemId() == R.id.menu_ch2)
	        {

	        	tu.setMstyle(Mstyle.Line);
	        	tu.setIsylineshow(false);
	        	tu.postInvalidate();
	        }
	        return true;
	  }

}
