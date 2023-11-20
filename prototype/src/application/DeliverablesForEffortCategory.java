package application;

import java.util.ArrayList;

public class DeliverablesForEffortCategory {
	// Attributes of the effort category
	private String effortCategoryName;
	private ArrayList<String> listForEffortCategory;
	
	// Constructor
	public DeliverablesForEffortCategory(String effortCategoryName) {
		this.effortCategoryName = effortCategoryName;
		this.listForEffortCategory = new ArrayList<String>();
	}
	
	// Setters
	// Add a deliverable to the given effort category
	public void addItemToEffortCategory(String item) {
		this.listForEffortCategory.add(item);
	}
	
	// Getters
	// Get the name of the effort category
	public String getCategoryName() {
		return effortCategoryName;
	}
	// Get the list of deliverables for the given effort category
	public ArrayList<String> getListForEffortCategory() {
		return listForEffortCategory;
	}
}
