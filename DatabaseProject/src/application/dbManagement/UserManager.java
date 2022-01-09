package application.dbManagement;

import java.sql.*;

import application.entities.*;

public class UserManager {
	private PreparedStatement addUserStatement;
	private PreparedStatement promoteUserStatement;
	private CallableStatement isManagerStatement;
	/*private PreparedStatement addPublisherStatement;
	private PreparedStatement addAuthorStatement;*/
	private CallableStatement signInStatement;
	private CallableStatement signOutStatement;
	UserManager(Connection connection) throws SQLException{
		addUserStatement = connection.prepareStatement("INSERT INTO USERS VALUES"
								+"( ?, ?, ?, ?, ?, ?)");
		promoteUserStatement = connection.prepareStatement("INSERT INTO MANAGERS VALUES(?)");
		
		isManagerStatement = connection.prepareCall("{? = CALL IS_MANAGER(?)}");
		
		signInStatement = connection.prepareCall("{? = CALL SIGN_IN(?, ?)}");
		signOutStatement = connection.prepareCall("{CALL SIGN_OUT(?)}");
		
		
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
		return signInStatement.getBoolean(1);
	}
	
}
