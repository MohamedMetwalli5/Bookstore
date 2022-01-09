package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class ProfilePageController {

	@FXML
	private Button SaveButton, SignOut;
	
	@FXML
	private void Save(MouseEvent mouseEvent) {
		String NewUserName, NewEmail, NewPassword, ConfirmNewPassword, NewPhoneNumber, NewShippingAddress;
		//comparing the new values with the empty string value and the old values
		
		//save in the data base the information
	}
	
	@FXML
	private void SignOut(MouseEvent mouseEvent) throws IOException {
		Main m = new Main();
		m.changeScene("Home.fxml");
	}
}
