package application;

import java.util.Date;

public class TaskTimer {
	// Attributes for the task timer
	private boolean timerStarted = false;
	private long startTime;
	private long elapsedTime;
	
	// Start timer
	public void startTimer() {
		timerStarted = true;
		startTime = System.currentTimeMillis();
	}
	// Stop timer and return the time elapsed
	public long stopTimer() {
		timerStarted = false;
		elapsedTime = (new Date().getTime() - startTime);
		return elapsedTime;
	}
	// Get whether the timer has started
	public boolean hasTimerStarted() {
		return timerStarted;
	}
}
