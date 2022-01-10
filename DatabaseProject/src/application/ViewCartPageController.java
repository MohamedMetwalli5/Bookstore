package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.*;

public class ViewCartPageController implements Initializable{

    @FXML
    private Text totalCostText;
    @FXML
    private TextField removedBookIsbn;

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    @FXML
    public void checkout(){

    }

    @FXML
    public void removeFromCart(){

    }

}
