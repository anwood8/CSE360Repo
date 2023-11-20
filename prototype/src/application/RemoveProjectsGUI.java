package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RemoveProjectsGUI {
	// Attributes for the GUI
	private VBox guiVBox;
	private HBox itemSelectionHBox;
	private Button removeButton;
	private Label infoLabel;
	private ObservableList<String> categoryOptions;
	private ComboBox<?> categoryComboBox;
	// Attributes for removing a project
	private ObservableList<String> projectOptions;
	private ComboBox<?> projectComboBox;
	// Attributes for removing a lifecycle step from a project
	private ObservableList<String> lifecycleStepOptions;
	private ComboBox<?> lifecycleStepComboBox;
	// Attributes for removing a deliverable from an effort category
	private ObservableList<String> effortCategoryOptions;
	private ComboBox<?> effortCategoryComboBox;
	private ObservableList<String> deliverableOptions;
	private ComboBox<?> deliverableComboBox;
	// Attributes to move navigate to other scenes
	private HBox navigationHBox;
	private Button effortLogConsole;
	public void GUI(Stage removeProjectsStage, User user) {
		// General setup for GUI
		guiVBox = new VBox();
		itemSelectionHBox = new HBox();
		removeButton = new Button("Delete");
		infoLabel = new Label("Select a category");
		categoryOptions = FXCollections.observableArrayList();
		categoryOptions.add("Project");
		categoryOptions.add("Lifecycle Step");
		categoryOptions.add("Deliverable");
		categoryComboBox = new ComboBox(categoryOptions);
		categoryComboBox.setPromptText("Select a category");
		categoryComboBox.getSelectionModel().selectedItemProperty().addListener((categoryOptions, oldValue, newValue) -> {
			// Remove every combo box
			itemSelectionHBox.getChildren().remove(projectComboBox);
			itemSelectionHBox.getChildren().remove(lifecycleStepComboBox);
			itemSelectionHBox.getChildren().remove(effortCategoryComboBox);
			itemSelectionHBox.getChildren().remove(deliverableComboBox);
			
			// Remove project
			if (categoryComboBox.getValue().equals("Project")) {
				removeProject(user);
			}
			// Remove lifecycle step from a project
			else if (categoryComboBox.getValue().equals("Lifecycle Step")) {
				removeLifecycleStep(user);
			}
			// Remove a deliverable for an effort category
			else if (categoryComboBox.getValue().equals("Deliverable")) {
				removeDeliverable(user);
			}
		});
		// Navigation buttons
		navigateButtons(removeProjectsStage, user);
		
		// Final setup of GUI
		itemSelectionHBox.getChildren().add(categoryComboBox);
		itemSelectionHBox.getChildren().add(removeButton);
		guiVBox.getChildren().add(itemSelectionHBox);
		guiVBox.getChildren().add(infoLabel);
		guiVBox.getChildren().add(navigationHBox);
		
		removeProjectsStage.setScene(new Scene(guiVBox, 800, 480));
	}
	// Removes a project
	public void removeProject(User user) {
		// Tell the user what to do
		infoLabel.setText("Select a project to delete");
		
		// Add the list of projects to a dropdown list
		projectOptions = FXCollections.observableArrayList();
		projectOptions.addAll(user.getListOfProjects());
		projectComboBox = new ComboBox(projectOptions);
		projectComboBox.setPromptText("Select a project");
		
		// Set the action of the button to remove the selected 
		removeButton.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
            	if (projectComboBox.getValue() != null) {
	            	infoLabel.setText("Removed \"" + projectComboBox.getValue() + "\"");
	            	user.removeProject(projectComboBox.getSelectionModel().getSelectedIndex());
	            	projectOptions.remove(projectComboBox.getValue());
            	}
            	else {
            		infoLabel.setText("You forgot to select a project.");
            	}
            }
        });
		
		// Add a project dropdown list to the list of items the user can select
		itemSelectionHBox.getChildren().add(1, projectComboBox);
	}
	// Removes a lifecycle step from a project
	public void removeLifecycleStep(User user) {
		// Tell the user what to do
		infoLabel.setText("Select a project to delete a lifecycle step from.");
		
		// Add the list of projects to a dropdown list
		projectOptions = FXCollections.observableArrayList();
		projectOptions.addAll(user.getListOfProjects());
		projectComboBox = new ComboBox(projectOptions);
		projectComboBox.setPromptText("Select a project");
		
		// Add a listener 
		projectComboBox.getSelectionModel().selectedItemProperty().addListener((categoryOptions, oldValue, newValue) -> {
			// Add the list of lifecycle steps from a given project to a dropdown list if a project is selected
			if (projectComboBox.getValue() != null) {
				infoLabel.setText("Select a lifecycle step to delete.");
	    		lifecycleStepOptions = FXCollections.observableArrayList();
	    		lifecycleStepOptions.addAll(user.getListOfLifecycleSteps(projectComboBox.getSelectionModel().getSelectedIndex()));
	    		lifecycleStepComboBox = new ComboBox(lifecycleStepOptions);
	    		lifecycleStepComboBox.setPromptText("Select a lifecycle step");
	    		itemSelectionHBox.getChildren().add(2, lifecycleStepComboBox);
			}
		});
		
		// Set the action of the button to remove the selected 
		removeButton.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
            	if (projectComboBox.getValue() != null) {
            		if (lifecycleStepComboBox.getValue() != null) {
            			infoLabel.setText("Removed \"" + lifecycleStepComboBox.getValue() + "\"");
		            	user.removeLifecycleStep(projectComboBox.getSelectionModel().getSelectedIndex(), lifecycleStepComboBox.getSelectionModel().getSelectedIndex());
		            	lifecycleStepOptions.remove(lifecycleStepComboBox.getValue());
            		}
            		else {
            			infoLabel.setText("You forgot to choose a lifecycle step.");
            		}
            	}
            	else {
            		infoLabel.setText("You forgot to select a project.");
            	}
            }
        });
		
		// Add a project dropdown list to the list of items the user can select
		itemSelectionHBox.getChildren().add(1, projectComboBox);
	}
	// Removes a deliverable from an effort category of a given project
	public void removeDeliverable(User user) {
		// Tell the user what to do
		infoLabel.setText("Select a project to delete a deliverable from.");
		
		// Add the list of projects to a dropdown list
		projectOptions = FXCollections.observableArrayList();
		projectOptions.addAll(user.getListOfProjects());
		projectComboBox = new ComboBox(projectOptions);
		projectComboBox.setPromptText("Select a project");
		
		// Add a listener 
		projectComboBox.getSelectionModel().selectedItemProperty().addListener((projectOptions, oldValue, newValue) -> {
			// Add the list of effort categories from a given project to a dropdown list if a project is selected
			if (projectComboBox.getValue() != null) {
				infoLabel.setText("Select an effort category.");
				effortCategoryOptions = FXCollections.observableArrayList();
				effortCategoryOptions.addAll(user.getListOfEffortCategories(projectComboBox.getSelectionModel().getSelectedIndex()));
				effortCategoryComboBox = new ComboBox(effortCategoryOptions);
				effortCategoryComboBox.setPromptText("Select an effort category");
				effortCategoryComboBox.getSelectionModel().selectedItemProperty().addListener((categoryOptions, oldValue2, newValue2) -> {
					// Add the list of deliverables from the effort category to a dropdown list if a project is selected
					if (effortCategoryComboBox.getValue() != null) {
						infoLabel.setText("Select a deliverable to delete.");
						deliverableOptions = FXCollections.observableArrayList();
						deliverableOptions.addAll(user.getListOfDeliverablesForEffortCategory(projectComboBox.getSelectionModel().getSelectedIndex(), effortCategoryComboBox.getSelectionModel().getSelectedIndex()));
						deliverableComboBox = new ComboBox(deliverableOptions);
						deliverableComboBox.setPromptText("Select a deliverable");
						
						itemSelectionHBox.getChildren().add(3, deliverableComboBox);
					}
				});
				
	    		itemSelectionHBox.getChildren().add(2, effortCategoryComboBox);
			}
		});
		
		// Set the action of the button to remove the selected deliverable
		removeButton.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
            	if (projectComboBox.getValue() != null) {
            		if (effortCategoryComboBox.getValue() != null) {
            			if (deliverableComboBox.getValue() != null) {
	            			infoLabel.setText("Removed \"" + deliverableComboBox.getValue() + "\"");
			            	user.removeDeliverable(projectComboBox.getSelectionModel().getSelectedIndex(), effortCategoryComboBox.getSelectionModel().getSelectedIndex(), deliverableComboBox.getSelectionModel().getSelectedIndex());
			            	deliverableOptions.remove(deliverableComboBox.getValue());
            			}
            			else {
            				infoLabel.setText("You forgot to choose a deliverable.");
            			}
            		}
            		else {
            			infoLabel.setText("You forgot to choose an effort category.");
            		}
            	}
            	else {
            		infoLabel.setText("You forgot to select a project.");
            	}
            }
        });
		
		// Add a project dropdown list to the list of items the user can select
		itemSelectionHBox.getChildren().add(1, projectComboBox);
	}
	// Navigate to other scenes in EffortLogger
	private void navigateButtons(Stage effortLoggerStage, User user) {
		// Define the horizontal box for EffortLogger
		navigationHBox = new HBox();
		
		// A button to navigate back to the Effort Logger Console
		effortLogConsole = new Button();
		effortLogConsole.setText("Effort Log Console");
		effortLogConsole.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
        		System.out.println("Going back to Effort Log Console");
        		EffortLoggerGUI effortLoggerGUI = new EffortLoggerGUI();
        		effortLoggerGUI.GUI(effortLoggerStage, user);
        		effortLoggerStage.show();
            }
        });
		navigationHBox.getChildren().add(effortLogConsole);
	}
}
