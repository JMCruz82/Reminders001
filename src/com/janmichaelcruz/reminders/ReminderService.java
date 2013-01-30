package com.janmichaelcruz.reminders;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class ReminderService extends Service {
	private static final String TAG = "MyService";

	private ScheduledExecutorService threadPool;

	NotificationInfo[] nInfos = {
			new NotificationInfo(1, "08:15", "Go to Work!"),
			new NotificationInfo(2, "08:45", "Winning Mindset Check!"),
			new NotificationInfo(3, "09:00", "Coffee Time!"),
			new NotificationInfo(4, "11:55", "Lunch Time!"),
			new NotificationInfo(5, "12:50", "Go to Work!"),
			new NotificationInfo(6, "14:00", "Coffee Time!"),
			new NotificationInfo(7, "14:45", "Charge Your Battery!"),
			new NotificationInfo(8, "18:45", "Dinner Time!"),
			new NotificationInfo(9, "22:15", "Time to Sleep!") };

	private boolean midnightJobSet = false;
	
	List<NotificationInfo> reminders;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	public void onCreate() {
		super.onCreate();

        DatabaseHandler db = new DatabaseHandler(this);
        
        //db.addReminder(new NotificationInfo(1, "08:15", "Go to Work!"));
        //db.addReminder(new NotificationInfo(1, "09:00", "Coffee Time!"));
        //db.addReminder(new NotificationInfo(1, "11:55", "Lunch Time!"));
        
        reminders = db.getAllReminders();
        
        threadPool = Executors.newScheduledThreadPool(11);

		Toast.makeText(this, "Service Created", Toast.LENGTH_LONG).show();

		Log.i(TAG, "MyService was created");
	}

	public void setTheTimers() {
		Date date;
		long delay;
		long futureTime;
		long now;
		NotificationThread notification;

		for (NotificationInfo nInfo : reminders) {
			date = NotificationInfo.stringToDate(nInfo.getDateString());

			futureTime = date.getTime();
			now = new Date().getTime();

			Log.i(TAG, "futureTime: " + futureTime);
			Log.i(TAG, "now       : " + now);

			delay = (futureTime - now) / 1000;

			Log.i(TAG, "title: " + nInfo.getTitle() + " set: " + nInfo.isSet()
					+ " delay: " + delay);

			@SuppressWarnings("unused")
			int day = new GregorianCalendar().get(Calendar.DAY_OF_WEEK);

			if (nInfo.isSet() == false && delay > 0) {
				notification = new NotificationThread(this, nInfo);

				threadPool.schedule(notification, delay, TimeUnit.SECONDS);

				nInfo.setSet(true);

				Log.i(TAG, nInfo.getTitle() + " is scheduled to run in "
						+ delay + " seconds");
			}
		}

		Log.i(TAG, "Timers were set");
	}

	public void onDestroy() {
		super.onDestroy();

		Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();

		Log.i(TAG, "MyService was destroyed");
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		Date date;
		long delay;

		super.onStartCommand(intent, flags, startId);

		setTheTimers();

		if (midnightJobSet == false) {
			date = NotificationInfo.stringToDate("00:00");
			delay = ((date.getTime() - new Date().getTime()) / 1000)
					+ (24 * 60 * 60) + 1;

			threadPool.schedule(new Runnable() {
				public void run() {
					setTheTimers();
				}
			}, delay, TimeUnit.SECONDS);

			midnightJobSet = true;

			Log.i(TAG, "Timer for midnight set");
		}

		Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();

		Log.i(TAG, "MyService was started");

		return START_STICKY;
	}
}
