package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class SignUpFormController {
	
	@FXML
	private Button SignUpButton;
	
	@FXML
	private void SignUp(MouseEvent mouseEvent) throws IOException {
		Main m = new Main();
		m.changeScene("HomePage.fxml");
	}
}
