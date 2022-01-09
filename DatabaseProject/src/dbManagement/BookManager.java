package dbManagement;
import java.sql.*;

import entities.*;
public class BookManager {
	private PreparedStatement addBookStatement;
	private PreparedStatement addSaleStatement;
	private PreparedStatement addBookOrderStatement;
	private PreparedStatement confirmOrderStatement;
	BookManager(Connection connection) throws SQLException{
		addBookStatement = connection.prepareStatement("INSERT INTO BOOKS VALUES"
								+"(?, ?, ?, ?, ?, ?, ?, ?, ?)");
		addSaleStatement = connection.prepareStatement("INSERT INTO SALES VALUES"
								+"(?, ?, ?, ?, ?, ?)");
		addBookOrderStatement = connection.prepareStatement("INSERT INTO BOOK_ORDER VALUES"
								+"(?, ?, ?, ?)");
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
		addSaleStatement.setInt(1, sale.getId()); // Should the id be generated in DB!
		addSaleStatement.setString(2, sale.getUserName());
		addSaleStatement.setString(3, sale.getIsbn());
		addSaleStatement.setDate(4, sale.getSaleTime());
		addSaleStatement.setInt(5, sale.getQuantity());
		addSaleStatement.setDouble(6, sale.getSalePrice());
		return addSaleStatement.executeUpdate() == 1;
	}
	public boolean addBookOrder(BookOrder order) throws SQLException {
		addBookOrderStatement.setInt(1, order.getId());// Should the id be generated in DB!
		addBookOrderStatement.setString(2, order.getIsbn());
		addBookOrderStatement.setInt(3, order.getQuantity());
		addBookOrderStatement.setDate(4, order.getOrderDate());
		return addBookOrderStatement.executeUpdate() == 1;
	}
	public boolean confirmBookOrder(int id) throws SQLException {
		confirmOrderStatement.setInt(1, id);
		return confirmOrderStatement.executeUpdate() == 1;
	}
}
