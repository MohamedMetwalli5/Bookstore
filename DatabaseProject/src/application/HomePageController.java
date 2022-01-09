package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class HomePageController {
	
	@FXML
	private Button SignOutButton, EditProfileButton, SearchButton, ViewCartButton, CheckOutButton;
	
	@FXML
	private Label TotalPriceLabel;
	
	@FXML
	private TextField InsertTextField;
	
	@FXML
	private RadioButton ISBNRadioButton, TitleRadioButton, AuthorRadioButton, PublisherRadioButton, PublicationYearRadioButton, SellingPriceRadioButton, CategoryRadioButton;
	
	private static String SearchType = "Title";
	
	@FXML
	private void SignOut(MouseEvent mouseEvent) throws IOException {
		Main m = new Main();
		m.changeScene("Home.fxml");	
	}
	
	@FXML
	private void EditProfile(MouseEvent mouseEvent) throws IOException {
		Main m = new Main();
		m.changeScene("ProfilePage.fxml");	
	}
	
	@FXML
	private void SelectSearchType(ActionEvent event) {
		if(ISBNRadioButton.isSelected()) {
			SearchType = "ISBN";
		}else if(TitleRadioButton.isSelected()) {
			SearchType = "Title";
		}else if(AuthorRadioButton.isSelected()) {
			SearchType = "Author";
		}else if(PublisherRadioButton.isSelected()) {
			SearchType = "Publisher";
		}else if(PublicationYearRadioButton.isSelected()) {
			SearchType = "PublicationYear";
		}else if(SellingPriceRadioButton.isSelected()) {
			SearchType = "SellingPrice";
		}else if(CategoryRadioButton.isSelected()) {
			SearchType = "Category";
		}
	}
	
	@FXML
	private void Search(MouseEvent mouseEvent) throws IOException {
		// searching in the data base and display what the user wanted
		String SearchingText = InsertTextField.getText();
		
		
		//search in the data base for what the user wanted
		
	}
	
	@FXML
	private void ViewCart(MouseEvent mouseEvent) throws IOException {
		Main m = new Main();
		m.changeScene("ViewCart.fxml");	
	}
	
	@FXML
	private void CheckOut(MouseEvent mouseEvent) throws IOException {
		// Change the total price label and make it equals zero
		
		// 
		
		Main m = new Main();
		m.changeScene("Home.fxml"); // to make the user sign out	
	}
}
