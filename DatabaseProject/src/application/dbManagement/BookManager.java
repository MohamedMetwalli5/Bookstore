package application.dbManagement;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import application.entities.*;
public class BookManager {
	private SimpleDateFormat format = new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private PreparedStatement addBookStatement;
	private PreparedStatement updateBookStatement;
	private PreparedStatement addSaleStatement;
	private PreparedStatement addBookOrderStatement;
	private PreparedStatement confirmOrderStatement;
	private PreparedStatement getBooksStatement;
	private PreparedStatement getBookOrdersStatement;
	private PreparedStatement addToCartStatement;
	private PreparedStatement removeFromCartStatement;
	private PreparedStatement emptyCartStatement;
	private PreparedStatement getBooksByIsbnStatement;
	private PreparedStatement getBooksByTitleStatement;
	private PreparedStatement getBooksByAuthorStatement;
	private PreparedStatement getBooksByPublisherStatement;
	private PreparedStatement getBooksByPublicationYearStatement;
	private PreparedStatement getBooksWithPriceStatement;
	private PreparedStatement getBooksByCategoryStatement;
	private PreparedStatement getBooksLessThanOrEqualPriceStatement;
	private PreparedStatement getBooksMoreThanOrEqualPriceStatement;
	
	BookManager(Connection connection) throws SQLException{
		addBookStatement = connection.prepareStatement("INSERT INTO BOOKS VALUES"
								+"(?, ?, ?, ?, ?, ?, ?, ?, ?)");
		updateBookStatement =  connection.prepareStatement("UPDATE BOOKS SET QUANTITY =? WHERE ISBN = ?");
		addSaleStatement = connection.prepareStatement("INSERT INTO SALES"+
								"(USER_NAME,ISBN,SALE_TIME,QUANTITY) VALUES"
								+"( ?, ?, ?, ?)");
		addBookOrderStatement = connection.prepareStatement("INSERT INTO BOOK_ORDER"+
								"(ISBN,QUANTITY,ORDER_DATE) VALUES"
								+"(?, ?, ?)");
		addToCartStatement = connection.prepareStatement("INSERT INTO CART VALUES(?, ?, ?)");
		
		confirmOrderStatement = connection.prepareStatement("DELETE FROM BOOK_ORDER WHERE ID = ?");
		removeFromCartStatement = connection.prepareStatement("DELETE FROM CART WHERE USER_NAME = ? AND ISBN = ?");
		emptyCartStatement = connection.prepareCall("DELETE FROM CART WHERE USER_NAME = ?");
		
		getBooksStatement = connection.prepareStatement("SELECT * FROM BOOKS ORDER BY TITLE");
		/*getSalesStatement = connection.prepareStatement("SELECT * FROM SALES");*/
		getBookOrdersStatement = connection.prepareStatement("SELECT * FROM BOOK_ORDER");
		getBooksByIsbnStatement=connection.prepareStatement("SELECT * FROM BOOKS WHERE ISBN=?");
		getBooksByTitleStatement=connection.prepareStatement("SELECT * FROM BOOKS WHERE TITLE=?");
		getBooksByAuthorStatement=connection.prepareStatement("SELECT * FROM BOOKS WHERE AUTHOR=?");
		getBooksByPublisherStatement=connection.prepareStatement("SELECT * FROM BOOKS WHERE PUBLISHER=?");
		getBooksByPublicationYearStatement=connection.prepareStatement("SELECT * FROM BOOKS WHERE PUBLICATION_YEAR=?");
		getBooksWithPriceStatement=connection.prepareStatement("SELECT * FROM BOOKS WHERE SELLING_PRICE=?");
		getBooksByCategoryStatement=connection.prepareStatement("SELECT * FROM BOOKS WHERE CATEGORY=?");
		getBooksLessThanOrEqualPriceStatement=connection.prepareStatement("SELECT * FROM BOOKS WHERE SELLING_PRICE <= ?");
		getBooksMoreThanOrEqualPriceStatement=connection.prepareStatement("SELECT * FROM BOOKS WHERE SELLING_PRICE >= ?");
	}
	
	public boolean addBook(Book book) throws SQLException {
		addBookStatement.setString(1, book.getIsbn());
		addBookStatement.setString(2, book.getTitle());
		addBookStatement.setString(3, book.getAuthor());
		addBookStatement.setString(4, book.getPublisher());
		addBookStatement.setInt(5, book.getPublicationYear());
		addBookStatement.setDouble(6, book.getSellingPrice());
		addBookStatement.setString(7, book.getCategory());
		addBookStatement.setInt(8, book.getMinQuantity());
		addBookStatement.setInt(9, book.getQuantity());
		System.out.println(addBookStatement);
		return addBookStatement.executeUpdate() == 1;
	}
	
	public boolean updateBook(Book book) throws SQLException {
		updateBookStatement.setInt(1, book.getQuantity());
		updateBookStatement.setString(2, book.getIsbn());
		return updateBookStatement.executeUpdate() == 1;
	}
	
	public boolean addSale(Sale sale) throws SQLException {
		addSaleStatement.setString(1, sale.getUserName());
		addSaleStatement.setString(2, sale.getIsbn());
		addSaleStatement.setString(3, format.format(sale.getSaleTime()));
		addSaleStatement.setInt(4, sale.getQuantity());
		return addSaleStatement.executeUpdate() == 1;
	}
	
