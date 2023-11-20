package SaveFeature;
import application.ProjectRelatedCategories;
import application.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVWriter;
//Written by Colton Hart

public class Save {
	public void saveToCSV(User user) throws IOException {
		ArrayList<String> projectList = user.getListOfProjects();
		try {
			// Initialize a FileWriter object to write to save.csv
			FileWriter outputFile = new FileWriter("UserData/save.csv");
			
			// Initialize a CSVWriter that will write to the FileWriter object to write to save.csv in csv format
			CSVWriter writer = new CSVWriter(outputFile);
			
			// Create the header with pertinent information relating to EffortLogger
			String header[] = {"projectName", "listOfLifecycleSteps", "Plans", "Deliverables", "Interruptions", "Defects", "Others"};
			
			//Write the header to the CSV file
			writer.writeNext(header);
			
			//For each project, get the name of the project and the necessary columns for the table
			for(int i=0;i<projectList.size();i++) {
				String[] data = {
						projectList.get(i),
						
						//Save Project (Name)
						user.getListOfLifecycleSteps(i).toString(),
						
						//Save Deliverables and Categories
						user.getListOfDeliverablesForEffortCategory(i, 0).toString(),
						user.getListOfDeliverablesForEffortCategory(i, 1).toString(),
						user.getListOfDeliverablesForEffortCategory(i, 2).toString(),
						user.getListOfDeliverablesForEffortCategory(i, 3).toString(),
						user.getListOfDeliverablesForEffortCategory(i, 4).toString(),
				};
				writer.writeNext(data);
			}
			//Close the CSVWriter and the FileWriter objects
			writer.close();
			
		//This prototype creates a csv file with the save information of the user that is logged in
				
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
