package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.ResourceBundle;

import application.dbManagement.BookManager;
import application.dbManagement.UserManager;
import application.entities.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class HomePageController implements Initializable{
	
	@FXML
	private Button SignOutButton, EditProfileButton, ManagerOperationsButton, SearchButton, ViewCartButton;
	
	@FXML
	private Label TotalPriceLabel;
	
	@FXML
	private TextField InsertTextField;
	
	@FXML
	private RadioButton ISBNRadioButton, TitleRadioButton, AuthorRadioButton, PublisherRadioButton, PublicationYearRadioButton, SellingPriceRadioButton, CategoryRadioButton;
	
	private static String SearchType = "";
	
	@FXML
	private TableView<Book> BooksTable;
	@FXML
    private TableColumn<Book, String> ISBNColumn;
	@FXML
    private TableColumn<Book, String> TitleColumn;
	@FXML
    private TableColumn<Book, String> AuthorColumn;
	@FXML
    private TableColumn<Book, String> PublisherColumn;
	@FXML
    private TableColumn<Book, Integer> PublicationYearColumn;
	@FXML
    private TableColumn<Book, Double> SellingPriceColumn;
	@FXML
    private TableColumn<Book, String> CategoryColumn;
	@FXML 
	private TextField bookIsbn;
	@FXML
	private TextField bookQuantity;
	
	ObservableList<Book> GetBooks(List<Book> BooksList){
        ObservableList<Book> ObservableBooksList = FXCollections.observableArrayList();
        for(int i=0;i<BooksList.size();i++) {
        	ObservableBooksList.add(BooksList.get(i));
        }
        return ObservableBooksList;
    }
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		ISBNColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("isbn"));
		TitleColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
		AuthorColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
		PublisherColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("publisher"));
		PublicationYearColumn.setCellValueFactory(new PropertyValueFactory<Book, Integer>("publicationYear"));
		SellingPriceColumn.setCellValueFactory(new PropertyValueFactory<Book, Double>("sellingPrice"));
		CategoryColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("category"));
		
		BookManager bm = Main.db.getBookManager();
		try {
			List<Book> lb = bm.getBooks();
			BooksTable.setItems(GetBooks(lb));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	@FXML
	private void SignOut(MouseEvent mouseEvent) {
		UserManager um = Main.db.getUserManager();
		try {
			um.signOut(Main.TheUserName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Main m = new Main();
		try {
			m.changeScene("Home.fxml");
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	@FXML
	private void EditProfile(MouseEvent mouseEvent) {
		Main m = new Main();
		try {
			m.changeScene("ProfilePage.fxml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void ManagerOperations(MouseEvent mouseEvent){

		UserManager um = Main.db.getUserManager();
		try {
			if(um.isManager(Main.TheUserName)) {	
				System.out.println(Main.TheUserName);
				Main m = new Main();
				m.changeScene("ManagerOperationsPage.fxml");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		String SearchingText = InsertTextField.getText();
		BookManager bm = Main.db.getBookManager();
		List<Book> searchedBooks=new ArrayList<>();
		if(SearchType.equals("ISBN") && SearchingText.length() > 0) {
			try {
				searchedBooks = bm.getBooksByIsbn(SearchingText);
				BooksTable.setItems(GetBooks(searchedBooks));
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}else if(SearchType.equals("Title") && SearchingText.length() > 0) {
			try {
				searchedBooks = bm.getBooksByTitle(SearchingText);
				BooksTable.setItems(GetBooks(searchedBooks));
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}else if(SearchType.equals("Author") && SearchingText.length() > 0) {
			try {
				searchedBooks = bm.getBooksByAuthor(SearchingText);
				BooksTable.setItems(GetBooks(searchedBooks));
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}else if(SearchType.equals("Category") && SearchingText.length() > 0) {
			try {
				searchedBooks = bm.getBooksByCategory(SearchingText);
				BooksTable.setItems(GetBooks(searchedBooks));
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}else if(SearchType.equals("PublicationYear") && SearchingText.length() > 0) {
			try {
				searchedBooks = bm.getBooksByPublicationYear(Integer.parseInt(SearchType));
				BooksTable.setItems(GetBooks(searchedBooks));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else if(SearchType.equals("SellingPrice") && SearchingText.length() > 0) {
			try {
				searchedBooks=bm.getBooksWithPrice(Integer.parseInt(SearchingText));
				BooksTable.setItems(GetBooks(searchedBooks));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else if(SearchType.equals("Publisher") && SearchingText.length() > 0) {
			try {
				searchedBooks = bm.getBooksByPublisher(SearchingText);
				BooksTable.setItems(GetBooks(searchedBooks));
			} catch (SQLException e) {
				e.printStackTrace();
			}			
		}else {
			// Get the whole original table
			BookManager bm2 = Main.db.getBookManager();
			try {
				List<Book> lb = bm.getBooks();
				BooksTable.setItems(GetBooks(lb));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@FXML
	private void ViewCart(MouseEvent mouseEvent) throws IOException {
		Main m = new Main();
		m.changeScene("ViewCartPage.fxml");	
	}
	@FXML 
	private void AddToCart(){
		if(!bookIsbn.getText().isEmpty() && !bookQuantity.getText().isEmpty()){
			try {
				Main.db.getBookManager().addToCart(Main.TheUserName, bookIsbn.getText().trim(), Integer.parseInt(bookQuantity.getText().trim()));
				bookIsbn.setText("");
				bookQuantity.setText("");
			} catch (NumberFormatException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
//	@FXML
//	private void CheckOut(MouseEvent mouseEvent) throws IOException, SQLException {
//		// Change the total price label and make it equals zero
//		
//		BookManager bm = Main.db.getBookManager();
//		bm.emptyCart(Main.TheUserName);
//		
//		Main m = new Main();
//		m.changeScene("Home.fxml"); // to make the user sign out	
//	}
}
