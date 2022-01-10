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
	private Button SaveButton, SignOut;
	
	@FXML
	private TextField NewUserName, NewEmail, NewPassword, ConfirmNewPassword, NewPhoneNumber, NewShippingAddress;
	
	@FXML
	private Label TheUnequalPasswordLabel;
	
	@FXML
	private void Save(MouseEvent mouseEvent) {
		
		String NewUserName = this.NewUserName.getText(), NewEmail = this.NewEmail.getText(), NewPassword = this.NewPassword.getText(), ConfirmNewPassword = this.ConfirmNewPassword.getText(), NewPhoneNumber = this.NewPhoneNumber.getText(), NewShippingAddress = this.NewShippingAddress.getText();
		//comparing the new values with the empty string value and the old values
		if(!NewPassword.equals(ConfirmNewPassword)) {
			this.TheUnequalPasswordLabel.setText("Confirmation password doesn't equal the new password");
		}else {
			this.TheUnequalPasswordLabel.setText("");
			UserManager um = Main.db.getUserManager();
			User u = new User();
			u.setUserName(NewUserName);
			u.setEmail(NewEmail);
			u.setPassword(NewPassword);
			u.setPhone(NewPhoneNumber);
			u.setShipAddress(NewShippingAddress);
			try {
				um.updateUser(u);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
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
