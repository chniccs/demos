package com.chniccs.study.demos.sign;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	public DBHelper(Context context) {
		super(context, "sin.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql="create table sinTB(" +
				"sin_id integer primary key autoincrement," +
				"userid varchar(20)," +
				"usernmae varchar(20)," +
				"sindate varchar(20)," +
				"yearmonth varchar(20)," +
				"nowdate integer" +
				")";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
}
