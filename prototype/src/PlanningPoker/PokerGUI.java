package PlanningPoker;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import com.opencsv.CSVWriter;

import PlanningPokerEstimate.PlanningPokerEstimateGUI;
import UserStories.UserStory;
import LoginPage.UserLoginGUI;
import PlanningPoker.PokerGUI;
import application.PlanningPokerGUI;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class PokerGUI {
	Label displayLabel = new Label("");
	private VBox buttonBox;
	private final int numButtons = 12;
	private Button[] pokerButtons = new Button[numButtons];
	
	private ObservableList<String> userStoryOptions;
	private ComboBox<?> stories;

	
	public void GUI(Stage pokerPage) {	
	 // Create the title
	pokerPage.setTitle("poker");
    createButton();
   
    BorderPane root = new BorderPane();
    root.setBottom(buttonBox);
    BorderPane.setAlignment(buttonBox, Pos.CENTER);
    BorderPane.setMargin(buttonBox, new Insets(10,10,10,160));  // Optional: Add margin for better spacing
    
    // Create a text field    
    userStoryOptions = FXCollections.observableArrayList();
	ArrayList<String> userStoryNames = new ArrayList<>();
	for (int i = 0; i < UserStory.stories.size(); i++) {
		userStoryNames.add(UserStory.stories.get(i).story);
	}
	userStoryOptions.addAll(userStoryNames);
	stories = new ComboBox(userStoryOptions);
    stories.setPrefWidth(150);
    stories.setPrefHeight(25);

    Button exitButton = new Button("Exit");
    exitButton.setOnAction(event -> {
    	PlanningPokerGUI back = new PlanningPokerGUI();
    	back.GUI(pokerPage, null);
    });
 
    
    HBox textFieldHBox = new HBox(stories, exitButton);
    textFieldHBox.setAlignment(Pos.TOP_LEFT); // Align to the top left
    textFieldHBox.setSpacing(10);

    root.setTop(textFieldHBox);

    pokerPage.setScene(new Scene(root, 1024, 678));
    
    	for (int i = 0; i < numButtons; i++) {
            int buttonIndex = i;
            
            // Add event handler for each button
            pokerButtons[i].setOnAction(event -> handleButtonClick(buttonIndex));
        }
    	
    	
    	pokerButtons[0].setText("-1");
    	pokerButtons[1].setText("0");
    	pokerButtons[2].setText("1/2");
    	pokerButtons[3].setText("1");
    	pokerButtons[4].setText("3");
    	pokerButtons[5].setText("5");
    	pokerButtons[6].setText("8");
    	pokerButtons[7].setText("13");
    	pokerButtons[8].setText("20");
    	pokerButtons[9].setText("40");
    	pokerButtons[10].setText("100");
    	pokerButtons[11].setText("INF");
    	
    	
    pokerPage.setResizable(false); // Set the stage to be non-resizable

    pokerPage.show();
    
    
   
}

// for login button
public void createButton() {
	buttonBox = new VBox();
	buttonBox.setAlignment(Pos.CENTER);
	buttonBox.setSpacing(10);
	
	HBox hbox1 = new HBox(20);
    HBox hbox2 = new HBox(20);
    
    
    displayLabel.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #000000; -fx-padding: 10px;");
    displayLabel.setPrefSize(120, 160);
    displayLabel.setAlignment(Pos.CENTER); // Align text to the center
    displayLabel.setFont(Font.font("Arial", 50));
    
    Button subButton = new Button("Submit Estimate");
    subButton.setPrefSize(125, 25);
    subButton.setOnAction(event -> handleSaveButtonClick());
    
    
    VBox labelVBox = new VBox(displayLabel, subButton);
    labelVBox.setAlignment(Pos.TOP_CENTER); // Align to the top center
    labelVBox.setPadding(new Insets(-75, 125, 100, 0));
    buttonBox.getChildren().addAll(labelVBox);
    
    

    for (int i = 0; i < numButtons; i++) {
        pokerButtons[i] = new Button();
        pokerButtons[i].setPrefSize(100, 160);

        // Add buttons to the corresponding HBox
        if (i < 6) {
            hbox1.getChildren().add(pokerButtons[i]);
        } else {
            hbox2.getChildren().add(pokerButtons[i]);
        }
    }
    
    buttonBox.getChildren().addAll(hbox1, hbox2);
	buttonBox.setPadding(new Insets(10,20,20,10));
    
}

private void handleButtonClick(int buttonIndex) {
	displayLabel.setText("" + pokerButtons[buttonIndex].getText());

}

private void handleSaveButtonClick() {
    // Retrieve relevant information
    
    if (stories != null && !"".equals(displayLabel.getText())) {
    	String selectedUserStory = stories.getSelectionModel().getSelectedItem().toString();
    	String cardWeight = displayLabel.getText(); // Assuming displayLabel is where the card weight is displayed
        
    	// Create a new row of data
    	int nextIndex = countRowsInCSV("UserData/pokerCards.csv");
    	System.out.println("Number of rows in CSV file: " + nextIndex);
        String[] data = {String.valueOf(nextIndex), selectedUserStory, cardWeight};

        try {
        
        FileWriter outputFile = new FileWriter("UserData/pokerCards.csv", true);
		
        // Initialize a CSVWriter that will write to the FileWriter object to write to save.csv in csv format
     	CSVWriter writer = new CSVWriter(outputFile);
		
		writer.writeNext(data);
		writer.close();

        System.out.println("Data saved to CSV file.");
        
        }
		catch(IOException e) {
			e.printStackTrace();
		}
    }

    
}

private int countRowsInCSV(String filePath) {
    int rowCount = 0;
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        while (br.readLine() != null) {
            rowCount++;
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return rowCount;
}
	
	
}
