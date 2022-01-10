module DatabaseProject {
	requires javafx.controls;
	requires javafx.fxml;
	
	requires javafx.graphics;
	
	requires java.sql;
	requires mysql.connector.java;
	requires java.base;
	requires jasperreports;
	requires javafx.base;

	opens application to javafx.graphics, javafx.fxml;
	opens application.dbManagement to java.sql, mysql.connector.java, jasperreports;
	opens application.entities to javafx.base;
}
