package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import application.dbManagement.BookManager;
import application.dbManagement.UserManager;
import application.entities.Book;
import application.entities.BookOrder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import net.sf.jasperreports.engine.JRException;

public class ManagerOperationsPageController implements Initializable {
	
	@FXML
	private Button BackButton, SignOutButton, SaveAddBookButton, SaveModifyBookButton, OrderBooksButton, SearchBookButton, AddManagerButton;
	
	@FXML
	private TextField AddBookISBNNumberTextField, AddBookTitleTextField, AddBookPublicationYearTextField, AddBookSellingPriceTextField, AddBookAuthorTextField, AddBookCategoryTextField, AddBookPublisherTextField, AddBookQuantityTextField, AddBookMinQuantityTextField;

	@FXML
	private TextField ModifyBookISBNNumberTextField, ModifyBookNewQuantityTextField;
	
	@FXML
	private TextField OrderBooksISBNNumberTextField, OrderBooksNewQuantityTextField;

	@FXML
	private TextField confirmedOrderIdText;

	@FXML
	private TextField UserNameTextField;
	@FXML
	private TableView<BookOrder> OrdersTable;
	@FXML
    private TableColumn<BookOrder, String> IDColumn;
	@FXML
    private TableColumn<BookOrder, String> ISBNColumn;
	@FXML
    private TableColumn<BookOrder, Integer> QuantityColumn;
	@FXML
    private TableColumn<BookOrder, Date> OrderDateColumn;

	ObservableList<BookOrder> GetBookOrders(List<BookOrder> orderList){
        ObservableList<BookOrder> ObservableOrdersList = FXCollections.observableArrayList();
        for(int i=0;i<orderList.size();i++) {
        	ObservableOrdersList.add(orderList.get(i));
        }
        return ObservableOrdersList;
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
	private void SignOut(MouseEvent mouseEvent) {
		Main m = new Main();
		try {
			m.changeScene("Home.fxml");
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	@FXML
	private void GenerateReport(MouseEvent mouseEvent) {
		UserManager um = Main.db.getUserManager();
		try {
			um.viewTop10Books();
		} catch (JRException e) {
			e.printStackTrace();
		}
		try {
			um.viewTop5Customers();
		} catch (JRException e) {
			e.printStackTrace();
		}
		try {
			um.viewTotalBookSales();
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void SaveAddBook(MouseEvent mouseEvent) {
		
			BookManager bm = Main.db.getBookManager();
			Book b = new Book();
			if(AddBookISBNNumberTextField.getLength()!=0
				&&AddBookTitleTextField.getLength()!=0
				&&AddBookPublicationYearTextField.getLength()!=0
				&&AddBookAuthorTextField.getLength()!=0
				&&AddBookCategoryTextField.getLength()!=0
				&&AddBookSellingPriceTextField.getLength()!=0
				&&AddBookPublisherTextField.getLength()!=0){
					b.setIsbn(AddBookISBNNumberTextField.getText());
					b.setTitle(AddBookTitleTextField.getText());
					b.setAuthor(AddBookAuthorTextField.getText());
					b.setPublisher(AddBookPublisherTextField.getText());
					b.setPublicationYear(Integer.parseInt(AddBookPublicationYearTextField.getText()));
					b.setCategory(AddBookCategoryTextField.getText());
					b.setSellingPrice(Double.parseDouble(AddBookSellingPriceTextField.getText()));
					
					try {
						bm.addBook(b);
					} catch (SQLException e) {
						e.printStackTrace();
					}
		}
	}
	
	@FXML
	private void SaveModifyBook(MouseEvent mouseEvent) {
		
		BookManager bm = Main.db.getBookManager();
		if(ModifyBookISBNNumberTextField.getLength()!=0&&ModifyBookNewQuantityTextField.getLength()!=0){
			try {
				Book b = bm.getBooksByIsbn(ModifyBookISBNNumberTextField.getText()).get(0);
				b.setQuantity(Integer.parseInt(ModifyBookNewQuantityTextField.getText()));
				bm.updateBook(b);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@FXML
	private void SaveOrderBooks(MouseEvent mouseEvent) {

		BookManager bm = Main.db.getBookManager();
		BookOrder bo = new BookOrder();
		bo.setIsbn(this.OrderBooksISBNNumberTextField.getText());
		bo.setQuantity(Integer.parseInt(this.OrderBooksNewQuantityTextField.getText()));
		bo.setOrderDate((java.sql.Date) new Date());
		try {
			bm.addBookOrder(bo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	@FXML
	private void OrderBooks(MouseEvent mouseEvent) {
		BookManager bm = Main.db.getBookManager();
		if(OrderBooksISBNNumberTextField.getLength()!=0&&OrderBooksNewQuantityTextField.getLength()!=0){
			try {
				Book b = bm.getBooksByIsbn(OrderBooksISBNNumberTextField.getText()).get(0);
				BookOrder order = new BookOrder();
				order.setIsbn(b.getIsbn());
				order.setOrderDate(new Date(System.currentTimeMillis()));
				order.setQuantity(Integer.parseInt(OrderBooksNewQuantityTextField.getText()));
				bm.addBookOrder(order);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	@FXML
	private void confirmOrder(){
		if(confirmedOrderIdText.getText().isEmpty())
			return;
		try {
			Main.db.getBookManager().confirmBookOrder(Integer.parseInt(confirmedOrderIdText.getText()));
		} catch (NumberFormatException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void AddManager(MouseEvent mouseEvent) {
		
		UserManager um = Main.db.getUserManager();
		try {
			if(this.UserNameTextField.getText() != null && this.UserNameTextField.getText().length() > 0) {
				um.promoteUser(this.UserNameTextField.getText());	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		IDColumn.setCellValueFactory(new PropertyValueFactory<BookOrder, String>("id"));
		ISBNColumn.setCellValueFactory(new PropertyValueFactory<BookOrder, String>("isbn"));
		QuantityColumn.setCellValueFactory(new PropertyValueFactory<BookOrder, Integer>("quantity"));
		OrderDateColumn.setCellValueFactory(new PropertyValueFactory<BookOrder, Date>("orderDate"));
		BookManager bm = Main.db.getBookManager();
		try {
			List<BookOrder> lb = bm.getBookOrders();
			OrdersTable.setItems(GetBookOrders(lb));
		} catch (SQLException | ParseException e) {
			e.printStackTrace();
		}
	}
}
