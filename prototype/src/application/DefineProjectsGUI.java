package application;

import FalseInputsMitigator.CreateDropdownList;
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

// A screen used to add items to projects, life cycle step, effort category, and deliverables
public class DefineProjectsGUI {
	// Define the attributes to add items
	private HBox addItemHBox;
	private Label sceneDescription;
	private TextField itemTextField;
	private Button addItem;
	private ObservableList<String> categoryOptions;
	private ComboBox<?> categoryComboBox;
	private ObservableList<String> projectOptions;
	private ComboBox<?> projectComboBox;
	private ObservableList<String> effortCategoryOptions;
	private ComboBox<?> effortCategoryComboBox;
	// Define the attributes to clear the text field
	private Button clearButton;
	// Define the attributes to navigate to other scenes in EffortLogger
	private HBox navigationHBox;
	private Button effortLogConsole;
	
	// Runs all the methods needed to create the GUI for defining projects.
	public void GUI(Stage defineProjectsStage, User user) {
		Label testLabel = new Label("On this page, add items to projects, life cycle step, effort category, and deliverables for each effort category.");
		
		// Create the UI for the user to add items to a chosen category
		addItemToOptions(user);
		
		// Create the buttons used to navigate to the other EffortLogger scenes
		navigateButtons(defineProjectsStage, user);
		
		// Add button to clear the text field
		clearTextField();
		
		// Create a vertical box
        VBox root = new VBox();
        root.getChildren().add(testLabel);
        root.getChildren().add(addItemHBox);
        root.getChildren().add(navigationHBox);
		
		defineProjectsStage.setScene(new Scene(root, 1024, 640));
	}
	// Adds a way to add an item to a specific category
	private void addItemToOptions(User user) {
		// A horizontal box for holding the various items
		addItemHBox = new HBox();
		
		// A dropdown menu telling the user what category they're adding to right now
		categoryOptions = FXCollections.observableArrayList();
		categoryOptions.add("Project");
		categoryOptions.add("Lifecycle Step");
		categoryOptions.add("Deliverable");
		categoryComboBox = new ComboBox(categoryOptions);
		categoryComboBox.setPromptText("Choose Your Category");
		categoryComboBox.getSelectionModel().selectedItemProperty().addListener((categoryOptions, oldValue, newValue) -> {
			// Remove unnecessary boxes
			addItemHBox.getChildren().remove(projectComboBox);
			addItemHBox.getChildren().remove(effortCategoryComboBox);
			
			// If "Lifecycle Step" is selected, it'll allow the user to select a project to add a lifecycle step to
			if (categoryComboBox.getValue().equals("Lifecycle Step")) {
				addItemHBox.getChildren().remove(projectComboBox);
				addItemHBox.getChildren().add(1, projectComboBox);
				projectOptions.clear();
				projectOptions.addAll(user.getListOfProjects());
				projectComboBox.setPromptText("Select a project");
			}
			// If "Deliverable" is selected, it'll allow the user to select a project and an effort category to add a deliverable to
			else if (categoryComboBox.getValue().equals("Deliverable")) {
				addItemHBox.getChildren().remove(projectComboBox);
				addItemHBox.getChildren().add(1, projectComboBox);
				addItemHBox.getChildren().remove(effortCategoryComboBox);
				addItemHBox.getChildren().add(2, effortCategoryComboBox);
				projectOptions.clear();
				projectOptions.addAll(user.getListOfProjects());
				projectComboBox.setPromptText("Select a project");
				effortCategoryOptions.clear();
			}
		});
		addItemHBox.getChildren().add(categoryComboBox);
		
		// A dropdown list to select a given project
		projectOptions = FXCollections.observableArrayList();
		projectComboBox = new ComboBox(projectOptions);
		projectComboBox.getSelectionModel().selectedItemProperty().addListener((categoryOptions, oldValue, newValue) -> {
			// Checks if a project has been selected
			if (projectComboBox.getValue() != null) {
				// Adds all the effort categories of the given project to the dropdown list
				effortCategoryOptions.addAll(user.getListOfEffortCategories(projectComboBox.getSelectionModel().getSelectedIndex()));
			}
		});
		
		// A dropdown list to select an effort category
		effortCategoryOptions = FXCollections.observableArrayList();
		effortCategoryComboBox = new ComboBox(effortCategoryOptions);
		
		// A text field where the user can type in
		itemTextField = new TextField();
		addItemHBox.getChildren().add(itemTextField);
		
		// A button for the user to add item to that category
		addItem = new Button();
		addItem.setText("Add to Category");
		addItem.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
            	// Check if a category is selected
            	if (categoryComboBox.getValue() != null) {
	            	// If the category is project, add to the project field
	            	if (categoryComboBox.getValue().equals("Project")) {
	            		String str = user.addProject(itemTextField.getText());
	                	itemTextField.setText(str);
	            	}
	            	// If the category is a lifecycle step, add to the lifecycle step of the selected project
	            	else if (categoryComboBox.getValue().equals("Lifecycle Step") && projectComboBox.getValue() != null) {
	            		String str = user.addLifecycleStep(itemTextField.getText(), projectComboBox.getSelectionModel().getSelectedIndex());
	                	itemTextField.setText(str);
	            	}
	            	else if (categoryComboBox.getValue().equals("Deliverable") && projectComboBox.getValue() != null && effortCategoryComboBox.getValue() != null) {
	            		String str = user.addDeliverable(itemTextField.getText(), projectComboBox.getSelectionModel().getSelectedIndex(), effortCategoryComboBox.getSelectionModel().getSelectedIndex());
	            		itemTextField.setText(str);
	            	}
	            	else {
	            		itemTextField.setText("Not every box is filled");
	            	}
            	}
            	else {
            		itemTextField.setText("No category selected");
            	}
            }
        });
		addItemHBox.getChildren().add(addItem);
	}
	// Adds a button to clear the text field
	private void clearTextField() {
		clearButton = new Button("Clear");
		clearButton.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
            	// Clear the text field
            	itemTextField.setText("");
            }
        });
		addItemHBox.getChildren().add(clearButton);
	}
	// Buttons used to navigate to the other scenes in EffortLogger.
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
