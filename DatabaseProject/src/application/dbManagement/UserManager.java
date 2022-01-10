package application.dbManagement;

import java.sql.*;
import java.util.*;

import application.entities.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.*;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class UserManager {
	private PreparedStatement addUserStatement;
	private PreparedStatement updateUserStatement;
	private PreparedStatement getUserStatement;
	private PreparedStatement promoteUserStatement;
	private PreparedStatement getUserCartStatement;
	private CallableStatement isManagerStatement;
	/*private PreparedStatement addPublisherStatement;
	private PreparedStatement addAuthorStatement;*/
	private CallableStatement signInStatement;
	private CallableStatement signOutStatement;
	private Connection connection;
	UserManager(Connection connection) throws SQLException{
		this.connection = connection;
		addUserStatement = connection.prepareStatement("INSERT INTO USERS VALUES"
								+"( ?, ?, ?, ?, ?, ?,?)");
		updateUserStatement = connection.prepareStatement("UPDATE USERS "
												+"SET FNAME = ?, LNAME = ?, PHONE_NUM = ?, PASSWORD = ?, "
												+ "EMAIL = ?, SHIP_ADDRESS = ? "
												+ "WHERE NAME = ?");
		promoteUserStatement = connection.prepareStatement("INSERT INTO MANAGERS VALUES(?)");
		
		isManagerStatement = connection.prepareCall("{? = CALL IS_MANAGER(?)}");
		
		signInStatement = connection.prepareCall("{? = CALL SIGN_IN(?, ?)}");
		signOutStatement = connection.prepareCall("{CALL SIGN_OUT(?)}");
		getUserStatement = connection.prepareStatement("SELECT * FROM USERS WHERE NAME = ?");
		getUserCartStatement = connection.prepareStatement("SELECT * FROM CART WHERE USER_NAME=?");
		
	}
	public boolean addUser(User user) throws SQLException {
		addUserStatement.setString(1, user.getUserName());
		addUserStatement.setString(2, user.getfName());
		addUserStatement.setString(3, user.getlName());
		addUserStatement.setString(4, user.getPhone());
		addUserStatement.setString(5, user.getPassword());
		addUserStatement.setString(6, user.getEmail());
		addUserStatement.setString(7, user.getShipAddress());
		return addUserStatement.executeUpdate() == 1;
	}
	public boolean updateUser(User user) throws SQLException {
		updateUserStatement.setString(1, user.getfName());
		updateUserStatement.setString(2, user.getlName());
		updateUserStatement.setString(3, user.getPhone());
		updateUserStatement.setString(4, user.getPassword());
		updateUserStatement.setString(5, user.getEmail());
		updateUserStatement.setString(6, user.getShipAddress());
		updateUserStatement.setString(7, user.getUserName());
		System.out.println(updateUserStatement);
		return updateUserStatement.executeUpdate() == 1;
	}
	public User getUser(String userName) throws SQLException{
		getUserStatement.setString(1, userName);
		ResultSet result = getUserStatement.executeQuery();
		User user = new User();
		if(!result.next())
			return null;
		user.setUserName(result.getString(1));
		user.setfName(result.getString(2));
		user.setlName(result.getString(3));
		user.setPhone(result.getString(4));
		user.setPassword(result.getString(5));
		user.setEmail(result.getString(6));
		user.setShipAddress(result.getString(7));
		return user;
	}
	public boolean promoteUser(String userName) throws SQLException {
		promoteUserStatement.setString(1, userName);
		return promoteUserStatement.executeUpdate() == 1;
	}
	public boolean signIn(String userName, String password) throws SQLException {
		signInStatement.setString(2, userName);
		signInStatement.setString(3, password);
		signInStatement.registerOutParameter(1, Types.BOOLEAN);
		signInStatement.execute();
		return signInStatement.getBoolean(1);
	}
	public void signOut(String userName) throws SQLException {
		signOutStatement.setString(1, userName);
		signOutStatement.execute();
	}
	public boolean isManager(String userName) throws SQLException {
		isManagerStatement.setString(2, userName);
		isManagerStatement.registerOutParameter(1, Types.BOOLEAN);
		isManagerStatement.execute();
		return isManagerStatement.getBoolean(1);
	}
	
	public List<CartItem> getUserCart(String userName) throws SQLException{
		getUserCartStatement.setString(1, userName);
		return resultToCart(getUserCartStatement.executeQuery());
	}
	public List<CartItem> resultToCart(ResultSet rs) throws SQLException{
		CartItem item;
		List<CartItem> cart=new ArrayList<>();
		while(rs.next()){
			item=new CartItem();
			item.setIsbn(rs.getString(2));
			item.setQuantity(rs.getInt(3));
			Book book = new DBManager().getBookManager().getBooksByIsbn(item.getIsbn()).get(0);
			item.setTitle(book.getTitle());
			item.setBookPrice(book.getSellingPrice());
			item.setTotalPrice(book.getSellingPrice()*item.getQuantity());
			cart.add(item);
		}
		return cart;
	}

	public boolean viewTop5Customers() throws JRException {
		JasperDesign design = JRXmlLoader.load("src/application/reports/top5Customers.jrxml");
        JasperReport report = JasperCompileManager.compileReport(design);
        JasperPrint jprint = JasperFillManager.fillReport(report, null, this.connection);
        JasperViewer.viewReport(jprint, false);
        return true;
	}
	public boolean viewTop10Books() throws JRException {
		JasperDesign design = JRXmlLoader.load("src/application/reports/top10Books.jrxml");
        JasperReport report = JasperCompileManager.compileReport(design);
        JasperPrint jprint = JasperFillManager.fillReport(report, null, this.connection);
        JasperViewer.viewReport(jprint, false);
        return true;
	}
	public boolean viewTotalBookSales() throws JRException {
		JasperDesign design = JRXmlLoader.load("src/application/reports/totalBookSales.jrxml");
        JasperReport report = JasperCompileManager.compileReport(design);
        JasperPrint jprint = JasperFillManager.fillReport(report, null, this.connection);
        JasperViewer.viewReport(jprint, false);
        return true;
	}
	
}