	public boolean addBookOrder(BookOrder order) throws SQLException {
		addBookOrderStatement.setString(1, order.getIsbn());
		addBookOrderStatement.setInt(2, order.getQuantity());
		addBookOrderStatement.setString(3, format.format(order.getOrderDate()));
		return addBookOrderStatement.executeUpdate() == 1;
	}
	
	public boolean addToCart(String userName, String isbn, int quantity) throws SQLException {
		addToCartStatement.setString(1, userName);
		addToCartStatement.setString(2, isbn);
		addToCartStatement.setInt(3, quantity);
		return addToCartStatement.executeUpdate() == 1;
	}
	
	public boolean removeFromCart(String userName, String isbn) throws SQLException {
		removeFromCartStatement.setString(1, userName);
		removeFromCartStatement.setString(2, isbn);
		return removeFromCartStatement.executeUpdate() == 1;
	}
	
	public void emptyCart(String userName) throws SQLException {
		emptyCartStatement.setString(1, userName);
		emptyCartStatement.executeUpdate();
	}
	public void checkOut(String userName,List<CartItem> list) throws SQLException{
		for(CartItem item : list){
			Sale sale = new Sale();
			sale.setUserName(userName);
			sale.setIsbn(item.getIsbn());
			sale.setSalePrice(item.getTotalPrice());
			sale.setQuantity(item.getQuantity());
			sale.setSaleTime(new Date(System.currentTimeMillis()));
			addSale(sale);
		}
	}
	public boolean confirmBookOrder(int id) throws SQLException {
		confirmOrderStatement.setInt(1, id);
		return confirmOrderStatement.executeUpdate() == 1;
	}
	
	private List<Book> resultToBooks(ResultSet result) throws SQLException{
		Book book;
		List<Book> bookList = new LinkedList<>();
		while(result.next()) {
			book = new Book();
			book.setIsbn(result.getString(1));
			book.setTitle(result.getString(2));
			book.setAuthor(result.getString(3));
			book.setPublisher(result.getString(4));
			book.setPublicationYear(result.getInt(5));
			book.setSellingPrice(result.getDouble(6));
			book.setCategory(result.getString(7));
			book.setMinQuantity(result.getInt(8));
			book.setQuantity(result.getInt(9));
			bookList.add(book);
		}
		return bookList;
	}
	
	public List<Book> getBooks() throws SQLException{
		return resultToBooks(getBooksStatement.executeQuery());
	}
	
	/*private List<Sale> resultToSales(ResultSet result) throws SQLException{
		Sale sale;
		List<Sale> saleList = new LinkedList<>();
		while(result.next()) {
			sale = new Sale();
			sale.setId(result.getInt(1));
			sale.setUserName(result.getString(2));
			sale.setIsbn(result.getString(3));
			
		}
	}
	public List<Sale> getSales() throws SQLException{
		return resultToSales(getSalesStatement.executeQuery());
	}*/
	public List<BookOrder> resultToBookOrders(ResultSet result) throws SQLException, ParseException{
		BookOrder order;
		List<BookOrder> orderList = new LinkedList<>();
		while(result.next()) {
			order = new BookOrder();
			order.setId(result.getInt(1));
			order.setIsbn(result.getString(2));
			order.setQuantity(result.getInt(3));
			order.setOrderDate(format.parse(result.getString(4)));
			orderList.add(order);
		}
		return orderList;
	}
	
	public List<BookOrder> getBookOrders() throws SQLException, ParseException{
		return resultToBookOrders(getBookOrdersStatement.executeQuery());
	}
	
	public List<Book> getBooksByIsbn(String isbn) throws SQLException{
		getBooksByIsbnStatement.setString(1, isbn);
		return resultToBooks(getBooksByIsbnStatement.executeQuery());
	}
	public List<Book> getBooksByTitle(String title) throws SQLException{
		getBooksByTitleStatement.setString(1,title);
		return resultToBooks(getBooksByTitleStatement.executeQuery());
	}
	public List<Book> getBooksByAuthor(String author) throws SQLException{
		getBooksByAuthorStatement.setString(1,author);
		return resultToBooks(getBooksByAuthorStatement.executeQuery());
	}
	public List<Book> getBooksByPublisher(String publisher) throws SQLException{
		getBooksByPublisherStatement.setString(1,publisher);
		return resultToBooks(getBooksByPublisherStatement.executeQuery());
	}
	public List<Book> getBooksByPublicationYear(int year) throws SQLException{
		getBooksByPublicationYearStatement.setInt(3, year);
		return resultToBooks(getBooksByPublicationYearStatement.executeQuery());
	}
	
	public List<Book> getBooksLessThanOrEqualPrice(double price) throws SQLException{
		getBooksLessThanOrEqualPriceStatement.setDouble(1, price);
		return resultToBooks(getBooksLessThanOrEqualPriceStatement.executeQuery());
	}
	public List<Book> getBooksMoreThanOrEqualPrice(double price) throws SQLException{
		getBooksMoreThanOrEqualPriceStatement.setDouble(1, price);
		return resultToBooks(getBooksMoreThanOrEqualPriceStatement.executeQuery());
	}
	public List<Book> getBooksWithPrice(double price) throws SQLException{
		getBooksWithPriceStatement.setDouble(1,price);
		return resultToBooks(getBooksWithPriceStatement.executeQuery());
	}
	public List<Book> getBooksByCategory(String category) throws SQLException{
		getBooksByCategoryStatement.setString(1,category);
		return resultToBooks(getBooksByCategoryStatement.executeQuery());
	}
}
