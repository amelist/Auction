package com.tags;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.classes.SQLConnection;

public class DBUpdateTag extends BodyTagSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6090102276316035907L;

	@Override
	public int doStartTag(){
			if(SQLConnection.getConn() != null) {
				String command1 = "SELECT car_id FROM cars WHERE date_active < ?";
				String command2 = "UPDATE cars SET is_active = ?, date_active = ?, "
						+ "c_owner = (SELECT bet_user FROM bets WHERE bet_car = ? AND cost = "
						+ "(SELECT MAX(cost) FROM bets WHERE bet_car = ?)) "
						+ "WHERE car_id = ?";
				PreparedStatement preparedStatement;
				try {
					preparedStatement = SQLConnection.getConn().prepareStatement(command1);
					preparedStatement.setTimestamp(1, new java.sql.Timestamp(new Date().getTime()));
					ResultSet resultSet = preparedStatement.executeQuery();
					while(resultSet.next()) {
						preparedStatement = SQLConnection.getConn().prepareStatement(command2);
						preparedStatement.setBoolean(1, false);
						preparedStatement.setTimestamp(2, null);
						preparedStatement.setInt(3, resultSet.getInt("car_id"));
						preparedStatement.setInt(4, resultSet.getInt("car_id"));
						preparedStatement.setInt(5, resultSet.getInt("car_id"));
						int rows = preparedStatement.executeUpdate();
					}
				}catch (SQLException ex){
					System.out.println(ex);
				}
			}else {
				System.out.println("SQL error");
			}
		return SKIP_BODY;
	}
}
