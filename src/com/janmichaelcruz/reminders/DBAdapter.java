package com.janmichaelcruz.reminders;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
	static final String KEY_ROWID      = "_id";
	static final String KEY_TIMESTAMP  = "timestamp";
	static final String KEY_TIME       = "time";
	static final String KEY_NAME       = "name";
	static final String KEY_SUNDAY     = "sunday";
	static final String KEY_MONDAY     = "monday";
	static final String KEY_TUESDAY    = "tuesday";
	static final String KEY_WEDNESDAY  = "wednesday";
	static final String KEY_THURSDAY   = "thursday";
	static final String KEY_FRIDAY     = "friday";
	static final String KEY_SATURDAY   = "saturday";
	static final String KEY_ACTIVE     = "active";
	static final String KEY_WEEKLY     = "weekly";
	static final String TAG = "DBAdapter";
	
	static final String DATABASE_NAME = "MyDB";
	static final String DATABASE_TABLE = "reminders";
	static final int DATABASE_VERSION = 1;
	
	static final String DATABASE_CREATE =
			"create table reminders (_id integer primary key autoincrement, " +
	        "timestamp text not null," +
			"time      text not null " +
			"name      text not null " +
			"sunday    text not null " +
			"monday    text not null " +
			"tuesday   text not null " +
			"wednesday text not null " +
			"thursday  text not null " +
			"friday    text not null " +
			"saturday  text not null " +
			"active    text not null " +
			"weekly    text not null);";
	
	final Context context;
	
	DatabaseHelper DBHelper;
	SQLiteDatabase db;

	public DBAdapter(Context ctx) {
		this.context = ctx;
		
		DBHelper = new DatabaseHelper(context);
	}
	
	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		DatabaseHelper(Context context)
		{
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		public void onCreate(SQLiteDatabase db)
		{
			try {
				db.execSQL(DATABASE_CREATE);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
		{
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to " +
		               newVersion + ", which will destroy all old data");
			
			db.execSQL("DROP TABLE IF EXISTS reminders");
			
			onCreate(db);
		}
	}

	public DBAdapter open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		
		return this;
	}

	public Cursor getAllReminders() {
		return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, 
													  KEY_TIMESTAMP,
													  KEY_TIME,
													  KEY_NAME,
													  KEY_SUNDAY,
													  KEY_MONDAY,
													  KEY_TUESDAY,
													  KEY_WEDNESDAY,
													  KEY_THURSDAY,
													  KEY_FRIDAY,
													  KEY_SATURDAY,
													  KEY_ACTIVE,
													  KEY_WEEKLY},
										null, null, null, null, null);
	}
	
}
