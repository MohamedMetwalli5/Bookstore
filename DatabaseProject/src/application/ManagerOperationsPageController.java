package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ManagerOperationsPageController {
	
	@FXML
	private Button SignOutButton, SaveAddBookButton, SaveModifyBookButton, OrderBooksButton, SearchBookButton, AddManagerButton;
	
	@FXML
	private TextField AddBookISBNNumberTextField, AddBookTitleTextField, AddBookPublicationYearTextField, AddBookSellingPriceTextField;

	@FXML
	private TextField ModifyBookISBNNumberTextField, ModifyBookNewQuantityTextField;
	
	@FXML
	private TextField OrderBooksISBNNumberTextField, OrderBooksNewQuantityTextField;

	@FXML
	private TextField UserNameTextField;
	
	@FXML
	private void SignOut(MouseEvent mouseEvent) throws IOException {
		Main m = new Main();
		m.changeScene("Home.fxml");	
	}
	
	@FXML
	private void SaveAddBook(MouseEvent mouseEvent) throws IOException {
			
	}
	
	@FXML
	private void SaveModifyBook(MouseEvent mouseEvent) throws IOException {
		
	}
	
	@FXML
	private void SaveOrderBooks(MouseEvent mouseEvent) throws IOException {
		
	}
	
	@FXML
	private void SearchBook(MouseEvent mouseEvent) throws IOException {
		
	}
	
	@FXML
	private void AddManager(MouseEvent mouseEvent) throws IOException {
		
	}
}
