package application.dbManagement;

import java.sql.*;

public class UserManager {
	private PreparedStatement addUserStatement;
	private PreparedStatement promoteUserStatement;
	private PreparedStatement isManagerStatement;
	/*private PreparedStatement addPublisherStatement;
	private PreparedStatement addAuthorStatement;*/
	// user sign in
	private CallableStatement signInStatement;
	UserManager(Connection connection) throws SQLException{
		addUserStatement = connection.prepareStatement("INSERT INTO USERS VALUES"
								+"( ?, ?, ?, ?, ?, ?)");
		promoteUserStatement = connection.prepareStatement("INSERT INTO MANAGERS VALUES(?)");
		
		isManagerStatement = connection.prepareStatement("SELECT * FROM MANAGERS WHERE NAME = ?");
		
		signInStatement = connection.prepareCall("{? = CALL  (?, ?)}");
		
	}
	public boolean signIn(String userName, String password) throws SQLException {
		signInStatement.setString(2, userName);
		signInStatement.setString(3, password);
		signInStatement.registerOutParameter(1, Types.BOOLEAN);
		signInStatement.execute();
		return signInStatement.getInt(1) != 0;
	}
	
}
