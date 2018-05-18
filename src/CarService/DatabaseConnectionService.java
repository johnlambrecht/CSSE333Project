package CarService;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnectionService {

	/**
	 * The code below is a modified version of the one given to us in the connectivity lab
	 * Edited by: Abhimanyu Agarwal
	 */
	private final String SampleURL = "jdbc:sqlserver://${dbServer};databaseName=${dbName};user=${user};password={${pass}}";

	private Connection connection = null;

	private String databaseName;
	private String serverName;

	public DatabaseConnectionService(String serverName, String databaseName) {
		//DO NOT CHANGE THIS METHOD
		this.serverName = serverName;
		this.databaseName = databaseName;
	}

	public boolean connect(String user, String pass) {
		//TODO: Task 1
		//BUILD YOUR CONNECTION STRING HERE USING THE SAMPLE URL ABOVE
		String connectionString = SampleURL;
		connectionString = connectionString.replace("${dbServer}", this.serverName);
		connectionString = connectionString.replace("${dbName}", this.databaseName );
		connectionString = connectionString.replace("${user}", user);
		connectionString = connectionString.replace("${pass}", pass);
		System.out.println(connectionString);
		try {
//			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            connection = DriverManager.getConnection(connectionString);  
            return true;
        }  
        catch (Exception e) {  
            e.printStackTrace();  
            return false;
        }  
	}
	

	public Connection getConnection() {
		return this.connection;
	}
	
	public void closeConnection() {
		if (connection != null) try { connection.close(); } catch(Exception e) {}
	}

}
