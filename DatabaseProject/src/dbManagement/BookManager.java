package dbManagement;
import java.sql.*;
import java.text.SimpleDateFormat;

import entities.*;
public class BookManager {
	private SimpleDateFormat format = new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private PreparedStatement addBookStatement;
	private PreparedStatement addSaleStatement;
	private PreparedStatement addBookOrderStatement;
	private PreparedStatement confirmOrderStatement;
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
}
