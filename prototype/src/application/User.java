package application;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.opencsv.CSVWriter;

import FalseInputsMitigator.FalseInputsChecker;

public class User {
	// Defines all the attributes of a user
	private ArrayList<ProjectRelatedCategories> listOfProjects = new ArrayList<ProjectRelatedCategories>();
	private ArrayList<EffortReport> listOfEffortReports = new ArrayList<EffortReport>();
	
	// Setters
	// Add a new project to the user
	public String addProject(String project) {
		if (FalseInputsChecker.checkIfValidInput(project, 0) && !duplicateProject(project)) {
			listOfProjects.add(new ProjectRelatedCategories(project));
			return "Added \"" + listOfProjects.get(listOfProjects.size() - 1).getProjectName() + "\"";
		}
		else {
			return "Invalid Input. Only use alphanumeric characters and don't use a duplicate project name!";
		}
	}
	// Add a new lifecycle step to a given project
	public String addLifecycleStep(String lifecycleStep, int projectIndex) {
		if (FalseInputsChecker.checkIfValidInput(lifecycleStep, 0) && !duplicateLifecycleStep(lifecycleStep, projectIndex)) {
			listOfProjects.get(projectIndex).addLifecycleStep(lifecycleStep);
			int size = listOfProjects.get(projectIndex).getLifecycleSteps().size();
			return "Added \"" + listOfProjects.get(projectIndex).getLifecycleSteps().get(size-1) + "\"";
		}
		else {
			return "Invalid Input. Only use alphanumeric characters and don't use a duplicate lifecycle step for the project!";
		}
	}
	// Add a new deliverable to a given project's effort category
	public String addDeliverable(String deliverable, int projectIndex, int effortCategoryIndex) {
		if (FalseInputsChecker.checkIfValidInput(deliverable, 0) && !duplicateDeliverable(deliverable, projectIndex, effortCategoryIndex)) {
			listOfProjects.get(projectIndex).addDeliverable(deliverable, effortCategoryIndex);
			int size = listOfProjects.get(projectIndex).getListOfDeliverablesForEffortCategory(effortCategoryIndex).size();
			return "Added \"" + listOfProjects.get(projectIndex).getListOfDeliverablesForEffortCategory(effortCategoryIndex).get(size-1) + "\"";
		}
		else {
			return "Invalid Input. Only use alphanumeric characters and don't use a duplicate deliverable for this project and effort category!";
		}
	}
	// Add an effort report
	public void addEffortReport(EffortReport newEffortReport) {
		listOfEffortReports.add(newEffortReport);
	}
	// Generate a log
	public void generateLog() throws IOException {
		try {
			sortLog();
			FileWriter outputFile = new FileWriter("UserData/log.csv");
			CSVWriter writer = new CSVWriter(outputFile);
			String header[] = {"Project Name", "Lifecycle Step", "Effort Category", "Deliverable", "Time Elapsed"};
			writer.writeNext(header);
			
			for(int i=0;i<listOfEffortReports.size();i++) {
				EffortReport effortReport = listOfEffortReports.get(i);
				String[] data = {
						effortReport.getProjectName(),
						effortReport.getLifecycleStep(),
						effortReport.getEffortCategory(),
						effortReport.getDeliverable(),
						effortReport.getTaskLength()
				};
				writer.writeNext(data);
			}
			writer.close();
				
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	// Getters
	// Get the names of all the projects
	public ArrayList<String> getListOfProjects() {
		ArrayList<String> namesOfEveryProject = new ArrayList<String>();
		for (int i = 0; i < listOfProjects.size(); i++) {
			namesOfEveryProject.add(listOfProjects.get(i).getProjectName());
		}
		return namesOfEveryProject;
	}
	// Get the list of lifecycle steps of a given project
	public ArrayList<String> getListOfLifecycleSteps(int index) {
		return listOfProjects.get(index).getLifecycleSteps();
	}
	// Get the list of effort categories of a given project
	public ArrayList<String> getListOfEffortCategories(int index) {
		return listOfProjects.get(index).getListOfNamesForEffortCategories();
	}
	// Get the list of deliverables of an effort category for the specified project
	public ArrayList<String> getListOfDeliverablesForEffortCategory(int projectIndex, int effectCategoryIndex) {
		return listOfProjects.get(projectIndex).getListOfDeliverablesForEffortCategory(effectCategoryIndex);
	}
	// Get the list of effort reports created by the user
	public ArrayList<EffortReport> getListOfEffortReports() {
		return listOfEffortReports;
	}
	
	// List of methods to change/delete things
	// Remove a project
	public void removeProject(int index) {
		listOfProjects.remove(index);
	}
	// Remove a lifecycle step from a project
	public void removeLifecycleStep(int projectIndex, int lifecycleStepIndex) {
		listOfProjects.get(projectIndex).getLifecycleSteps().remove(lifecycleStepIndex);
	}
	// Remove a deliverable from a project
	public void removeDeliverable(int projectIndex, int effortCategoryIndex, int deliverableIndex) {
		listOfProjects.get(projectIndex).getListOfDeliverablesForEffortCategory(effortCategoryIndex).remove(deliverableIndex);
	}
	
	// Check if there is a duplicate
	// Check if there is a duplicate project
	private boolean duplicateProject(String projectName) {
		for (int i = 0; i < listOfProjects.size(); i++) {
			if (listOfProjects.get(i).getProjectName().compareTo(projectName) == 0) return true;
		}
		return false;
	}
	// Check if there is a duplicate lifecycle step
	private boolean duplicateLifecycleStep(String lifecycleStep, int projectIndex) {
		ProjectRelatedCategories project = listOfProjects.get(projectIndex);
		
		for (int i = 0; i < project.getLifecycleSteps().size(); i++) {
			if (project.getLifecycleSteps().get(i).compareTo(lifecycleStep) == 0) return true;
		}
		return false;
	}
	// Check if there is a duplicate deliverable for the given effort category
	private boolean duplicateDeliverable(String deliverable, int projectIndex, int effortCategoryIndex) {
		ProjectRelatedCategories project = listOfProjects.get(projectIndex);
		
		for (int i = 0; i < project.getListOfDeliverablesForEffortCategory(effortCategoryIndex).size(); i++) {
			if (project.getListOfDeliverablesForEffortCategory(effortCategoryIndex).get(i).compareTo(deliverable) == 0) return true;
		}
		return false;
	}
	
	// Implement comparator for comparing projects by their name
	class effortReportComparator implements Comparator<EffortReport> {

		@Override
		public int compare(EffortReport o1, EffortReport o2) {
			if (o1.getProjectName().compareTo(o2.getProjectName()) == 0) {
				return 0;
			}
			else if (o1.getProjectName().compareTo(o2.getProjectName()) > 0) {
				return 1;
			}
			else {
				return -1;
			}
		}
		
	}
	// Sort the log by project name
	private void sortLog() {
		Collections.sort(listOfEffortReports, new effortReportComparator());
	}
}
