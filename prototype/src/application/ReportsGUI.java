package application;

import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ReportsGUI {
	public static void show(Stage stage, User user) {
		// Setup grid
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		// Build grid
		Text title = new Text("Login Required");
		grid.add(title, 0, 0);
		
		Label usernameLabel = new Label("Username: ");
		grid.add(usernameLabel, 0, 1);
		
		TextField usernameField = new TextField();
		grid.add(usernameField, 1, 1);
		
		Label passwordLabel = new Label("Password:");
		grid.add(passwordLabel, 0, 2);
		
		PasswordField passwordField = new PasswordField();
		grid.add(passwordField, 1, 2);
		
		Button backBtn = new Button("Back");
		backBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				EffortLoggerGUI effortLoggerGUI = new EffortLoggerGUI();
        		effortLoggerGUI.GUI(stage, user);
			}
		});
		grid.add(backBtn, 0, 3);
		
		Button loginBtn = new Button("Log in");
		grid.add(loginBtn, 1, 3);
		
		Text status = new Text();
		grid.add(status, 0, 4);
		
		loginBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				String enteredUsername = usernameField.getText();
				String enteredPassword = passwordField.getText();
				if(enteredUsername.equals("supervisor") && enteredPassword.equals("password")) {
					enter(stage, user);
				}
				else {
					status.setText("Incorrect login.");
				}
			}
		});
		
		// Setup scene
		Scene scene = new Scene(grid, 300, 300);
		stage.setScene(scene);
	}
	private static void enter(Stage stage, User user) {
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(25, 25, 25, 25));
		Text t1 = new Text("Welcome, supervisor!");
		grid.add(t1, 0, 0);
		Text t2 = new Text("This is a restricted interface and contains confidential information.");
		grid.add(t2, 0, 1);
		Button backBtn = new Button("Logout");
		backBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				EffortLoggerGUI effortLoggerGUI = new EffortLoggerGUI();
        		effortLoggerGUI.GUI(stage, user);
			}
		});
		grid.add(backBtn, 0, 3);
		
		// Setup scene
		Scene scene = new Scene(grid, 1280, 720);
		stage.setScene(scene);
	}
}
