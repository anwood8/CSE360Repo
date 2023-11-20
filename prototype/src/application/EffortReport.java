package application;

import java.util.Comparator;

// Generates an effort report
public class EffortReport {
	private String projectName;
	private String lifecycleStep;
	private String effortCategory;
	private String deliverable;
	private String taskLength;
	
	// Constructor
	public EffortReport(String projectName, String lifecycleStep, String effortCategory, String deliverable) {
		// Set the attributes of the effort report
		this.projectName = projectName;
		this.lifecycleStep = lifecycleStep;
		this.effortCategory = effortCategory;
		this.deliverable = deliverable;
	}
	// Add the task length
	public void addTaskLength(long timeElapsed) {
		// Generate the string to represent the task length
		int seconds = (int)(timeElapsed / 1000) % 60;
		int minutes = (int)(timeElapsed / 1000) % 3600 / 60;
		int hours = (int)(timeElapsed / 1000) / 3600;
		String taskLength = String.format("%02d:%02d:%02d", hours, minutes, seconds);
		this.taskLength = taskLength;
	}
	// Return the project name
	public String getProjectName() {
		return projectName;
	}
	// Return the lifecycle step
	public String getLifecycleStep() {
		return lifecycleStep;
	}
	// Return the effort category
	public String getEffortCategory() {
		return effortCategory;
	}
	// Return the deliverable
	public String getDeliverable() {
		return deliverable;
	}
	// Return the task length
	public String getTaskLength() {
		return taskLength;
	}
}
