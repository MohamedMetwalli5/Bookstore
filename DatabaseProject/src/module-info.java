module DatabaseProject {
	requires javafx.controls;
	requires javafx.fxml;
	
	requires javafx.graphics;
	requires java.sql;
//	requires mysql.connector.java;
	
	opens application to javafx.graphics, javafx.fxml;
}
