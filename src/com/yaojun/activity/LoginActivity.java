package com.yaojun.activity;

import org.json.JSONException;
import com.yaojun.DB.MyDatabaseHelper;
import com.yaojun.bean.User;
import com.yaojun.json.JsonThread;
import com.yaojun.mes.R;
import com.yaojun.service.UserService;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
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

			try {
				
				if(content!=null && content.length()>0 && !content.equals("false"))
				{
						User user=new User();
						user=JsonUser.getJsonUser(content);
						/*new UserService().insertUser(LoginDb.getReadableDatabase(),user);*/
						editor=loginUser.edit();
						editor.putString("name", user.getName());
						editor.putString("number",user.getNumber());
						editor.putString("password", user.getPassword());
						
						editor.commit();
						ToastUtil.showMessage(LoginActivity.this, "保存成功");
						
						Intent intent=new Intent(LoginActivity.this,MainActivity.class);
						startActivity(intent);
						finish();
							
				}
				else
				{
					new AlertDialog.Builder(LoginActivity.this)
					.setIcon(getResources().getDrawable(R.drawable.login_error_icon))
					.setTitle("登录失败")
					.setMessage("用户名或密码错误")
					.create().show();
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
		
		//故意写错，这样使得每次都登录
		String name=loginUser.getString("name", null);
		String snumber=loginUser.getString("number", null);
		String spassword=loginUser.getString("password", null);
		
		if(name!=null && snumber != null && spassword !=null)
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
					String stringNumber = number.getText().toString();
					String stringPassword = password.getText().toString();
					if(stringNumber!=null && stringNumber.length()>0)
					{
						if(stringPassword!=null && stringPassword.length()>0)
						{
							new JsonThread(url+"?user="+stringNumber+"&password="+stringPassword, handler,LoginActivity.this).start();
						}
						else
						{
							new AlertDialog.Builder(LoginActivity.this)
							.setIcon(getResources().getDrawable(R.drawable.login_error_icon))
							.setTitle("登录失败")
							.setMessage("密码不能为空")
							.create().show();
						}
						
					}
					else
					{
						new AlertDialog.Builder(LoginActivity.this)
						.setIcon(getResources().getDrawable(R.drawable.login_error_icon))
						.setTitle("登录失败")
						.setMessage("用户名不能为空")
						.create().show();
					}
					
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
