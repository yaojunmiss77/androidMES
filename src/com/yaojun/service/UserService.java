package com.yaojun.service;

import com.yaojun.bean.User;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class UserService {
	
		public boolean checkUser(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			boolean b=false;
			try {	
				
				Cursor cursor=db.rawQuery("select count(*) from User",null);
				
				
				while(cursor.moveToNext())
				{
					Log.d("数据库中值的数量为:", String.valueOf(cursor.getInt(0)));
					if(cursor.getInt(0)>=1)
						b=true;
				}
				
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}finally{
				if(db.isOpen())
				{
					db.close();
				}
			}
			
			return b;
		}

		public void insertUser(SQLiteDatabase db, User user) {
			// TODO Auto-generated method stub
			db.execSQL("insert into User(name,nubmer,password) values(?,?,?)",new String[] {user.getName(),user.getNumber(),user.getPassword()});	
		}
}
