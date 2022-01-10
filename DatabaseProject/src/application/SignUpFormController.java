package application;

import java.io.IOException;
import java.sql.SQLException;

import application.dbManagement.UserManager;
import application.entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class SignUpFormController {
	
	@FXML
	private Button SignUpButton, BackButton;
	
	@FXML
	private TextField FirstName, LastName, UserName, Email, Password, PhoneNumber, ShippingAddress;
	
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
	private void SignUp(MouseEvent mouseEvent) {
		
		String FirstName = this.FirstName.getText(), LastName = this.LastName.getText(), UserName = this.UserName.getText(), Email = this.Email.getText(), Password = this.Password.getText(), PhoneNumber = this.PhoneNumber.getText(), ShippingAddress = this.ShippingAddress.getText();
		UserManager um = Main.db.getUserManager();
		User u = new User();
		u.setfName(FirstName);
		u.setlName(LastName);
		u.setUserName(UserName);
		u.setPassword(Password);
		u.setEmail(Email);
		u.setPhone(PhoneNumber);
		u.setShipAddress(ShippingAddress);
		
		Main.TheUserName = UserName;
		try {
			um.addUser(u);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Main m = new Main();
		try {
			m.changeScene("HomePage.fxml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
