package com.yaojun.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

@SuppressLint("HandlerLeak")
public class ModelTemperatureChartActivity extends Activity{
	
	Spinner selectRoad;
	Handler handler;
	ClientThread clientThread;
	MyChartView tu;
	HashMap<Double, Double> map;
	Double key=8.0;
	Double value=0.0;
	Tools tool=new Tools();
	private String deviceNumber;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.model_temperature_new);

		tu= (MyChartView)findViewById(R.id.modelTemChart);
		selectRoad=(Spinner)findViewById(R.id.select_road);
		
		tu.SetTuView(map,50,10,"x","y",false);
		map=new HashMap<Double, Double>();
		map.put(1.0, (double) 0);
    	map.put(3.0, 25.0);
    	map.put(4.0, 32.0);
    	map.put(5.0, 41.0);
    	map.put(6.0, 16.0);
    	map.put(7.0, 36.0);
    	map.put(8.0, 26.0);
    	tu.setTotalvalue(50);
    	tu.setPjvalue(10);
    	tu.setMap(map);
		tu.setMargint(20);
		tu.setMarginb(50);
		tu.setMstyle(Mstyle.Line);
		
		handler=new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				
				//先转义成字符串
				StringTokenizer content = new StringTokenizer(msg.obj.toString(),",;");
				randmap(map, Double.parseDouble(content.nextToken().toString()));
				super.handleMessage(msg);
			}
			
		};
		
		Intent intent=getIntent();
		deviceNumber=intent.getStringExtra("deviceNumber");
		ToastUtil.showMessage(ModelTemperatureChartActivity.this, deviceNumber);
		
		clientThread=new ClientThread(handler);
		new Thread(clientThread).start();
		
		try {
			Thread.sleep(1000);
			Message msg=new Message();
			msg.what=0x345;
			msg.obj="模温".toString();
			clientThread.revHandler.sendMessage(msg);
			
		} catch (Exception e) {
			// TODO: handle exception
			
			e.printStackTrace();
		}
		
		selectRoad.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				
				System.out.println("你已经选择了我"+position);
				
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
		 for(int j=0;j<dz.size()-1;j++)
		 {
			 mp.put(dz.get(j), mp.get(dz.get(j+1)));
		 }
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
