package org.icrisat.mbdt.ui.wizards;



	import java.sql.Connection;   
	import java.sql.DriverManager;   
	import java.sql.ResultSet;   
	import java.sql.Statement;   
	  
	/**  
	 * @author www.javaworkspace.com  
	 *   
	 */  
	public class DataConnection {   
	    public static void main(String[] args) {   
	    	
	    	  try {   
		            Class.forName("com.mysql.jdbc.Driver");   
		            Connection connection = DriverManager.getConnection(   
		                    "jdbc:mysql://localhost:3306/ibdb", "root", "root");   
		            Statement statement = connection.createStatement();   
		            ResultSet resultSet = statement   
		                    .executeQuery("SELECT userid FROM users");   
		            while (resultSet.next()) {   
		            }   
		        } catch (Exception e) {   
		        }
	    }
	}  


