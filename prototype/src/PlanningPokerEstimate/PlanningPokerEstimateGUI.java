package PlanningPokerEstimate;

import java.util.ArrayList;
import java.util.Collections;

import UserStories.UserStory;
import application.PlanningPokerGUI;
import application.ProjectData;
import application.ReportsGUI;
import application.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PlanningPokerEstimateGUI {
	// A temporary array to test user stories
	private ArrayList<ProjectData> arrayProjectData;
	private ArrayList<ProjectData> relevantProjectData;
	private ArrayList<ProjectData> irrelevantProjectData;
	private ArrayList<ProjectData> relevantProjects = new ArrayList<>();
	// Vertical box for all the GUI elements
	private VBox planningPokerEstimateVBox;
	// Labels
	private Label instruction;
	/** Attributes for the back button **/
	// Label
	private Label backLabel;
	// Button
	private Button backButton;
	/** Attributes for selecting a baseline **/
	// Baseline vertical box
	private VBox selectBaselineVBox;
	// Baseline horizontal box
	private HBox selectBaselineHBox;
	// Labels and field 
	private Label selectBaselineLabel;
	private TextField selectBaselineTextfield;
	private Button selectBaselineButton;
	/** Attributes for creating a button that selects the relevant projects for generating an estimate **/
	// VBox
	private VBox generateRelevantProjectsVBox;
	// Labels
	private Label instructionGenerateProjects;
	// Buttons
	private Button generateProjectsButton;
	/** Attributes for creating dropdown lists for relevant and irrelevant projects **/
	// Labels
	private Label relevantProjectsLabel;
	private Label irrelevantProjectsLabel;
	// Projects HBox
	private HBox projectsHBox;
	// Projects VBox's
	private VBox relevantProjectsVBox;
	private VBox irrelevantProjectsVBox;
	// Projects Dropdown lists
	private ObservableList<String> relevantProjectsOptions;
	private ComboBox<?> relevantProjectsComboBox;
	private ObservableList<String> irrelevantProjectsOptions;
	private ComboBox<?> irrelevantProjectsComboBox;
	private ComboBox<?> irrelevantComboBox;
	/** Attributes for generating an estimate **/
	// Generate estimate vertical box
	private VBox generateEstimateVBox;
	// Generate estimate button
	private Button generateEstimateButton;
	// Generate estimate labels
	private Label estimateLabel;
	
	public void GUI(Stage planningPokerEstimateStage, User user, UserStory userStory) {
		// For testing purposes
		testProjectData();
		testProjectData();
		
		// Add a back button
		createBackButtonGUIElements(planningPokerEstimateStage, user);
		
		// Grab historical projects
		getRelevantProjectData();
		getIrrelevantProjectData();
		
		// Select baseline GUI elements
		selectBaselineGUIElements();
		
		// Create GUI elements for the button that separates historical data in a set of relevant and irrelevant projects
		selectProjectLists(userStory);
		
		// Create project lists GUI elements
		createProjectListsGUIElements(arrayProjectData);
		
		// Create generate estimate GUI elements
		createGenerateEstimateGUIElements();
		
		
		// Create the vertical box for this GUI
		planningPokerEstimateVBox = new VBox();
		planningPokerEstimateVBox.getChildren().add(backLabel);
		planningPokerEstimateVBox.getChildren().add(backButton);
		planningPokerEstimateVBox.getChildren().add(selectBaselineVBox);
		planningPokerEstimateVBox.getChildren().add(generateRelevantProjectsVBox);
		planningPokerEstimateVBox.getChildren().add(irrelevantProjectsVBox);
		planningPokerEstimateVBox.getChildren().add(relevantProjectsVBox);
		planningPokerEstimateVBox.getChildren().add(generateEstimateVBox);
		
		
		// Set the stage for the planning poker estimate GUI
		planningPokerEstimateStage.setScene(new Scene(planningPokerEstimateVBox, 1024, 768));
	}
	// Create a back button
	private void createBackButtonGUIElements(Stage planningPokerEstimateStage, User user) {
		backLabel = new Label("Go back to Planning Poker");
		
		backButton = new Button("Planning Poker");
		backButton.setOnAction(new EventHandler<>() {
			public void handle(ActionEvent event) {
				System.out.println("Switching to planning poker screen");
				PlanningPokerGUI planningPokerGUI = new PlanningPokerGUI();
				planningPokerGUI.GUI(planningPokerEstimateStage, user);
				planningPokerEstimateStage.show();
			}
		});
	}
	// Select baseline GUI elements
	private void selectBaselineGUIElements() {
		// Create a vertical box for the select baseline elements
		selectBaselineVBox = new VBox();
		
		// Instruction label
		selectBaselineLabel = new Label("Select the number of hours that represents a 1.");
		selectBaselineVBox.getChildren().add(selectBaselineLabel);
		
		// Create a horizontal box to hold the elements to ask user for their input
		selectBaselineHBox = new HBox();
		selectBaselineVBox.getChildren().add(selectBaselineHBox);
		
		// User input elements
		selectBaselineTextfield = new TextField();
		selectBaselineHBox.getChildren().add(selectBaselineTextfield);
		
		selectBaselineButton = new Button("Submit");
		selectBaselineButton.setOnAction(new EventHandler<>() {
			public void handle(ActionEvent event) {
				if (selectBaselineTextfield.getText() != null) {
					try {
						GenerateEstimate.selectBaseline(Double.parseDouble(selectBaselineTextfield.getText()));
					}
					catch (Exception e) {
						selectBaselineTextfield.setText("Not a valid number");
					}
					
				}
			}
		});
		selectBaselineHBox.getChildren().add(selectBaselineButton);
		
	}
	// Create a button that when pressed, generates a list of relevant and irrelevant projects
	private void selectProjectLists(UserStory userStory) {
		// Create a VBox 
		generateRelevantProjectsVBox = new VBox();
		
		// Instruction label
		instructionGenerateProjects = new Label("Click the button to have the program select a list of relevant and irrelevant projects");
		generateRelevantProjectsVBox.getChildren().add(instructionGenerateProjects);
		
		// A button the user clicks to generate a list of relevant and irrelevant projects
		generateProjectsButton = new Button("Generate Project List");
		generateProjectsButton.setOnAction(new EventHandler<>() {
			public void handle(ActionEvent event) {
				// Get the list of keywords for the user story
				ArrayList<String> userStoryKeywords = userStory.keywords;
						
				// Select relevant and irrelevant projects
				GenerateEstimate.generateRelevantProjects(relevantProjects, arrayProjectData, userStoryKeywords);
				
				// Testing purposes, show relevant and irrelevant items
				System.out.println("Relevant Projects:");
				for (int i = 0; i < relevantProjects.size(); i++) {
					System.out.println(relevantProjects.get(i).getName());
				}
				System.out.println("Irrelevant Projects:");
				for (int i = 0; i < arrayProjectData.size(); i++) {
					System.out.println(arrayProjectData.get(i).getName());
				}
					
					// Update the list of relevant projects and irrelevant projects
			}
		});
		generateRelevantProjectsVBox.getChildren().add(generateProjectsButton);
	}
	// Create two dropdown lists for the list of relevant projects and irrelevant projects
	private void createProjectListsGUIElements(ArrayList<ProjectData> arrayProjectData) {
		projectsHBox = new HBox();
		
		relevantProjectsVBox = new VBox();
		relevantProjectsLabel = new Label("Relevant Projects");
		
		irrelevantProjectsVBox = new VBox();
		irrelevantProjectsLabel = new Label("Irrelevant Projects");
		
		irrelevantProjectsOptions = FXCollections.observableArrayList();
		
		//Moving all projects to the irrelevant project ComboBox
		irrelevantProjectData = arrayProjectData;
		for(int i = 0; i < irrelevantProjectData.size(); i++) {
			irrelevantProjectsOptions.add(irrelevantProjectData.get(i).getName());
		}
		
		irrelevantProjectsComboBox = new ComboBox(irrelevantProjectsOptions);
		irrelevantProjectsComboBox.setPromptText("Irrelevant Projects");
		
		relevantProjectsOptions = FXCollections.observableArrayList();
		relevantProjectData = new ArrayList<ProjectData>();
		
		//Button to switch irrelevant projects to relevant projects
		Button addRelevantProjectButton = new Button("Add to relevant projects");
		addRelevantProjectButton.setOnAction(new EventHandler<> () {
			public void handle(ActionEvent arg0) {
				if(irrelevantProjectsComboBox.getSelectionModel().getSelectedItem() != null) {
					int i = irrelevantProjectsComboBox.getSelectionModel().getSelectedIndex();
					
					relevantProjectData.add(irrelevantProjectData.get(i));
					irrelevantProjectData.remove(i);
					
					relevantProjectsOptions.clear();
					for(int j = 0; j < relevantProjectData.size(); j++) {
						relevantProjectsOptions.add(relevantProjectData.get(j).getName());
					}
					
					irrelevantProjectsOptions.clear();
					for(int j = 0; j < irrelevantProjectData.size(); j++) {
						irrelevantProjectsOptions.add(irrelevantProjectData.get(j).getName());
					}
				}
			}
		});
		
		Button addIrrelevantProjectButton = new Button("Add to irrelevant projects");
		addIrrelevantProjectButton.setOnAction(new EventHandler<> () {
			public void handle(ActionEvent arg0) {
				if(relevantProjectsComboBox.getSelectionModel().getSelectedItem() != null) {
					int i = relevantProjectsComboBox.getSelectionModel().getSelectedIndex();
					
					irrelevantProjectData.add(relevantProjectData.get(i));
					relevantProjectData.remove(i);
					
					
					irrelevantProjectsOptions.clear();
					for(int j = 0; j < irrelevantProjectData.size(); j++) {
						irrelevantProjectsOptions.add(irrelevantProjectData.get(j).getName());
					}
					
					relevantProjectsOptions.clear();
					for(int j = 0; j < relevantProjectData.size(); j++) {
						relevantProjectsOptions.add(relevantProjectData.get(j).getName());
					}
					
				}
			}
		});
		
		for(int j = 0; j < relevantProjectData.size(); j++) {
			relevantProjectsOptions.add(relevantProjectData.get(j).getName());
		}
		

		relevantProjectsComboBox = new ComboBox(relevantProjectsOptions);
		relevantProjectsComboBox.setPromptText("Relevant Projects");
		
		
		relevantProjectsVBox.getChildren().add(relevantProjectsLabel);
		relevantProjectsVBox.getChildren().add(relevantProjectsComboBox);
		relevantProjectsVBox.getChildren().add(addIrrelevantProjectButton);
		irrelevantProjectsVBox.getChildren().add(irrelevantProjectsLabel);
		irrelevantProjectsVBox.getChildren().add(irrelevantProjectsComboBox);
		irrelevantProjectsVBox.getChildren().add(addRelevantProjectButton);
	}
	// Generate estimate
	private void createGenerateEstimateGUIElements() {
		// Create a VBox for the generate estimate elements
		generateEstimateVBox = new VBox();
		
		// Estimate label
		estimateLabel = new Label("Estimate: ");
		
		// Create a button for the user to press to generate an estimate
		generateEstimateButton = new Button("Generate Estimate");
		generateEstimateButton.setOnAction(new EventHandler<>() {
			public void handle(ActionEvent event) {
				// Check if a user story is selected
				if (GenerateEstimate.getBaseline() > 0) {
					estimateLabel.setText("Estimate: " + GenerateEstimate.generateEstimate(relevantProjects));
				}
				else {
					estimateLabel.setText("Baseline <= 0 selected");
				}
			}
		});
		generateEstimateVBox.getChildren().add(generateEstimateButton);
		
		generateEstimateVBox.getChildren().add(estimateLabel);
		
	}
	// An array for testing purposes
	private void testProjectData() {
		arrayProjectData = new ArrayList<ProjectData>();
		
		ProjectData proj1 = new ProjectData("Project 1");
		proj1.addKeywords("Test1");
		proj1.addKeywords("Test2");
		proj1.addProjectData("stage1,test,test,01:00:20");
		proj1.calculateTotalTime();
		ProjectData proj2 = new ProjectData("Project 2");
		proj2.addKeywords("Test3");
		proj2.addKeywords("Test4");
		proj2.addProjectData("stage1,test,test,01:00:20");
		proj2.calculateTotalTime();
		ProjectData proj3 = new ProjectData("Project 3");
		proj3.addKeywords("Test5");
		proj3.addKeywords("Test6");
		proj3.addProjectData("stage1,test,test,01:00:20");
		proj3.calculateTotalTime();
		
		arrayProjectData.add(proj1);
		arrayProjectData.add(proj2);
		arrayProjectData.add(proj3);
	}
	
	private ArrayList<ProjectData> getRelevantProjectData() {
		return relevantProjectData;
	}
	
	private ArrayList<ProjectData> getIrrelevantProjectData() {
		return irrelevantProjectData;
	}
	
}
