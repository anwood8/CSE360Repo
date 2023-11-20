/**
 * 
 */
/**
 * 
 */
module CSE360Th63 {
	requires javafx.graphics;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.base;
	requires com.opencsv;
	
	opens application to javafx.graphics;
	opens LoginPage to javafx.graphics;
}