package com.yaojun.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.yaojun.mes.R;
import com.yaojun.socket.ClientThread;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import tool.ToastUtil;



public class DeviceStatusActivity extends Activity{
	
	TextView oneOne,oneTwo,oneThree,oneFour,oneFive,
	twoOne,twoTwo,twoThree,twoFour,twoFive,
	threeOne,threeTwo,threeThree,threeFour,threeFive,
	up,center,down;
	TextView aluQuality,aluQuantity,alarmAluQuantity,overageUserQuantity,
	pressDeviation,compensatePress,aluRealtime,aluVoltage;
	ImageView upModelImage;
	ProgressBar upModelProcess;
	ListView upModelListView,aluStoveListView;

	ClientThread clientThread;
	
	private String deviceNumber;
	
	
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			
			/*List<Map<String,Object>> upModelData=new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> aluData=new ArrayList<Map<String,Object>>();*/
			
			//先转义成字符串
			  StringTokenizer content = new StringTokenizer(msg.obj.toString(),",;");
			
		while(content.hasMoreTokens()){
				
				oneOne.setText(content.nextToken().toString());
				oneTwo.setText(content.nextToken().toString());
				oneThree.setText(content.nextToken().toString());
				oneFour.setText(content.nextToken().toString());
				oneFive.setText(content.nextToken().toString());
				
				
				twoOne.setText(content.nextToken().toString());
				twoTwo.setText(content.nextToken().toString());
				twoThree.setText(content.nextToken().toString());
				twoFour.setText(content.nextToken().toString());
				twoFive.setText(content.nextToken().toString());
				
				
				threeOne.setText(content.nextToken().toString());
				threeTwo.setText(content.nextToken().toString());
				threeThree.setText(content.nextToken().toString());
				threeFour.setText(content.nextToken().toString());
				threeFive.setText(content.nextToken().toString());
				
				/*Map<String,Object> upMap=new HashMap<String, Object>();
				upMap.put("upModelImage",R.drawable.two);
				upMap.put("upModelProcess", Integer.parseInt(content.nextToken().toString()));
				upMap.put("up", "上位值："+content.nextToken().toString());
				upMap.put("center","顶出位值："+content.nextToken().toString());
				upMap.put("down","下位值："+content.nextToken().toString());
				upModelData.add(upMap);
				
				SimpleAdapter upModelSimpleAdapter=new SimpleAdapter(DeviceStatusActivity.this,upModelData,R.layout.upmodel_status,
						new String[]{"upModelImage","upModelProcess","up","center","down"},
						new int[]{R.id.upModelImage,R.id.upModelProcess,R.id.up,R.id.center,R.id.down});
				
				upModelListView.setAdapter(upModelSimpleAdapter);*/
				
				
				
				
/*				Map<String,Object> aluMap=new HashMap<String, Object>();
				aluMap.put("aluStoveImage",R.drawable.three);
				aluMap.put("aluQuality", "产品重量："+content.nextToken().toString());
				aluMap.put("aluQuantity", "总可用铝量："+content.nextToken().toString());
				aluMap.put("alarmAluQuantity", "报警提示用铝量"+content.nextToken().toString());
				aluMap.put("overageUserQuantity", "当前剩余铝液可铸件数："+content.nextToken().toString());
				aluMap.put("pressDeviation","压力偏差报警："+content.nextToken().toString());
				aluMap.put("compensatePress","补偿压力值："+content.nextToken().toString());
				aluMap.put("aluRealtime","铝液炉实时压力值："+content.nextToken().toString());
				aluMap.put("aluVoltage","铝液炉电压值："+content.nextToken().toString());
				aluData.add(aluMap);*/
				
				aluQuality.setText(content.nextToken().toString()+"kg");
				aluQuantity.setText(content.nextToken().toString()+"kg");
				alarmAluQuantity.setText(content.nextToken().toString()+"kg");
				overageUserQuantity.setText(content.nextToken().toString()+"件");
				pressDeviation.setText(content.nextToken().toString()+"pa");
				compensatePress.setText(content.nextToken().toString()+"pa");
				aluRealtime.setText(content.nextToken().toString()+"pa");
				aluVoltage.setText(content.nextToken().toString()+"V");
				
/*				SimpleAdapter aluStoveSimpleAdapter=new SimpleAdapter(DeviceStatusActivity.this,aluData,R.layout.alustove_status,
						new String[]{"aluStoveImage","aluQuality",
								"aluQuantity","alarmAluQuantity","overageUserQuantity",
								"pressDeviation","compensatePress",
								"aluRealtime","aluVoltage"},
						new int[]{R.id.aluStoveImage,R.id.aluQuality,
								R.id.aluQuantity,R.id.alarmAluQuantity,R.id.overageUserQuantity,
								R.id.pressDeviation,R.id.compensatePress,
								R.id.aluRealtime,R.id.aluVoltage});
			
				aluStoveListView.setAdapter(aluStoveSimpleAdapter);*/
				
				up.setText("上位值："+content.nextToken().toString());
				upModelProcess.setProgress(Integer.parseInt(content.nextToken().toString()));
				center.setText("顶出位值："+content.nextToken().toString());
				down.setText("下位值："+content.nextToken().toString());
				
				
				
		 }
			
