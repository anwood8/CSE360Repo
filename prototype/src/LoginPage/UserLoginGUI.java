package LoginPage;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class UserLoginGUI {
	// for title
	private HBox titleHBox;
	private Label title;
	
	// username/password 
	private HBox userBox;
	private Label userLabel;
	private TextField userText;
	
	private HBox passBox;
	private Label passLabel;
	private TextField passText;
	
	// button
	private VBox buttonBox;
	private Button loginButton;
	private Label attemptLabel;
	
	
	public void GUI(Stage loginPage, CompletableFuture<Integer> rNum) {
		
        // Create the title
		loginPage.setTitle("Login");
        createTitle();
        createUserInfo();
        createPassInfo();
        createButton();
       
        
        // Create a vertical box/scene
        VBox root = new VBox();
        root.getChildren().add(titleHBox);
        root.getChildren().add(userBox);
        root.getChildren().add(passBox);
        root.getChildren().add(buttonBox);
        loginPage.setScene(new Scene(root, 512, 339));
        
        // call Logic when button pressed
        loginButton.setOnAction(event -> {
        	String uName = userText.getText();
        	String pWord = passText.getText();
        	Logic logic = new Logic();
        	
        	int result;
			result = logic.verify(uName, pWord);
			
        	
        	if(result == 1) {
        		attemptLabel.setText("Login Successful");
        		rNum.complete(result);
        	}else {
        		attemptLabel.setText("Invalid Login Attempt");
        	}
        	
        });

        
        loginPage.show(); 
	}
		
	// for title
	private void createTitle() {
		titleHBox = new HBox();
		title = new Label("Welcome"); // The title
		titleHBox.setAlignment(Pos.CENTER);
		title.setAlignment(Pos.CENTER);
		title.setFont(new Font(30));
		titleHBox.getChildren().addAll(title);
	}
	
	
	// for username 
	private void createUserInfo() {
		userBox = new HBox();
		userLabel = new Label("Username:	"); // The title
		userText = new TextField();
		userBox.setAlignment(Pos.CENTER_LEFT);
		userLabel.setAlignment(Pos.CENTER);
		userLabel.setFont(new Font(15));
		userText.setAlignment(Pos.CENTER_RIGHT);
		userBox.getChildren().addAll(userLabel, userText);
		userBox.setPadding(new Insets(40,10,10,100));
		
	}
	
	// for password
	private void createPassInfo() {
		passBox = new HBox();
		passLabel = new Label("Password: 	"); // The title
		passText = new TextField();
		passBox.setAlignment(Pos.CENTER_LEFT);
		passLabel.setAlignment(Pos.CENTER);
		passLabel.setFont(new Font(15));
		passText.setAlignment(Pos.CENTER_RIGHT);
		passBox.getChildren().addAll(passLabel,passText);
		passBox.setPadding(new Insets(10,10,10,100));
	}
	
	public void createButton() {
		buttonBox = new VBox();
		buttonBox.setAlignment(Pos.CENTER);
		attemptLabel = new Label();
		attemptLabel.setText("test");
		loginButton = new Button();
	    loginButton.setText("Login");
	    buttonBox.getChildren().add(loginButton);
	    buttonBox.getChildren().add(attemptLabel);
	    buttonBox.setPadding(new Insets(10,20,10,10));
	}
	
}
		
	


