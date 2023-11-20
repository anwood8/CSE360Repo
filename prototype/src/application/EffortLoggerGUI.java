package application;

import java.io.IOException;

import FalseInputsMitigator.CreateDropdownList;
import SaveFeature.Save;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class EffortLoggerGUI {
	// Attributes for title
	private HBox titleHBox;
	private Label title;
	// Attributes for categories
	private VBox defineCategoriesVBox;
	private Label step1;
	private HBox categoriesHBox;
	private ObservableList<String> projectOptions;
	private ComboBox<?> projectsComboBox;
	private ObservableList<String> lifecycleStepOptions;
	private ComboBox<?> lifecycleStepsComboBox;
	private ObservableList<String> effortCategoryOptions;
	private ComboBox<?> effortCategoriesComboBox;
	private ObservableList<String> deliverableOptions;
	private ComboBox<?> deliverablesComboBox;
	// Attributes for the timers
	private VBox timerVBox;
	private HBox timerHBox;
	private Label timerLabel;
	private Button startActivity;
	private Button endActivity;
	private TaskTimer taskTimer;
	private EffortReport effortReport;
	// Attributes for the options page
	private HBox optionsHBox;
	private Button addProjectsButton;
	private Button removeProjectsButton;
	private Button addSaveButton;
	private Button addReportsButton;
	private Button planningPokerButton;
	private Button importPlanningPokerButton;
	
	// The main method and sets up the GUI for the Effort Log console
	public void GUI(Stage effortLoggerStage, User user) {
        // Create the title
        createTitle();
        
        // Create dropdown lists
        createDropdownLists(user);
        
        // Create timer
        createTimer(user);
        
        // Add a way to navigate to other scenes
        addOptions(effortLoggerStage, user);
        
        // Create a vertical box
        VBox root = new VBox();
        root.getChildren().add(titleHBox);
        root.getChildren().add(defineCategoriesVBox);
        root.getChildren().add(timerVBox);
        root.getChildren().add(optionsHBox);
        
        effortLoggerStage.setScene(new Scene(root, 1024, 768));
	}
	// Creates the title for the EffortLogger Main Page GUI
	private void createTitle() {
		// Title of current screen + formatting
		titleHBox = new HBox(); // This Horizontal Box holds the title.
		titleHBox.setAlignment(Pos.CENTER);
		title = new Label("Effort Logger Console"); // The title
		title.setAlignment(Pos.CENTER);
		title.setFont(new Font(30));
		title.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		titleHBox.getChildren().add(title);
	}
	// Create dropdown lists for the projects, lifecycle steps, effort categories, and deliverables
	private void createDropdownLists(User user) {
		defineCategoriesVBox = new VBox();
        
		// Add the instructions to the vertical box
        step1 = new Label("1. Select the project, life cycle step, effort category, and deliverable from the following lists: ");
        defineCategoriesVBox.getChildren().add(step1);
        
        // An HBox for holding all the dropdown lists
        categoriesHBox = new HBox();
        defineCategoriesVBox.getChildren().add(categoriesHBox);
        
        // Add the dropdown list for the projects
        projectOptions = FXCollections.observableArrayList();
        projectOptions.addAll(user.getListOfProjects());
        projectsComboBox = new ComboBox(projectOptions);
        projectsComboBox.setPromptText("Project");
        // Add a listener for when the user selects a project
        projectsComboBox.getSelectionModel().selectedItemProperty().addListener((categoryOptions, oldValue, newValue) -> {
	        // If the user has selected a project
        	if (projectsComboBox.getValue() != null) {
        		// Add the lifecycle steps associated with the project to the lifecycle steps list
	        	lifecycleStepOptions.clear();
	        	lifecycleStepOptions.addAll(user.getListOfLifecycleSteps(projectsComboBox.getSelectionModel().getSelectedIndex()));
	        	// Add the effort categories associated with the project to the dropdown list
	        	effortCategoryOptions.clear();
	        	effortCategoryOptions.addAll(user.getListOfEffortCategories(projectsComboBox.getSelectionModel().getSelectedIndex()));
	        	// Clear the deliverables box
	        	deliverableOptions.clear();
	        }
		});
        categoriesHBox.getChildren().add(projectsComboBox);
        
        // Add the dropdown list for the lifecycle steps
        lifecycleStepOptions = FXCollections.observableArrayList();
        lifecycleStepsComboBox = new ComboBox(lifecycleStepOptions);
        lifecycleStepsComboBox.setPromptText("Lifecycle Step");
        categoriesHBox.getChildren().add(lifecycleStepsComboBox);
        
        // Add the dropdown list for the effort categories
        effortCategoryOptions = FXCollections.observableArrayList();
        effortCategoriesComboBox = new ComboBox(effortCategoryOptions);
        effortCategoriesComboBox.setPromptText("Effort Category");
        effortCategoriesComboBox.getSelectionModel().selectedItemProperty().addListener((categoryOptions, oldValue, newValue) -> {
        	if (effortCategoriesComboBox.getValue() != null) {
        		deliverableOptions.clear();
        		deliverableOptions.addAll(user.getListOfDeliverablesForEffortCategory(projectsComboBox.getSelectionModel().getSelectedIndex(), effortCategoriesComboBox.getSelectionModel().getSelectedIndex()));
        	}
        });
        categoriesHBox.getChildren().add(effortCategoriesComboBox);
        
        // Add the dropdown list for the deliverables
        deliverableOptions = FXCollections.observableArrayList();
        deliverablesComboBox = new ComboBox(deliverableOptions);
        deliverablesComboBox.setPromptText("Deliverable");
        categoriesHBox.getChildren().add(deliverablesComboBox);
	}
	// Creates the timer and its functionalities
	private void createTimer(User user) {
		// Vertical Box for all the timer attributes
		timerVBox = new VBox();
		// Horizontal Box for the buttons
		timerHBox = new HBox();
		
		// Add instructions on how to start and stop an activity
		timerLabel = new Label("2. To start an activity, press the \"Start an activity\" button and to end an activity, press the \"Stop an activity\" button.");
		timerVBox.getChildren().add(timerLabel);
		
		// Add a task timer
		taskTimer = new TaskTimer();

		// Add the start button
		startActivity = new Button("Start an activity");
		startActivity.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
            	// Make sure a project, lifecycle step, effort category, and deliverable is selected
            	if (projectsComboBox.getValue() != null && lifecycleStepsComboBox.getValue() != null && effortCategoriesComboBox.getValue() != null && deliverablesComboBox.getValue() != null) {
            		System.out.println("Starting activity");
            		taskTimer.startTimer();
            		effortReport = new EffortReport((String)projectsComboBox.getValue(), (String)lifecycleStepsComboBox.getValue(), (String)effortCategoriesComboBox.getValue(), (String)deliverablesComboBox.getValue());
            	}
            }
        });
		timerHBox.getChildren().add(startActivity);
		
		// Add the stop button
		endActivity = new Button("Stop an activity");
		endActivity.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
            	if (taskTimer.hasTimerStarted() == true) {
            		System.out.println("Ending activity");
            		effortReport.addTaskLength(taskTimer.stopTimer());
            		user.addEffortReport(effortReport);
            		
            		// Generate Log
            		try {
            			user.generateLog();
            		} catch (IOException e) {
            			// TODO Auto-generated catch block
            			e.printStackTrace();
            		}
            	}
            }
        });
		timerHBox.getChildren().add(endActivity);
		
		timerVBox.getChildren().add(timerHBox);
		
	}
	// Adds buttons to help the user navigate to other sections of EffortLogger
	private void addOptions(Stage effortLoggerStage, User user) {
		// A horizontal box to hold all the buttons that navigate to other sections of EffortLogger
		optionsHBox = new HBox();
		
		// Add projects button that the user uses to switch to a scene where the user can add projects and items to those projects
		addProjectsButton = new Button("Add Projects");
		addProjectsButton.setOnAction(new EventHandler<>() { // Switches to another scene
			public void handle(ActionEvent event) {
            	System.out.println("Switching to adding projects scene.");
            	DefineProjectsGUI defineProjectsGUI = new DefineProjectsGUI();
            	defineProjectsGUI.GUI(effortLoggerStage, user);
            	effortLoggerStage.show();
            }
        });
		optionsHBox.getChildren().add(addProjectsButton);
		
		// Remove projects button that the user uses to switch to a scene where the user can remove projects and items from those projects
		removeProjectsButton = new Button("Remove Projects");
		removeProjectsButton.setOnAction(new EventHandler<>() { // Switches to another scene
			public void handle(ActionEvent event) {
            	System.out.println("Switching to removing projects scene.");
            	RemoveProjectsGUI removeProjectsGUI = new RemoveProjectsGUI();
            	removeProjectsGUI.GUI(effortLoggerStage, user);
            	effortLoggerStage.show();
            }
        });
		optionsHBox.getChildren().add(removeProjectsButton);

		// Add a button for the user to save their progress to a CSV file
		addSaveButton = new Button("Save your Progress");
		addSaveButton.setOnAction(new EventHandler<>() {
			public void handle(ActionEvent event) {
				System.out.println("Saving Progress");
				Save save = new Save();
				try {
					save.saveToCSV(user);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		optionsHBox.getChildren().add(addSaveButton);
		
		// Add a button that sends the user to a log in screen for the summary report screen
		addReportsButton = new Button("Reports");
		addReportsButton.setOnAction(new EventHandler<>() {
			public void handle(ActionEvent event) {
				System.out.println("Switching to reports screen");
				ReportsGUI.show(effortLoggerStage, user);
			}
		});
		optionsHBox.getChildren().add(addReportsButton);
		
		// Add a button that sends the user to a Planning Poker tool screen
		planningPokerButton = new Button("Planning Poker");
		planningPokerButton.setOnAction(new EventHandler<>() {
			public void handle(ActionEvent event) {
				System.out.println("Switching to planning poker screen");
				PlanningPokerGUI planningPokerGUI = new PlanningPokerGUI();
				planningPokerGUI.GUI(effortLoggerStage, user);
				effortLoggerStage.show();
			}
		});
		optionsHBox.getChildren().add(planningPokerButton);
		
		/*importPlanningPokerButton = new Button("Import Planning Poker");
		importPlanningPokerButton.setOnAction(new EventHandler<>() {
			public void handle(ActionEvent event) {
				System.out.println("Switching to import planning poker screen");
				importPP importPlanningPoker = new importPP();
				importPlanningPoker.start(effortLoggerStage);
				effortLoggerStage.show();
			}
		});
		optionsHBox.getChildren().add(importPlanningPokerButton);*/
	}
}
