package UserStories;

import application.PlanningPokerGUI;
import application.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class UserStoryCreateGUI {
	public static void show(Stage stage, User user) {
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10));
		
		Button backBtn = new Button("Back");
		vbox.getChildren().add(backBtn);
		backBtn.setOnAction(new EventHandler<>() {
			public void handle(ActionEvent event) {
				System.out.println("Switching to planning poker screen");
				PlanningPokerGUI planningPokerGUI = new PlanningPokerGUI();
				planningPokerGUI.GUI(stage, user);
			}
		});
		
		Label storyLabel = new Label("Story");
		vbox.getChildren().add(storyLabel);
		
		TextField storyField = new TextField();
		vbox.getChildren().add(storyField);
		
		
		Label keywordsLabel = new Label("Keywords, separated by commas");
		vbox.getChildren().add(keywordsLabel);
		
		TextField keywordsField = new TextField();
		vbox.getChildren().add(keywordsField);
		
		HBox buttons = new HBox();
		
		Button createBtn = new Button("Save Story");
		buttons.getChildren().add(createBtn);
		createBtn.setOnAction(new EventHandler<>() {
			public void handle(ActionEvent event) {
				System.out.println("Adding story");
				UserStory story = new UserStory(storyField.getText(), keywordsField.getText());
				UserStory.stories.add(story);
				createBtn.setText("Added");
			}
		});
		
		vbox.getChildren().add(buttons);
		
		stage.setScene(new Scene(vbox, 1280, 720));
	}
}
