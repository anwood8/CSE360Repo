package FalseInputsMitigator;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/*
 * Contains functions to add items to various dropdown lists.
 */
public class CreateDropdownList {
	// ArrayLists for storing the various dropdown items needed for EffortLogger.
	private static ArrayList<String> listOfProjects = new ArrayList<String>();
	private static ArrayList<String> listOfLifecycleSteps = new ArrayList<String>();
	private static ArrayList<String> listOfEffortCategories = new ArrayList<String>();
	private static ArrayList<String> listOfDeliverables = new ArrayList<String>();
	private static ObservableList<String> projectOptions = FXCollections.observableArrayList();
	private static ObservableList<String> lifecycleStepOptions = FXCollections.observableArrayList();
	private static ObservableList<String> effortCategoryOptions = FXCollections.observableArrayList();
	private static ObservableList<String> deliverableOptions = FXCollections.observableArrayList();
	
	
	// A function that adds projects to the project ArrayList (Setter)
	public static String addProject(String project) {
		if (FalseInputsChecker.checkIfValidInput(project, 0)) {
			listOfProjects.add(project);
			return "Added \"" + listOfProjects.get(listOfProjects.size() - 1) + "\"";
		}
		else {
			return "Invalid Input. Only use alphanumeric characters!";
		}
	}
	// A function that grabs the list of projects array list (Getter)
	public static ArrayList<String> getProjects() {
		return listOfProjects;
	}
	// A function that adds lifecycle steps to the lifecycle steps ArrayList (Setter)
	public static String addLifecycleStep(String lifecycleStep) {
		if (FalseInputsChecker.checkIfValidInput(lifecycleStep, 0)) {
			listOfLifecycleSteps.add(lifecycleStep);
			return "Added \"" + listOfLifecycleSteps.get(listOfLifecycleSteps.size() - 1) + "\"";
		}
		else {
			return "Invalid Input. Only use alphanumeric characters!";
		}
	}
	// A function that grabs the list of lifecycle steps array list (Getter)
	public static ArrayList<String> getLifecycleSteps() {
		return listOfLifecycleSteps;
	}
	// A function that adds effort categories to the effort categories ArrayList (Setter)
	public static String addEffortCategory(String effortCategory) {
		if (FalseInputsChecker.checkIfValidInput(effortCategory, 0)) {
			listOfEffortCategories.add(effortCategory);
			return "Added \"" + listOfEffortCategories.get(listOfEffortCategories.size() - 1) + "\"";
		}
		else {
			return "Invalid Input. Only use alphanumeric characters!";
		}
	}
	// A function that adds deliverables to the deliverables ArrayList
	public static String addDeliverables(String deliverable) {
		if (FalseInputsChecker.checkIfValidInput(deliverable, 0)) {
			listOfDeliverables.add(deliverable);
			return "Added \"" + listOfDeliverables.get(listOfDeliverables.size() - 1) + "\"";
		}
		else {
			return "Invalid Input. Only use alphanumeric characters!";
		}
	}
	
}
