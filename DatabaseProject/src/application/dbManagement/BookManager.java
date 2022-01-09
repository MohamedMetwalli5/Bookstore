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
	private PreparedStatement addSaleStatement;
	private PreparedStatement addBookOrderStatement;
	private PreparedStatement confirmOrderStatement;
	private PreparedStatement getBooksStatement;
	/*private PreparedStatement getSalesStatement;*/
	private PreparedStatement getBookOrdersStatement;
	
	//search for books and filtering ?
	
	BookManager(Connection connection) throws SQLException{
		addBookStatement = connection.prepareStatement("INSERT INTO BOOKS VALUES"
								+"(?, ?, ?, ?, ?, ?, ?, ?, ?)");
		addSaleStatement = connection.prepareStatement("INSERT INTO SALES"+
								"(USER_NAME,ISBN,SALE_TIME,QUANTITY) VALUES"
								+"( ?, ?, ?, ?)");
		addBookOrderStatement = connection.prepareStatement("INSERT INTO BOOK_ORDER"+
								"(ISBN,QUANTITY,ORDER_DATE) VALUES"
								+"(?, ?, ?)");
		
		confirmOrderStatement = connection.prepareStatement("DELETE FROM BOOK_ORDER WHERE ID = ?");
		
		getBooksStatement = connection.prepareStatement("SELECT * FROM BOOKS ORDER BY TITLE");
		/*getSalesStatement = connection.prepareStatement("SELECT * FROM SALES");*/
		getBookOrdersStatement = connection.prepareStatement("SELECT * FROM BOOK_ORDER");
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
		return addBookStatement.executeUpdate() == 1;
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
			order.setOrderDate((Date)format.parse(result.getString(4)));
			orderList.add(order);
		}
		return orderList;
	}
	public List<BookOrder> getBookOrders() throws SQLException, ParseException{
		return resultToBookOrders(getBookOrdersStatement.executeQuery());
	}
}
