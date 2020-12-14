package com.bracket.common.register;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class TimeCheck {
	public static AtomicBoolean isStarted = new AtomicBoolean(false);
	public static AtomicBoolean isSeted = new AtomicBoolean(false);
	private static String timepath = "C:/Windows/System32/msoertagaAccess2";

	public static String productCode = "";
	public static String key = "";
	private static final int intevalPeriodMin = 5;
	private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	public static Date standToday;

	public static void initDateTime() throws ParseException {
		standToday = new Date();
		if (!new java.io.File(timepath).isFile()) {
			if (FileOperate.isExist(Register._registerFileName, false)) {
				String timecon = EncryptHelper
						.AESDecrypt(FileOperate.ReadInfo(
								Register._registerFileName, false), key);
				Date timec = dateTimeFormat.parse(timecon);
				standToday = standToday.compareTo(timec) > 0 ? standToday
						: timec;
			}
		} else {
			String timecon = EncryptHelper.AESDecrypt(
					FileOperate.ReadInfo(timepath, true), key);
			Date timec = dateTimeFormat.parse(timecon);
			standToday = standToday.compareTo(timec) > 0 ? standToday : timec;
		}
	}

	public static void init(String productCode, String key)
			throws ParseException {
		if (isSeted.compareAndSet(false, true)) {
			TimeCheck.productCode = productCode;
			timepath = timepath + "_"
					+ new StringBuffer(productCode).reverse().toString()
					+ ".ini";
			TimeCheck.key = key;
			initDateTime();

			// startAddup();
		}
	}

	public static void startAddup() {
		System.out.println("before compareAndSet");
		if (isStarted.compareAndSet(false, true)) {
			System.out.println("time start");
			if (!(new java.io.File(timepath)).isFile()) {
				FileOperate.WriteInfo(
						timepath,
						EncryptHelper.AESEncrypt(
								dateTimeFormat.format(standToday), key), false,
						true);
			}
			/*
			 * TimerTask task = new TimerTask() {
			 *
			 * @Override public void run() {
			 * System.out.println(dateTimeFormat.format(new
			 * Date())+" time run"); // Register.cacheFlag= false;
			 * updateTime(intevalPeriodMin); } }; Timer timer = new Timer(); //
			 * long intevalPeriod = intevalPeriodMin*60 * 1000; long
			 * intevalPeriod = 20 * 1000; timer.schedule(task, intevalPeriod,
			 * intevalPeriod); TimeCheck.updateTime(0);
			 */

			/*
			 * TimeCheck.updateTime(0); new Thread(new Runnable() {
			 *
			 * @Override public void run() { while (true){ // long intevalPeriod
			 * = intevalPeriodMin*60 * 1000; long intevalPeriod = 20 * 1000; try
			 * { Thread.sleep(intevalPeriod); } catch (InterruptedException e) {
			 * e.printStackTrace(); }
			 * System.out.println(dateTimeFormat.format(new
			 * Date())+" time run"); Register.cacheFlag= false;
			 * updateTime(intevalPeriodMin); } } }).start();
			 */

			TimeCheck.updateTime(0);
			Runnable runnable = new Runnable() {
				public void run() {
					System.out.println(dateTimeFormat.format(new Date())
							+ " time run");
					Register.cacheFlag = false;
					updateTime(intevalPeriodMin);
				}
			};
			long intevalPeriod = intevalPeriodMin * 60 * 1000;
			// long intevalPeriod = 20 * 1000;
			ScheduledExecutorService service = Executors
					.newSingleThreadScheduledExecutor();
			// 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
			service.scheduleAtFixedRate(runnable, intevalPeriod, intevalPeriod,
					TimeUnit.MILLISECONDS);
		}
	}

	public static boolean updateTime(int addMin) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(standToday);
		cal1.add(Calendar.MINUTE, addMin);
		standToday = cal1.getTime();
		System.out.println("standToday:" + standToday + "  cal1:" + cal1);

		FileOperate.WriteInfo(timepath, EncryptHelper.AESEncrypt(
				dateTimeFormat.format(standToday), key), false, true);
		FileOperate.WriteInfo(Register._registerFileName, EncryptHelper
						.AESEncrypt(dateTimeFormat.format(standToday), key), false,
				false);

		return true;
	}

	/*
	 * public static boolean check(Date topdate){ try { Date today= new Date();
	 * return today.compareTo(standToday)<0;
	 *
	 *
	 * if (!(new java.io.File(timepath)).isFile()){ if
	 * (!FileOperate.isExist(Register._registerFileName,false)) {
	 * FileOperate.WriteInfo(Register._registerFileName,
	 * EncryptHelper.AESEncrypt( dateTimeFormat.format(today), key),
	 * false,false); } Date endTime; String _registerTime=
	 * EncryptHelper.AESDecrypt
	 * (FileOperate.ReadInfo(Register._registerFileName,false), key);
	 * System.out.println("_registerTime:"+_registerTime); endTime =
	 * dateTimeFormat.parse(_registerTime);
	 *
	 * return !(today.compareTo(endTime) < 0); }else{
	 *
	 * String timecon=
	 * EncryptHelper.AESDecrypt(FileOperate.ReadInfo(timepath,true),key); Date
	 * lastDate = new SimpleDateFormat("yyyy-MM-dd").parse(timecon); return
	 * !(lastDate.compareTo(topdate)>0); }
	 *
	 * } catch (Exception e) {
	 *
	 * return false; } }
	 */
}
