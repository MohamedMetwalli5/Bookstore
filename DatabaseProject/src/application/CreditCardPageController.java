package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class CreditCardPageController {

	@FXML
	private Button SubmitButton;
	
	@FXML
	private Label WrongInformationLabel;
	
	@FXML
	private Label ExpirationDateLabel;
	
	@FXML
	private Button SignOutButton;
	
	@FXML
	private void Submit(MouseEvent mouseEvent) throws IOException {
		// check for the correctness of the information
//		if() {
			Main m = new Main();
			m.changeScene("HomePage.fxml");	
//		}else{
			
//		}
	}
	
	@FXML
	private void SignOut(MouseEvent mouseEvent) throws IOException {
		Main m = new Main();
		m.changeScene("Home.fxml");	
	}
}
