package application.dbManagement;
import java.sql.*;

public class DBManager {
	BookManager bookManager;
	UserManager userManager;
	Connection connection; 
	public DBManager() throws SQLException {
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
	/*public static void main(String args[]){
        try{
            System.out.println("before connection");
            Connection con =DriverManager.getConnection(
    				"jdbc:mysql://127.0.0.1:3306/BOOK_STORE","admin","admin");
            JasperDesign design = JRXmlLoader.load("src/application/reports/top5Customers.jrxml");
            JasperReport report = JasperCompileManager.compileReport(design);
            JasperPrint jprint = JasperFillManager.fillReport(report, null, con);
            JasperViewer.viewReport(jprint, false);
            con.close();
        }catch(Exception e){ System.out.println(e);}  
    }  */
	
	
}
