package application;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import ImportFeature.Import;
import LoginPage.UserLoginGUI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class EffortLogger extends Application {
	public static void main(String[] args) {
		launch(args);
	}
	public void start(Stage primaryStage) {
		// Sets the title for EffortLogger Tool
		System.out.println("EffortLogger V2 has started");
        primaryStage.setTitle("EffortLogger V2");
        
        // Create the user
        User user = new User();
        
        // Import from csv 
        Import import1 = new Import();
        try {
			import1.importfromcsv(user);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		// After that, it will open the EffortLogger GUI where the user can create reports and the
		// main functionalities of the original EffortLogger.
        
        /*
          LOGIN PAGE STUFF
          
         CompletableFuture<Integer> resultFuture = new CompletableFuture<>();
        UserLoginGUI loginP = new UserLoginGUI();
		loginP.GUI(primaryStage, resultFuture);
		
		resultFuture.thenAccept(result -> {
			if(result == 1) {
				// After that, it will open the EffortLogger GUI where the user can create reports and the
				// main functionalities of the original EffortLogger.
				// Do code for effortlogger
				EffortLoggerGUI mainEffortLoggerGUI = new EffortLoggerGUI();
				mainEffortLoggerGUI.GUI(primaryStage, user);
				primaryStage.show();
			}
		});*/
        EffortLoggerGUI mainEffortLoggerGUI = new EffortLoggerGUI();
		mainEffortLoggerGUI.GUI(primaryStage, user);
		primaryStage.show();
	}
}
