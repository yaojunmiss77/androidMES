package com.yaojun.activity;

import org.json.JSONException;
import com.yaojun.DB.MyDatabaseHelper;
import com.yaojun.bean.User;
import com.yaojun.json.JsonThread;
import com.yaojun.mes.R;
import com.yaojun.service.UserService;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ShareActionProvider;
import tool.JsonUser;
import tool.ToastUtil;

public class LoginActivity extends Activity{
	private static final String url ="http://192.168.253.1:8080/MES/android/AndroidLoginAction.action";
	MyDatabaseHelper LoginDb;
	EditText number,password;
	Button loginButton;
	String content;
	
	SharedPreferences loginUser;
	SharedPreferences.Editor editor;
	
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			content = (String)msg.obj;
			
			User user=new User();
			try {
				user=JsonUser.getJsonUser(content);
				
				if(user.getName()!=null&&user.getNumber().equals(number.getText().toString()))
				{
					if(user.getPassword().equals(password.getText().toString()))
					{
						/*new UserService().insertUser(LoginDb.getReadableDatabase(),user);*/
						editor=loginUser.edit();
						editor.putString("userName",number.getText().toString());
						editor.putString("userPassword", password.getText().toString());
						
						editor.commit();
						ToastUtil.showMessage(LoginActivity.this, "保存成功");
						
						Intent intent=new Intent(LoginActivity.this,MainActivity.class);
						startActivity(intent);
						finish();
					}
					else
					{
						ToastUtil.showMessage(LoginActivity.this,"密码错误");
					}
							
				}
				else
				{
					ToastUtil.showMessage(LoginActivity.this, "当前用户名不存在");
				}

				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
/*			String[]  s=new JsonArrayTool().jsonArrayToString(content);
			ToastUtil.showMessage(LoginActivity.this,s[0]);*/
		}	
	};
	@SuppressLint("WorldReadableFiles")
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LoginDb=new MyDatabaseHelper(this, "MES.db3", 1);
		loginUser=getSharedPreferences("user",MODE_WORLD_READABLE);
		String userName=loginUser.getString("userName", null);
		String userPassword=loginUser.getString("userPassword", null);
		
		if(userName!=null&&userPassword!=null)
		{
			
			Intent intent=new Intent(LoginActivity.this,MainActivity.class);
			startActivity(intent);
			finish();
		}
		else
		{
			setContentView(R.layout.login);
	    	number=(EditText)findViewById(R.id.numberEdit);
	    	password=(EditText)findViewById(R.id.passwordEdit);
	    	loginButton=(Button)findViewById(R.id.LoginButton);
	    	
	    	loginButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					new JsonThread(url+"?user="+number+"&password="+password, handler,LoginActivity.this).start();
				}
			});
		}
		
		
	   /* if(!new UserService().checkUser(LoginDb.getReadableDatabase()))
	    {
	    	setContentView(R.layout.login);
	    	number=(EditText)findViewById(R.id.numberEdit);
	    	password=(EditText)findViewById(R.id.passwordEdit);
	    	loginButton=(Button)findViewById(R.id.LoginButton);
	    	
	    	loginButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					new JsonThread(url, handler,LoginActivity.this).start();
				}
			});
	    }
	    else
	    {
	    	Intent intent=new Intent(LoginActivity.this,MainActivity.class);
			startActivity(intent);
			finish();
	    }*/
		
	}	
	
}
