package application;

import java.io.IOException;
import java.sql.SQLException;

import application.dbManagement.UserManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class SignInFormController {
	
	@FXML
	private Button SignInButton, BackButton;
	
	@FXML
	private TextField UserName;

	@FXML
	private PasswordField Password;
	
	@FXML
	private void GetPreviousScene(MouseEvent mouseEvent) {
		Main m = new Main();
		try {
			m.changeScene("Home.fxml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void SignIn(MouseEvent mouseEvent) {
		
		UserManager um = Main.db.getUserManager();
		try {
			String UserName = this.UserName.getText(), Password = this.Password.getText();
			Main.TheUserName = UserName;
			boolean check = um.signIn(UserName, Password);
			if(check) {
				Main m = new Main();
				try {
					m.changeScene("HomePage.fxml");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
}
