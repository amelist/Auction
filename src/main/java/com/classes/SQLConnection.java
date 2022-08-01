package com.classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SQLConnection {
	private static Connection conn;

	static{
		try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();             
               conn = setConnection();      
        }
        catch(Exception ex){
            System.out.println("Connection failed...");
            System.out.println(ex);
        }
		System.out.println("Connection to Store DB succesfull!");
	}
	
	private static Connection setConnection() throws SQLException{
	       
		   String url = "jdbc:mysql://localhost/auctiondb";
		   String username = "root";
		   String password = "Gytyde12";

		   return DriverManager.getConnection(url, username, password);
	}
	
	public static Connection getConn() {
		return conn;
	}
	
	public static int convertSQLDate(Date toConvert) {
		Calendar cal = new GregorianCalendar();
        cal.setTime(toConvert);
        return cal.get(Calendar.YEAR);
	}
}
