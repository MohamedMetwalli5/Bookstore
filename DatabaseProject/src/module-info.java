module DatabaseProject {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.base;
	requires javafx.graphics;
	
	requires java.sql;
	requires mysql.connector.java;
	requires java.base;
	requires jasperreports;

	opens application to javafx.graphics, javafx.fxml;
	opens application.dbManagement to java.sql, mysql.connector.java, jasperreports;
	opens application.entities to javafx.base;
}
