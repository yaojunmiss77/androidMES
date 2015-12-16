package com.yaojun.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Timer;

import com.yaojun.mes.R;
import com.yaojun.socket.ClientThread;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import tool.MyChartView;
import tool.Tools;
import tool.MyChartView.Mstyle;
import tool.ToastUtil;

public class FillChartActivity extends Activity{
	
	TextView coolingTimesReal,fillPressReal,lowTemTimesReal,textView1;
	TextView coolingTimesOption,fillPressOption,lowTemTimesOption;
	
	String fillData,lowTemData,coolingData;
	MyChartView tu;
	private String deviceNumber;

	LinkedHashMap<Double, Double> map;

	Double key=8.0;
	Double value=0.0;
	Tools tool=new Tools();
	int flag=0;
	ClientThread clientThread;
	double d=8;
	double data=0;
	double data1=0;
	
	
	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			
			//先转义成字符串
			  StringTokenizer content = new StringTokenizer(msg.obj.toString(),",;");

			while(content.hasMoreTokens()){
				
				//首先移除一个图表左边的数据
				
				double dataY=Double.parseDouble(content.nextToken().toString());
				
				
				if(map.size()>=7)
					for(double key : map.keySet())
					{
						map.remove(key);
						
						break;
					}
					
					
						
				
				
				map.put(d, data);
				Log.d("yaojunLog","d的值为:"+String.valueOf(d));
				Log.d("yaojunLog","相对应的值为"+String.valueOf(data));
				
				for(Double key : map.keySet())
				{
					Log.d("yaojunLog", "建为:"+String.valueOf(key)+"值为:"+map.get(key));
				}
				
				
				randmap(map, data);
				
				d++;			
				
				data+=10;
				if(data>50)
					data=0;
				
				coolingTimesReal.setText(content.nextToken().toString()+"S");
				fillPressReal.setText(content.nextToken().toString()+"S");
				lowTemTimesReal.setText(content.nextToken().toString()+"S");
				
				coolingTimesOption.setText(content.nextToken().toString()+"S");
				fillPressOption.setText(content.nextToken().toString()+"S");
				lowTemTimesOption.setText(content.nextToken().toString()+"S");
				
		  }
			
			super.handleMessage(msg);
			
		}
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fill_pressure_chart_new);
		
		coolingTimesReal=(TextView)findViewById(R.id.CoolingTimesReal);
		fillPressReal=(TextView)findViewById(R.id.fillTimesReal);
		lowTemTimesReal=(TextView)findViewById(R.id.lowPressTimesReal);
		
		coolingTimesOption=(TextView)findViewById(R.id.CoolingTimesOption);
		fillPressOption=(TextView)findViewById(R.id.fillTimesOption);
		lowTemTimesOption=(TextView)findViewById(R.id.lowPressTimesOption);

		
		Intent intent=getIntent();
		deviceNumber=intent.getStringExtra("deviceNumber");
		ToastUtil.showMessage(FillChartActivity.this, deviceNumber);

		map=new LinkedHashMap<Double, Double>();
		
		  tu= (MyChartView)findViewById(R.id.menulist);
				tu.SetTuView(map,50,10,"x","y",false);
				
				map.clear();
			
				map.put((double)1, (double) 0);
		    	map.put((double)2,(double) 0);
		    	map.put((double)3, (double) 0);
		    	map.put((double)4, (double) 0);
		    	map.put((double)5, (double) 0);
		    	map.put((double)6, (double) 0);
		    	map.put((double)7, (double) 0);
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
			msg.obj="充型".toString();
			clientThread.revHandler.sendMessage(msg);
			
		} catch (Exception e) {
			// TODO: handle exception
			
			e.printStackTrace();
		}
		
	
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
