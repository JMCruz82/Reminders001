package com.janmichaelcruz.reminders;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 2;
	private static final String DATABASE_NAME = "remindersManager";
	private static final String KEY_ID = "id";
	private static final String KEY_TITLE = "title";
	private static final String KEY_TIME = "time";
	private static final String TABLE_REMINDERS = "reminders";
	private static final String TAG = "DatabaseHandler";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		Log.d(TAG, "DatabaseHandler instantiated");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "onCreate Start");
		
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_REMINDERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT,"
                + KEY_TIME + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
        
        Log.d(TAG, "onCreate Done");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(TAG, "onUpgrade Start");
		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDERS);
		 
        onCreate(db);
        
        Log.d(TAG, "onUpgrade Dene");
	}

	public void addReminder(NotificationInfo notificationInfo) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TITLE, notificationInfo.getTitle().toString());
		values.put(KEY_TIME, notificationInfo.getDateString());

		db.insert(TABLE_REMINDERS, null, values);
		db.close(); // Closing database connection
	}

	public List<NotificationInfo> getAllReminders() {
		List<NotificationInfo> reminderList = new ArrayList<NotificationInfo>();
		String selectQuery = "SELECT  * FROM " + TABLE_REMINDERS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				NotificationInfo notifInfo = new NotificationInfo();
				notifInfo.setId(Integer.parseInt(cursor.getString(0)));
				notifInfo.setTitle(cursor.getString(1));
				notifInfo.setDateString(cursor.getString(2));
				reminderList.add(notifInfo);
			} while (cursor.moveToNext());
		}

		// return contact list
		return reminderList;
	}
	 
    // Deleting single contact
    public void deleteReminder(NotificationInfo notificationInfo) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        db.delete(TABLE_REMINDERS, KEY_ID + " = ?",
                new String[] { String.valueOf(notificationInfo.getId()) });
        db.close();
    }
}
