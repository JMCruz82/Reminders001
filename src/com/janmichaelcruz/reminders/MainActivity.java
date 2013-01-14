package com.janmichaelcruz.reminders;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		DBAdapter db = new DBAdapter(this);
		
		try {
			String destPath = "/data/data" + getPackageName() + "/databases";
			
			File f = new File(destPath);
			
			if (!f.exists()) {
				f.mkdirs();
				
				f.createNewFile();
				
				CopyDB(getBaseContext().getAssets().open("mydb"),
					   new FileOutputStream(destPath + "/MyDB"));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		db.open();
		
		Cursor c = db.getAllReminders();
	}

	private void CopyDB(InputStream open, FileOutputStream fileOutputStream) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
