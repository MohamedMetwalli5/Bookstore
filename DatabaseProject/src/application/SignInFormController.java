package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class SignInFormController {
	
	@FXML
	private Button SignInButton;
	
	@FXML
	private void SignIn(MouseEvent mouseEvent) throws IOException {
		Main m = new Main();
		m.changeScene("HomePage.fxml");
	}
}
