package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.dbManagement.BookManager;
import application.entities.Book;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.*;

public class ViewCartPageController implements Initializable{

    @FXML
    private Text totalCostText;
    @FXML
    private TextField BookISBNTextField;
    
    @FXML 
    private Button BackButton, RemoveFromCartButton, CheckOutButton;
/*
    @FXML
	private TableView<Book> cartTable;
	@FXML
    private TableColumn<Book, String> ISBNColumn;
	@FXML
    private TableColumn<Book, String> TitleColumn;
	@FXML
    private TableColumn<Book, Integer> QuantityColumn;
    @FXML
    private TableColumn<Book, String> BookPriceColumn;
	@FXML
    private TableColumn<Book, String> TotalPriceColumn;

*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
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
    public void CheckOut(){
    	
    }

    @FXML
    public void RemoveFromCart(MouseEvent mouseEvent){
    	BookManager bm = Main.db.getBookManager();
    	try {
    		if(this.BookISBNTextField.getText() != null && this.BookISBNTextField.getText().length() > 0) {
    			bm.removeFromCart(Main.TheUserName, this.BookISBNTextField.getText());	
    		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

}
