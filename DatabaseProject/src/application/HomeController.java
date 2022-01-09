package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class HomeController {
	
	@FXML
	private Button SignUpButton, SignInButton;
	
	private void SignUp(MouseEvent mouseEvent) throws IOException {
		Main m = new Main();
		m.changeScene("SignUpForm.fxml");
	}
	
	private void SignIn(MouseEvent mouseEvent) throws IOException {
		Main m = new Main();
		m.changeScene("SignInForm.fxml");
	}
	
}
