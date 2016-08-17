package com.chniccs.study.demos.sign;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SinInDao {
	Context c;
	DBHelper dbhelper;
	SQLiteDatabase db;
	
	public SinInDao(Context c)
	{
		this.c = c;
	}
	public boolean open()
	{
		dbhelper = new DBHelper(c);
		db=dbhelper.getWritableDatabase();
		if(db==null)
		{
			return false;
		}
		return true;
	}
	public void close()
	{
		dbhelper.close();
	}
	public void insertSinInfo(String uid,String name,String date,String ym){
		String sql="insert into sinTB(userid,usernmae,sindate,yearmonth,nowdate) values(?,?,?,?,?)";
		db.execSQL(sql,new Object[]{uid,name,date,ym,System.currentTimeMillis()});
	}
	public ArrayList<HashMap<String, Object>> findSinInfo(String uid,String date,String ym){
		ArrayList<HashMap<String,Object>> alist = new ArrayList<HashMap<String,Object>>();
		alist.clear();
		HashMap<String, Object> rowMap;
		String sql;
		try{
			if("0".equals(ym))
			{
				sql="select * from sinTB where userid='"+uid+"' and sindate='"+date+"'";
			}
			else
			{
				sql="select * from sinTB where userid='"+uid+"' and yearmonth='"+ym+"'";
			}
			Cursor cur = db.rawQuery(sql, null);
			cur.moveToFirst();
			while(cur.moveToNext()){
				rowMap = new HashMap<String, Object>();
				rowMap.put("sin_id", cur.getInt(cur.getColumnIndex("sin_id")));
				rowMap.put("userid", cur.getString(cur.getColumnIndex("userid")));
				rowMap.put("usernmae", cur.getString(cur.getColumnIndex("usernmae")));
				rowMap.put("sindate", cur.getString(cur.getColumnIndex("sindate")));
				long aa = cur.getLong(cur.getColumnIndex("nowdate"));
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date now = new Date(aa);
				String date1 = format.format(now);
				rowMap.put("nowdate", date1);
				Log.e("", cur.getString(cur.getColumnIndex("sindate")));
				alist.add(rowMap);
			}
			return alist;
		}catch(Exception e){
			return alist;
		}
		
	}
	
}
