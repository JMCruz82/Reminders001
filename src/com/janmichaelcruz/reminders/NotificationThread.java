package com.janmichaelcruz.reminders;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

public class NotificationThread implements Runnable {

	private static final String TAG = "NotificationThread";
	private Context context;
	private NotificationInfo nInfo;

	public NotificationThread(Context c, NotificationInfo ni) {
		context = c;
		nInfo = ni;
	}

	@Override
	public void run() {
		NotificationManager nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		CharSequence from = nInfo.getTitle();

		Notification notif = new Notification.Builder(context)
				.setContentTitle(from).setSmallIcon(R.drawable.ic_launcher)
				.setWhen(nInfo.getDate().getTime()).build();

		notif.defaults |= Notification.DEFAULT_SOUND;
		notif.defaults |= Notification.DEFAULT_LIGHTS;
		notif.defaults |= Notification.DEFAULT_VIBRATE;

		nm.notify("com.janmichaelcruz.reminder", nInfo.getId(), notif);

		nInfo.setSet(false);
		
		Log.i(TAG, nInfo.getTitle() + " has run");
	}
}