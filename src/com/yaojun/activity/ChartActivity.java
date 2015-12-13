package com.yaojun.activity;


import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

import com.yaojun.mes.R;
import com.yaojun.socket.ClientThread;

import tool.JsonArrayTool;
import tool.MyChartView;
import tool.ToastUtil;
import tool.MyChartView.Mstyle;
import tool.Tools;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.app.Activity;
import android.graphics.Bitmap;



public class ChartActivity extends Activity {

	List<Integer> infos=new ArrayList<Integer>();
	MyChartView tu;
	//Button BT_Add;
	Timer mTimer =new Timer();
	HashMap<Double, Double> map;
	Double key=8.0;
	Double value=0.0;
	Tools tool=new Tools();
	int flag=0;
	ClientThread clientThread;
	
	
	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			randmap(map, Double.parseDouble(msg.obj.toString()));
		}
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chart);
		//BT_Add=(Button)findViewById(R.id.bt_add);
		tu= (MyChartView)findViewById(R.id.menulist);
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
//		tu.setXstr("");
//		tu.setYstr("");
		tu.setMargint(20);
		tu.setMarginb(50);
		tu.setMstyle(Mstyle.Line);
		
		clientThread=new ClientThread(handler);
		
		new Thread(clientThread).start();
		
		
		
		try {
			Thread.sleep(1000);
			Message msg=new Message();
			msg.what=0x345;
			msg.obj="给我数据吧".toString();
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
		clientThread.closeSocket();
		
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		clientThread.closeSocket();
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		clientThread.closeSocket();
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