			super.handleMessage(msg);
			
		}
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.device_new_status);
		
		oneOne=(TextView)findViewById(R.id.oneOne);
		oneTwo=(TextView)findViewById(R.id.oneTwo);
		oneThree=(TextView)findViewById(R.id.oneThree);
		oneFour=(TextView)findViewById(R.id.oneFour);
		oneFive=(TextView)findViewById(R.id.oneFive);
		
		twoOne=(TextView)findViewById(R.id.twoOne);
		twoTwo=(TextView)findViewById(R.id.twoTwo);
		twoThree=(TextView)findViewById(R.id.twoThree);
		twoFour=(TextView)findViewById(R.id.twoFour);
		twoFive=(TextView)findViewById(R.id.twoFive);
		
		threeOne=(TextView)findViewById(R.id.threeOne);
		threeTwo=(TextView)findViewById(R.id.threeTwo);
		threeThree=(TextView)findViewById(R.id.threeThree);
		threeFour=(TextView)findViewById(R.id.threeFour);
		threeFive=(TextView)findViewById(R.id.threeFive);
		
		/*upModelListView=(ListView)findViewById(R.id.upModelStatus);*/
		/*aluStoveListView=(ListView)findViewById(R.id.aluStoveStatus);*/
		upModelImage=(ImageView)findViewById(R.id.upModelImage);
		upModelProcess=(ProgressBar)findViewById(R.id.upModelProcess);
		up=(TextView)findViewById(R.id.up);
		center=(TextView)findViewById(R.id.center);
		down=(TextView)findViewById(R.id.down);
		
		aluQuality=(TextView)findViewById(R.id.aluQuality);
		aluQuantity=(TextView)findViewById(R.id.aluQuantity);
		alarmAluQuantity=(TextView)findViewById(R.id.alarmAluQuantity);
		overageUserQuantity=(TextView)findViewById(R.id.overageUserQuantity);
		pressDeviation=(TextView)findViewById(R.id.pressDeviation);
		compensatePress=(TextView)findViewById(R.id.compensatePress);
		aluRealtime=(TextView)findViewById(R.id.aluRealtime);
		aluVoltage=(TextView)findViewById(R.id.aluVoltage);
		
		
		Intent intent=getIntent();
		deviceNumber=intent.getStringExtra("deviceNumber");
		ToastUtil.showMessage(DeviceStatusActivity.this, deviceNumber);
	
		clientThread=new ClientThread(handler);
		
		new Thread(clientThread).start();
		
		
		try {
			Thread.sleep(1000);
			Message msg=new Message();
			msg.what=0x345;
			msg.obj="设备状态".toString();
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

}
