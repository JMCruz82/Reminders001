package com.janmichaelcruz.reminders;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {
	private static final String TAG = "MainActivity";
	ScheduledExecutorService threadPool;
	
	private List<String> labels;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent serviceIntent = new Intent();

		serviceIntent.setAction("com.janmichaelcruz.reminders.ReminderService");

		startService(serviceIntent);

        DatabaseHandler db = new DatabaseHandler(this);

    	List<NotificationInfo> reminders  = db.getAllReminders();
		
		labels = new ArrayList<String>();

		for (NotificationInfo nInfo : reminders) {
			labels.add(nInfo.getId() + " - " + nInfo.getDateString() + " - " + nInfo.getTitle());
		}

		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, labels));

		Log.i(TAG, "MainActivity was created");
	}
	
	public void onListItemClick(ListView parent, View v, int position, long id)
	{
		Toast.makeText(this, "You have selected " + labels.get(position), Toast.LENGTH_SHORT).show();
		
		Intent i = new Intent(getApplicationContext(), EditActivity.class);
		
		startActivity(i);
	}

	public ScheduledExecutorService getThreadPool() {
		return threadPool;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}