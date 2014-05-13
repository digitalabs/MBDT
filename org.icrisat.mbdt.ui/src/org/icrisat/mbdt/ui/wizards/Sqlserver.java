package org.icrisat.mbdt.ui.wizards;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
	public class Sqlserver 
    {
        public static void main(String[] args)
        {
            try
            {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

                String userName = "tis";
                String password = "tis";
                String databaseName = "tis";
               String url = "jdbc:sqlserver://10.4.11.7:1433;databaseName="+databaseName+";user="+userName+";password="+password;
                Connection con = DriverManager.getConnection(url);
                Statement s1 = con.createStatement();
                ResultSet rs = s1.executeQuery("SELECT  * FROM travelinfo");
                if(rs!=null){
                    while (rs.next()){
//                        
                    	System.out.println(rs.getObject(1)+".."+rs.getObject(2)+".."+rs.getObject(3)+".."+rs.getObject(4)+".."+rs.getObject(5)+".."+rs.getObject(6)+"..");
                    }
                }


            } catch (Exception e)
            {
                e.printStackTrace();
            }
    }


}
