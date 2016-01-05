package com.dophin.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TestScheduleTask {
	public static void main(String[] args) {
		ScheduledExecutorService task = Executors.newScheduledThreadPool(1);
		long oneDay = 24 * 60 * 60 * 1000;
		long initDay = getTimeMillis("13:08:00") - System.currentTimeMillis();
		initDay = initDay > 0 ? initDay : oneDay + initDay;

		task.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				System.err.println(new Date());

			}
		}, initDay, oneDay, TimeUnit.MILLISECONDS);
	}

	private static long getTimeMillis(String time) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
			DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");
			Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " " + time);
			return curDate.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
