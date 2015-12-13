package com.yaojun.DB;

import com.yaojun.mes.R;

import android.app.Activity;
import android.os.Bundle;

public class CreateDatabase extends Activity{
	
	MyDatabaseHelper db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.createdatabaselayout);
		db=new MyDatabaseHelper(this,"MES.db3", 1);
		db.getReadableDatabase();		
	}

}
