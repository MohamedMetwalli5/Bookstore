package application;

import java.io.IOException;
import java.sql.SQLException;

import application.dbManagement.UserManager;
import application.entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ProfilePageController {

	@FXML
	private Button SaveButton, SignOut, BackButton;
	
	@FXML
	private TextField NewEmail, NewPassword, ConfirmNewPassword, NewPhoneNumber, NewShippingAddress;
	
	@FXML
	private Label TheUnequalPasswordLabel;
	
	@FXML
	private void Save(MouseEvent mouseEvent) {
		
		String  NewEmail = this.NewEmail.getText(), NewPassword = this.NewPassword.getText(), ConfirmNewPassword = this.ConfirmNewPassword.getText(), NewPhoneNumber = this.NewPhoneNumber.getText(), NewShippingAddress = this.NewShippingAddress.getText();
		//comparing the new values with the empty string value and the old values
		if(!NewPassword.equals(ConfirmNewPassword) && NewPassword.length() > 0) {
			this.TheUnequalPasswordLabel.setText("Confirmation password doesn't equal the new password");
		}else {
			this.TheUnequalPasswordLabel.setText("");
			UserManager um = Main.db.getUserManager();
			User u;
			try {
				u = um.getUser(Main.TheUserName);
			if(NewEmail != null && NewEmail.length() != 0) {
				u.setEmail(NewEmail);	
			}
			if(NewPassword != null && NewPassword.length() != 0) {
				u.setPassword(NewPassword);
			}
			if(NewPhoneNumber != null && NewPhoneNumber.length() != 0) {
				u.setPhone(NewPhoneNumber);
			}
			if(NewShippingAddress != null && NewShippingAddress.length() != 0) {
				u.setShipAddress(NewShippingAddress);
			}
			um.updateUser(u);
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}
		
	}
	
	@FXML
	private void GetPreviousScene(MouseEvent mouseEvent) {
		Main m = new Main();
		try {
			m.changeScene("HomePage.fxml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void SignOut(MouseEvent mouseEvent) throws IOException, SQLException {
		
		UserManager um = Main.db.getUserManager();
		um.signOut(Main.TheUserName); // TheUserName is a global variable that is set to the user name when the user signs up or signs in
		
		Main m = new Main();
		m.changeScene("Home.fxml");
	}
}
