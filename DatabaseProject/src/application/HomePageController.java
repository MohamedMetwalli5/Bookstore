package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
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
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class HomePageController implements Initializable{
	
	@FXML
	private Button SignOutButton, EditProfileButton, ManagerOperationsButton, SearchButton, ViewCartButton, CheckOutButton;
	
	@FXML
	private Label TotalPriceLabel;
	
	@FXML
	private TextField InsertTextField;
	
	@FXML
	private RadioButton ISBNRadioButton, TitleRadioButton, AuthorRadioButton, PublisherRadioButton, PublicationYearRadioButton, SellingPriceRadioButton, CategoryRadioButton;
	
	private static String SearchType = "Title";
	
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
		List<Book> lb;
		try {
			lb = bm.getBooks();
			BooksTable.setItems(GetBooks(lb));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void Display(MouseEvent mouseEvent) throws SQLException {
		BookManager bm = Main.db.getBookManager();
		List<Book> lb = bm.getBooks();
		System.out.println(lb.get(0).getAuthor());
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
			System.out.println("ISBN Is Chosen");
		}else if(TitleRadioButton.isSelected()) {
			SearchType = "Title";
			System.out.println("Title Is Chosen");
		}else if(AuthorRadioButton.isSelected()) {
			SearchType = "Author";
			System.out.println("Author Is Chosen");
		}else if(PublisherRadioButton.isSelected()) {
			SearchType = "Publisher";
			System.out.println("Publisher Is Chosen");
		}else if(PublicationYearRadioButton.isSelected()) {
			SearchType = "PublicationYear";
			System.out.println("PublicationYear Is Chosen");
		}else if(SellingPriceRadioButton.isSelected()) {
			SearchType = "SellingPrice";
			System.out.println("SellingPrice Is Chosen");
		}else if(CategoryRadioButton.isSelected()) {
			SearchType = "Category";
			System.out.println("Category Is Chosen");
		}
	}
	
	@FXML
	private void Search(MouseEvent mouseEvent) throws IOException {
		String SearchingText = InsertTextField.getText();
		BookManager bm = Main.db.getBookManager();
		if(SearchType.equals("ISBN")) {
			try {
				bm.getBooksByIsbn(SearchingText);
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}else if(SearchType.equals("Title")) {
			try {
				bm.getBooksByTitle(SearchingText);
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}else if(SearchType.equals("Author")) {
			try {
				bm.getBooksByAuthor(SearchingText);
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}else if(SearchType.equals("Category")) {
			try {
				bm.getBooksByCategory(SearchingText);
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}else if(SearchType.equals("PublicationYear")) {
			try {
				bm.getBooksByPublicationYear(Integer.parseInt(SearchType));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else if(SearchType.equals("SellingPrice")) {
			try {
				bm.getBooksWithPrice(Integer.parseInt(SearchingText));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else if(SearchType.equals("Publisher")) {
			try {
				bm.getBooksByPublisher(SearchingText);
			} catch (SQLException e) {
				e.printStackTrace();
			}			
		}
		
	}
	
	@FXML
	private void ViewCart(MouseEvent mouseEvent) throws IOException {
		Main m = new Main();
		m.changeScene("ViewCart.fxml");	
	}
	
	@FXML
	private void CheckOut(MouseEvent mouseEvent) throws IOException, SQLException {
		// Change the total price label and make it equals zero
		
		BookManager bm = Main.db.getBookManager();
		bm.emptyCart(Main.TheUserName);
		
		Main m = new Main();
		m.changeScene("Home.fxml"); // to make the user sign out	
	}
}
