package com.janmichaelcruz.reminders;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class NotificationInfo {
	private String dateString;
	private Date date;
	private CharSequence title;
	private int id;
	private boolean set;
	private boolean[] runDays;

	public NotificationInfo(int i, String ds, CharSequence t) {
		setDateString(ds);
		setDate(stringToDate(ds));
		setTitle(t);
		setId(i);
		set = false;
	}

	public NotificationInfo() {
		// TODO Auto-generated constructor stub
	}

	public static Date stringToDate(String time) {
		String[] timeArray = time.split(":");

		int hour = Integer.parseInt(timeArray[0]);
		int minute = Integer.parseInt(timeArray[1]);

		GregorianCalendar gregCal = new GregorianCalendar();

		gregCal.set(Calendar.HOUR_OF_DAY, hour);
		gregCal.set(Calendar.MINUTE, minute);
		gregCal.set(Calendar.SECOND, 0);

		return gregCal.getTime();
	}

	public boolean isSet() {
		return set;
	}

	public void setSet(boolean set) {
		this.set = set;
	}

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public CharSequence getTitle() {
		return title;
	}

	public void setTitle(CharSequence title) {
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean runDay(int day) {
		return runDays[day];
	}
}
