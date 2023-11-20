package application;

import java.util.ArrayList;

public class ProjectData {
	// Attributes
	private String name;
	private ArrayList<String> projectData;
	private double totalElapsedTime;
	private ArrayList<String> keywords;
	private String description;
	private float weight;
	
	// constructor
	public ProjectData(String name) {
		this.name = name;
		this.projectData = new ArrayList<String>(); 
		this.totalElapsedTime = 0;
		this.keywords = new ArrayList<String>();
		this.description = "";
		this.weight = 0; 
	}
	
	// getters 
	public String getName() {
		return name;
	}
	
	public ArrayList<String> getProjectData() {
		return projectData;
	}
	
	public double getTotalElapsedTime() {
		return totalElapsedTime;
	}
	
	public ArrayList<String> getKeywords() {
		return keywords;
	}
	
	public String getDescription() {
		return description;
	}
	
	public float getWeight() {
		return weight;
	}
	
	// setters 
	public void addProjectData(String data) {
		projectData.add(data);
	}
	
	public void addKeywords(String inputKeywords) {
		keywords.add(inputKeywords);
	}
	
	public void addDescription(String inputDescription) {
		description = inputDescription;
	}
	
	public void addWeight(float inputWeight) {
		weight = inputWeight;
	}
	
	public double calculateTotalTime() {
		for (String data : projectData) {
			String[] parts = data.split(",");
			if (parts.length == 4) {
				double timeElapsed = convertTimeToDouble(parts[3]);
				totalElapsedTime += timeElapsed;
			}
			else {
				System.out.println("Invalid data format: " + data);
			}
		}
		return totalElapsedTime;
	}
	
	private double convertTimeToDouble(String time) {
	    String[] timeParts = time.split(":");
	    double hours = Double.parseDouble(timeParts[0]);
	    double minutes = Double.parseDouble(timeParts[1]) / 60.0;
	    double seconds = Double.parseDouble(timeParts[2]) / 3600.0;

	    return hours + minutes + seconds;
	}
}
