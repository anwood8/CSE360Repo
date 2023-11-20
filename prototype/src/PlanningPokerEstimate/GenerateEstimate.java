package PlanningPokerEstimate;

import java.util.ArrayList;
import java.util.Collections;

import application.ProjectData;

public class GenerateEstimate {
	// Total hours of all relevant projects
	private static double totalHours;
	// Estimate
	private static int storyPointEstimate;
	// Baseline value for an estimate of 1
	private static double baseline;
	
	// Generates a list of relevant projects given a list of irrelevant projects
	public static void generateRelevantProjects(ArrayList<ProjectData> relevantProjectsList, ArrayList<ProjectData> irrelevantProjectsList, ArrayList<String> userStoryKeywords) {
		// Sort the keywords of the user story
		Collections.sort(userStoryKeywords);
		
		for (int i = 0; i < irrelevantProjectsList.size(); i++) {
			float weight = 0f;
			ProjectData project = irrelevantProjectsList.get(i);
			ArrayList<String> projectKeywords = project.getKeywords();
			
			// Search the user story keywords for keywords that match the historical project's keywords
			for (int j = 0; j < projectKeywords.size(); j++) {
				if(keywordMatch(projectKeywords.get(j), userStoryKeywords)) {
					weight += (float)1 / projectKeywords.size();
				}
			}
			
			// If it found keywords that matched, remove it from the irrelevant projects list and add it to the relevant projects list
			if (weight > 0) {
				relevantProjectsList.add(project);
				irrelevantProjectsList.remove(project);
				i--;
				project.addWeight(weight);
				System.out.println("Weight for " + project.getName() + ": " + project.getWeight());
			}
		}
	}
	
	// Looks at a log and accumulates the number of hours. Chooses relevant projects based on key words
	public static int generateEstimate(ArrayList<ProjectData> relevantProjectsList) {
		// Set total hours to 0
		totalHours = 0;
		
		// Calculate the total hours
		for (int i = 0; i < relevantProjectsList.size(); i++) {
			ProjectData project = relevantProjectsList.get(i);
			
			float weight = project.getWeight(); // Weight of the current project
			totalHours += weight*project.getTotalElapsedTime();
		}
		
		// Generate the story point estimate
		storyPointEstimate = (int)(totalHours / baseline);
		
		if (storyPointEstimate < 1) return 1;
		else return storyPointEstimate;
	}
	// Allow the user to select the number of hours to mark as a 1
	public static void selectBaseline(double hours) {
		baseline = hours;
	}
	// Binary Search keywords
	private static boolean keywordMatch(String keyword, ArrayList<String> userStoryKeywords) {
		int size = userStoryKeywords.size();
		int lowIndex = 0;
		int highIndex = size - 1;
		while (lowIndex <= highIndex) {
			int midIndex = (highIndex + lowIndex) / 2;
			int value = userStoryKeywords.get(midIndex).compareTo(keyword);
			if (value < 0) {
				lowIndex = midIndex + 1;
			}
			else if (value > 0) {
				highIndex = midIndex - 1;
			}
			else {
				return true;
			}
		}
		return false;
	}
	public static int getEstimate() {
		return storyPointEstimate;
	}
	
	public static double getBaseline() {
		return baseline;
	}
} 
