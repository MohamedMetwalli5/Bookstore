package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import application.dbManagement.BookManager;
import application.dbManagement.UserManager;
import application.entities.Book;
import application.entities.CartItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.*;

public class ViewCartPageController implements Initializable{

    @FXML
    private Text TotalCostText;
    @FXML
    private TextField BookISBNTextField;
    
    @FXML 
    private Button BackButton, RemoveFromCartButton, CheckOutButton;

    @FXML
	private TableView<CartItem> CartTable;

    @FXML
    private TableColumn<CartItem, String> ISBNColumn;
	
    @FXML
    private TableColumn<CartItem, String> TitleColumn;
	
    @FXML
    private TableColumn<CartItem, Integer> QuantityColumn;
    
    @FXML
    private TableColumn<CartItem, Double> BookPriceColumn;
	
    @FXML
    private TableColumn<CartItem, Double> TotalPriceColumn;

    ObservableList<CartItem> GetCartItems(List<CartItem> CartItemsList){
        ObservableList<CartItem> ObservableCartItemsList = FXCollections.observableArrayList();
        for(int i=0;i<CartItemsList.size();i++) {
        	ObservableCartItemsList.add(CartItemsList.get(i));
        }
        return ObservableCartItemsList;
    }
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		ISBNColumn.setCellValueFactory(new PropertyValueFactory<CartItem, String>("isbn"));
		TitleColumn.setCellValueFactory(new PropertyValueFactory<CartItem, String>("title"));
		QuantityColumn.setCellValueFactory(new PropertyValueFactory<CartItem, Integer>("quantity"));
		BookPriceColumn.setCellValueFactory(new PropertyValueFactory<CartItem, Double>("bookPrice"));
		TotalPriceColumn.setCellValueFactory(new PropertyValueFactory<CartItem, Double>("totalPrice"));
		
		UserManager um = Main.db.getUserManager();
		try {
			List<CartItem> lc = um.getUserCart(Main.TheUserName);
			CartTable.setItems(GetCartItems(lc));
			Double TotalPrice = 0.0;
			for(int i=0;i<lc.size();i++) {
				TotalPrice += lc.get(0).getTotalPrice();
			}
			this.TotalCostText.setText(String.valueOf(TotalPrice));
		} catch (SQLException e) {
			e.printStackTrace();
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
    public void CheckOut(MouseEvent mouseEvent){
        UserManager um = Main.db.getUserManager();
        List<CartItem> lc = null;
		try {
			lc = um.getUserCart(Main.TheUserName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        BookManager bm2 = Main.db.getBookManager();
        try {
			bm2.checkOut(Main.TheUserName, lc);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    } 

    @FXML
    public void RemoveFromCart(MouseEvent mouseEvent){
    	BookManager bm = Main.db.getBookManager();
    	try {
    		if(this.BookISBNTextField.getText() != null && this.BookISBNTextField.getText().length() > 0) {
    			bm.removeFromCart(Main.TheUserName, this.BookISBNTextField.getText());	
    			CartTable.setItems(GetCartItems(Main.db.getUserManager().getUserCart(Main.TheUserName)));
    		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

}
