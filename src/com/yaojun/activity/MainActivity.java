package com.yaojun.activity;

import java.util.*;

import org.json.JSONException;

import com.yaojun.bean.Device;
import com.yaojun.json.JsonThread;
import com.yaojun.mes.R;
import com.yaojun.server.AlarmService;
import com.yaojun.server.OnbindService;
import com.yaojun.server.OnbindService.MyBind;
import com.yaojun.socket.ServerThread;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import tool.JsonDevice;
import tool.RoundCornerImageView;
import tool.ToastUtil;


public class MainActivity extends Activity{
	private static final String url ="http://192.168.253.1:8080/MES/android/GetDeviceContentAction.action";
	Handler handler;
	ListView deviceList;
	StringBuffer deviceName;
	StringBuffer deviceType;
	StringBuffer deviceNumber;
	StringBuffer deviceDate;
	String content;
	
	private OnbindService.MyBind binder;
	
	List<Map<String,Object>> deviceListContent;
	
	private long exitTime = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		deviceList=(ListView)findViewById(R.id.deviceList);
		
		 handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				content = (String)msg.obj;
					
				try {
					List<Device> devices=JsonDevice.getJsonDevice(content);
					deviceList.setAdapter(new DeviceAdapter(devices));
					deviceListContent=new ArrayList<Map<String,Object>>();
					for(int i=0;i<devices.size();i++)
					{
						Device device =new Device();
						device=devices.get(i);
						Map<String,Object> deviceMap=new HashMap<String, Object>();
						deviceMap.put("header",R.drawable.android);
						deviceMap.put("deviceName", device.getName());
						deviceMap.put("deviceType","型号："+device.getType());
						deviceMap.put("deviceNumber","编号："+device.getNumber());
						deviceMap.put("deviceDate", "出厂日期："+device.getDate());
						deviceListContent.add(deviceMap);
					}
										
					/*SimpleAdapter deviceSimpleAdapter=new SimpleAdapter(MainActivity.this,deviceListContent,R.layout.device_base_content,
							new String[]{"header","deviceName","deviceType","deviceNumber","deviceDate"},
							new int[]{R.id.deviceImage,R.id.deviceName,R.id.deviceType,R.id.deviceNumber,R.id.deviceDate});
					deviceList.setAdapter(deviceSimpleAdapter);*/
				
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
		};
		
		
		new JsonThread(url, handler,MainActivity.this).start();
		
		deviceList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				
					//现在我们实现开启服务器的环节
					new ServerThread("http://192.168.253.1:8080/MES/android/MyServer.action",MainActivity.this);
				
				    //开启activity
					Intent intent=new Intent(MainActivity.this,MonitorActivity.class);
					Bundle deviceNumber=new Bundle();
					deviceNumber.putCharSequence("deviceNumber", deviceListContent.get(position).get("deviceNumber").toString());
					intent.putExtras(deviceNumber);
					startActivity(intent);
					
					
					
					//开启service
					/*Intent service=new Intent(MainActivity.this,AlarmService.class);
					startService(service);*/
					
					
					
					
					ToastUtil.showMessage(MainActivity.this,"开启了服务器");
					
					/*Intent bindservice=new Intent(MainActivity.this,OnbindService.class);
					
					bindService(bindservice, conn, Service.BIND_AUTO_CREATE);
					
					binder.getOnbindService().hello(MainActivity.this);*/
					
				
				
			}
		});

	}
	
	public class DeviceAdapter extends BaseAdapter
	{
		
		private List<Device> devices=new ArrayList<Device>();
		
		private LayoutInflater inflater;
		
		public DeviceAdapter(List<Device> devices) {
			// TODO Auto-generated constructor stub
			
			inflater=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
			this.devices=devices;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return devices.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return devices.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder viewHolder=null;
			if(convertView==null)
			{
				viewHolder=new ViewHolder();
				convertView=inflater.inflate(R.layout.device_base_content,null);
				viewHolder.name=(TextView)convertView.findViewById(R.id.deviceName);
				viewHolder.number=(TextView)convertView.findViewById(R.id.deviceNumber);
				viewHolder.type=(TextView)convertView.findViewById(R.id.deviceType);
				viewHolder.date=(TextView)convertView.findViewById(R.id.deviceDate);
				/*viewHolder.image=(RoundCornerImageView)convertView.findViewById(R.id.deviceImage);*/
			
				convertView.setTag(viewHolder);
			}
			else
			{
				viewHolder=(ViewHolder)convertView.getTag();
			}
			viewHolder.name.setText(String.valueOf(devices.get(position).getName()));
			viewHolder.number.setText(String.valueOf(devices.get(position).getNumber()));
			viewHolder.type.setText(String.valueOf(devices.get(position).getType()));
			viewHolder.date.setText(String.valueOf(devices.get(position).getDate()));
			/*viewHolder.image.setImageBitmap(((BitmapFactory.decodeResource(getResources(), R.drawable.device))));*/
			return convertView;
		}
		
	}
	
	
	public static class ViewHolder
	{
		
		public TextView name;
		public TextView type;
		public TextView number;
		public TextView date;
		public RoundCornerImageView image;
		
	}
	
	
	private ServiceConnection conn=new ServiceConnection(){

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			Log.d("yaojunLog","现在我已经开始连接了");
			
			binder=(OnbindService.MyBind)service;
			
			
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			
			Log.d("yaojunLog", "现在我已经断开连接了");
			
		}
		
	};

	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void exit() {
		if ((System.currentTimeMillis() - exitTime) > 2000) {
			Toast.makeText(getApplicationContext(), "再按一次退出程序",
					Toast.LENGTH_SHORT).show();
			exitTime = System.currentTimeMillis();
		} else {
			finish();
			System.exit(0);
		}

	}

}
