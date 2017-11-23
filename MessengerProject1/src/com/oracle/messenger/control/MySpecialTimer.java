package com.oracle.messenger.control;

import java.util.Timer;

public class MySpecialTimer extends Timer {
	private String timerName;

	public String getTimerName() {
		return timerName;
	}
	public void setTimerName(String timerName) {
		this.timerName = timerName;
	}

	public MySpecialTimer(String timerName) {
		super();
		this.timerName = timerName;
	}

	public MySpecialTimer() {
		super();
	}
	

}
