package com.krikelin.spotifysource;

public class Duration {
	private int mMinutes;
	private int mHours;
	private int mSeconds;
	public static String zerofill(int number)
	{
		return number < 10 ?  "0"+String.valueOf(number) :  String.valueOf(number);
	}
	public Duration(int seconds)
	{
		// FROM http://answers.yahoo.com/question/index?qid=20080905173323AArLUVA
		int secsIn = seconds;
		mHours = secsIn / 3600;
		int remainder = secsIn % 3600;
		mMinutes = remainder / 60;
		mSeconds = remainder % 60;
		
		
	}
	@Override
	public String toString()
	{
		return String.format( ( mHours > 0 ? zerofill(mHours) + ":" : "") + zerofill(mMinutes)+":"+zerofill(mSeconds));
	}
	public int getMinutes() {
		return mMinutes;
	}
	
	public int getHours() {
		return mHours;
	}
	
	public int getSeconds() {
		return mSeconds;
	}
	
}
