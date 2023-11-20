package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class HistoricalData {
	private ArrayList<ProjectData> projects = new ArrayList<ProjectData>();
	
	public void importFromLog(ProjectData historicalProject) {
		String file = "UserData/log.csv";
		BufferedReader reader = null;
		try {
            reader = new BufferedReader(new FileReader(file));
            String line;

            // Read each line from the CSV file
            while ((line = reader.readLine()) != null) {
                // Split the line into parts using the comma as a separator
                String[] parts = line.split(", ");

                // Check if the array has enough elements
                if (parts.length == 5) {
                    // Extract data from the parts
                    String projectName = parts[0];
                    String step = parts[1];
                    String category = parts[2];
                    String deliverable = parts[3];
                    String timeElapsed = parts[4];

                    // Check if the project already exists in the projects list
                    ProjectData existingProject = findProject(projectName);

                    if (existingProject != null) {
                        // If the project already exists, add data to the existing project
                        existingProject.addProjectData(step + ", " + category + ", " + deliverable + ", " + timeElapsed);
                    } else {
                        // If the project doesn't exist, create a new ProjectData object
                        ProjectData newProject = new ProjectData(projectName);
                        newProject.addProjectData(step + ", " + category + ", " + deliverable + ", " + timeElapsed);
                        projects.add(newProject);
                    }
                } else {
                    // Handle the case where the format is incorrect
                    System.out.println("Invalid data format: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Helper method to find a project by name in the projects list
    private ProjectData findProject(String projectName) {
        for (ProjectData project : projects) {
            if (project.getName().equals(projectName)) {
                return project;
            }
        }
        return null;
    }
}

