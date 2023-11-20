package application;

import java.util.ArrayList;

public class ProjectRelatedCategories {
	private String projectName;
	private ArrayList<String> listOfLifecycleSteps;
	private ArrayList<DeliverablesForEffortCategory> listOfEffortCategories;
	
	public ProjectRelatedCategories(String projectName) {
		this.projectName = projectName;
		this.listOfLifecycleSteps = new ArrayList<String>();
		this.listOfEffortCategories = new ArrayList<DeliverablesForEffortCategory>();
		this.listOfEffortCategories.add(new DeliverablesForEffortCategory("Plans"));
		this.listOfEffortCategories.add(new DeliverablesForEffortCategory("Deliverables"));
		this.listOfEffortCategories.add(new DeliverablesForEffortCategory("Interruptions"));
		this.listOfEffortCategories.add(new DeliverablesForEffortCategory("Defects"));
		this.listOfEffortCategories.add(new DeliverablesForEffortCategory("Others"));
	}
	// Setters
	public void addLifecycleStep(String lifecycleStep) {
		listOfLifecycleSteps.add(lifecycleStep);
	}
	public void addDeliverable(String deliverable, int index) {
		listOfEffortCategories.get(index).addItemToEffortCategory(deliverable);
	}
	// Getters
	// Get the project name of the selected project
	public String getProjectName() {
		return projectName;
	}
	// Get the lifecycle steps of the selected project
	public ArrayList<String> getLifecycleSteps() {
		return listOfLifecycleSteps;
	}
	// Get the list of names of every effort category
	public ArrayList<String> getListOfNamesForEffortCategories() {
		ArrayList<String> listOfEffortCategoryNames = new ArrayList<>();
		
		// Loops through every effort category and saves the names to an arraylist
		for (int i = 0; i < listOfEffortCategories.size(); i++) {
			listOfEffortCategoryNames.add(listOfEffortCategories.get(i).getCategoryName());
		}
		
 		return listOfEffortCategoryNames;
	}
	// Get the list of deliverables of the selected effort category
	public ArrayList<String> getListOfDeliverablesForEffortCategory(int index) {
		return listOfEffortCategories.get(index).getListForEffortCategory();
	}
}
