package application;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import application.dbManagement.BookManager;
import application.dbManagement.UserManager;
import application.entities.Book;
import application.entities.BookOrder;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import net.sf.jasperreports.engine.JRException;

public class ManagerOperationsPageController {
	
	@FXML
	private Button BackButton, SignOutButton, SaveAddBookButton, SaveModifyBookButton, OrderBooksButton, SearchBookButton, AddManagerButton;
	
	@FXML
	private TextField AddBookISBNNumberTextField, AddBookTitleTextField, AddBookPublicationYearTextField, AddBookSellingPriceTextField, AddBookAuthorTextField, AddBookCategoryTextField, AddBookPublisherTextField;

	@FXML
	private TextField ModifyBookISBNNumberTextField, ModifyBookNewQuantityTextField;
	
	@FXML
	private TextField OrderBooksISBNNumberTextField, OrderBooksNewQuantityTextField;

	@FXML
	private TextField UserNameTextField;
	
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
			b.setIsbn(this.AddBookISBNNumberTextField.getText());
			b.setTitle(this.AddBookTitleTextField.getText());
			b.setAuthor(this.AddBookAuthorTextField.getText());
//			b.setPublisher(this.AddBookPublisherTextField.getText());
			b.setPublicationYear(Integer.parseInt(this.AddBookPublicationYearTextField.getText()));
//			b.setCategory(this.Add.getText());
			b.setSellingPrice(Integer.parseInt(this.AddBookSellingPriceTextField.getText()));
			
			try {
				bm.addBook(b);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	@FXML
	private void SaveModifyBook(MouseEvent mouseEvent) {
		
		BookManager bm = Main.db.getBookManager();
		Book b = new Book();
		b.setAuthor(ModifyBookISBNNumberTextField.getText());
		b.setQuantity(Integer.parseInt(ModifyBookNewQuantityTextField.getText()));
		try {
			bm.updateBook(b);
		} catch (SQLException e) {
			e.printStackTrace();
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
	private void SearchBook(MouseEvent mouseEvent) {
		
	}
	
	@FXML
	private void OrderBooks(MouseEvent mouseEvent) {
		
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
}
