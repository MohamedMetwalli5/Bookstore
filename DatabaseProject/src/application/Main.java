package application;
	
import java.io.IOException;

import application.dbManagement.DBManager;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	private static Stage stg;
	
	public static DBManager db;
	public static String TheUserName = "";
	public static String TheTotalPriceLabel = "0 $";
	
	@Override
	public void start(Stage primaryStage) {
		try {
			stg = primaryStage;
			db = new DBManager();
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Home.fxml"));
			Scene scene = new Scene(root);
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
//			primaryStage.setResizable(false);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void changeScene(String fxml) throws IOException{
		Parent pane = FXMLLoader.load(getClass().getResource(fxml));
		stg.getScene().setRoot(pane);
	}
	
	public static void main(String[] args) {
		
		launch(args);
	}
}
