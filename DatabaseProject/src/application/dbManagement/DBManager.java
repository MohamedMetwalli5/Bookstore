package application.dbManagement;
import java.sql.*;
public class DBManager {
	BookManager bookManager;
	UserManager userManager;
	Connection connection;
	public DBManager() throws SQLException {
		//Start connection
		DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        System.out.println("before connection");
        connection =DriverManager.getConnection(
        				"jdbc:mysql://127.0.0.1:3306/BOOK_STORE","admin","admin");
        bookManager = new BookManager(connection);
        userManager = new UserManager(connection);
        
	}
	public BookManager getBookManager() {
		return bookManager;
	}
	public UserManager getUserManager() {
		return userManager;
	}
	public void commitAll() throws SQLException {
		connection.commit();
	}
	public static void main(String args[]){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("before connection");
            Connection con=DriverManager.getConnection(
            "jdbc:mysql://127.0.0.1:3306/FSCHOOL","admin","admin");

            System.out.println("before creating the statement");
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT * FROM users");
            System.out.println("before getting data");
            while(rs.next())
            System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
            con.close();
        }catch(Exception e){ System.out.println(e);}  
    }  
	
	
}
