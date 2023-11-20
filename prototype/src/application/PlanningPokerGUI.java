package application;

import java.util.ArrayList;

import PlanningPoker.PokerGUI;
import PlanningPokerEstimate.PlanningPokerEstimateGUI;
import UserStories.UserStory;
import UserStories.UserStoryCreateGUI;
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

// Main GUI for Planning Poker
public class PlanningPokerGUI {
	// Stored data to be used for Planning Poker
	
	// VBox for all the buttons
	private VBox planningPokerVBox;
	// Attributes for generating the estimate
	private HBox generateEstimateHBox;
	private Button generateEstimateGUIButton;
	// Attributes for a back button
	private Label backLabel;
	private Button backButton;
	/** Attributes for selecting a user story **/
	// Instruction label
	private Label userStoryLabel;
	// User story vertical box
	private VBox userStoryVBox;
	// A dropdown list for the list of user stories
	private ObservableList<String> userStoryOptions;
	private ComboBox<?> userStoryComboBox;
	
	public void GUI(Stage planningPokerStage, User user) {
		// Button for going back to EffortLogger
		backButton(planningPokerStage, user);
		// Buttons and stuff for adding historical projects
		// Buttons and stuff for adding user stories and displaying the current user story
		// Select a user story
		createUserStoryGUIElements();
		// Buttons and stuff for generating an estimate and editing it
		generateEstimateItems(planningPokerStage, user);
		// Buttons and stuff for choosing a planning poker card
		// Setting up the vertical box for the various horizontal boxes
		planningPokerVBox = new VBox();
		planningPokerVBox.getChildren().add(backLabel);
		planningPokerVBox.getChildren().add(backButton);
		planningPokerVBox.getChildren().add(userStoryVBox);
		planningPokerVBox.getChildren().add(generateEstimateHBox);
		
		// Set the stage for the Planning Poker GUI
		planningPokerStage.setScene(new Scene(planningPokerVBox, 1024, 768));
	}
	// Go back to EffortLogger screen
	private void backButton(Stage effortLoggerStage, User user) {
		backLabel = new Label("Go back to Effort Log Console");
		
		backButton = new Button("EffortLogger");
		backButton.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
            	System.out.println("Going back to Effort Log Console");
        		EffortLoggerGUI effortLoggerGUI = new EffortLoggerGUI();
        		effortLoggerGUI.GUI(effortLoggerStage, user);
        		effortLoggerStage.show();
            }
		});
	}
	// User story GUI elements
		private void createUserStoryGUIElements() {
			// Create a VBox for the user story elements
			userStoryVBox = new VBox();
			
			// Label for the user story instructions
			userStoryLabel = new Label("Select a user story");
			userStoryVBox.getChildren().add(userStoryLabel);
			
			// Create the dropdown list for the user stories
			userStoryOptions = FXCollections.observableArrayList();
			ArrayList<String> userStoryNames = new ArrayList<>();
			for (int i = 0; i < UserStory.stories.size(); i++) {
				userStoryNames.add(UserStory.stories.get(i).story);
			}
			userStoryOptions.addAll(userStoryNames);
			userStoryComboBox = new ComboBox(userStoryOptions);
			userStoryVBox.getChildren().add(userStoryComboBox);
			
		}
	// Go to the screen where the user can generate an estimate 
	private void generateEstimateItems(Stage planningPokerEstimateStage, User user) {
		generateEstimateHBox = new HBox();
		generateEstimateGUIButton = new Button("Story Point Estimate");
		generateEstimateGUIButton.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
            	if (userStoryComboBox.getSelectionModel().getSelectedItem() != null) {
	            	System.out.println("Switching to planning poker estimate screen");
	            	PlanningPokerEstimateGUI planningPokerEstimateGUI = new PlanningPokerEstimateGUI();
					planningPokerEstimateGUI.GUI(planningPokerEstimateStage, user, UserStory.stories.get(userStoryComboBox.getSelectionModel().getSelectedIndex()));
					planningPokerEstimateStage.show();
            	}
            }
		});
		generateEstimateHBox.getChildren().add(generateEstimateGUIButton);
		
	    // For the Poker Table
	    Button pokerTableButton = new Button("Poker Table");
	    pokerTableButton.setOnAction(new EventHandler<>() {
	        public void handle(ActionEvent event) {
	            // Add your logic for handling the pokerTable button click
	            System.out.println("Switching to poker table screen");
	            PokerGUI poker = new PokerGUI();
	            poker.GUI(planningPokerEstimateStage);
	            
	        }
	    });
	    generateEstimateHBox.getChildren().add(pokerTableButton);
		
		Button createUserStory = new Button("Create User Story");
		createUserStory.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
            	System.out.println("Switching to Create user story screen");
            	UserStoryCreateGUI.show(planningPokerEstimateStage, user);
            }
		});
		generateEstimateHBox.getChildren().add(createUserStory);
	}
}
