/**
 *
 */
package com.yaojun.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper
{
	public MyDatabaseHelper(Context context, String name, int version)
	{
		super(context, name, null, version);
	}
	@Override
	public void onCreate(SQLiteDatabase db)
	{	
		createTables(db);	
	}
	@Override
	public void onUpgrade(SQLiteDatabase db
		, int oldVersion, int newVersion)
	{
		System.out.println("--------onUpdate Called--------"
			+ oldVersion + "--->" + newVersion);
	}
	
	public void createTables(SQLiteDatabase db)
	{
		db.execSQL(Tables.CREATE_TABLE_DEVICE);	
		db.execSQL(Tables.CREATE_TABLE_USER);
	}
}
