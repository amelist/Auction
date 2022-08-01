package com.tags;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.classes.SQLConnection;

public class CarOutputTag extends BodyTagSupport{
	
	private String minprice;
	private String maxprice;

	private static final long serialVersionUID = -4948612762905449369L;

	@Override
	public int doStartTag() throws JspException {
		JspWriter out=pageContext.getOut();
		try {
			if(SQLConnection.getConn() != null) {
				String command = "SELECT car_id, model, min_price, c_year, date_active, cost, c_owner "
						+ "FROM cars JOIN bets ON bets.bet_car = cars.car_id "
						+ "WHERE cost = (SELECT MAX(cost) FROM bets WHERE bets.bet_car = cars.car_id) "
						+ "AND min_price BETWEEN ? AND ?";
				PreparedStatement preparedStatement;
				try {
					if(minprice == null || minprice.isEmpty()) minprice = "0";
					if(maxprice == null || maxprice.isEmpty()) maxprice = "999999999";
					preparedStatement = SQLConnection.getConn().prepareStatement(command);
					preparedStatement.setString(1, minprice);
					preparedStatement.setString(2, maxprice);
					printCarTable(out, preparedStatement.executeQuery());
				}catch (SQLException ex){
					System.out.println(ex);
			}
			}else {
				out.println("SQL error");
			}
		}catch(IOException e) {
			throw new JspException(e);		
		}
	    
		return EVAL_BODY_INCLUDE;
	}
	
	private void printCarTable(JspWriter out, ResultSet resultSet) throws SQLException, IOException {
		
		out.print("<table class = \"cartable\">");
		out.print("<caption>List of cars</caption>");
		out.print("<tr>");
		out.print("<th>Lot #</th><th>Model</th><th>Year</th><th>Start price</th>" + 
				"<th>Current price</th><th>Date to</th>");
		out.print("</tr>");
		while(resultSet.next()) {
			out.print("<tr>");
			out.print("<td>" + resultSet.getString("car_id") +"</td>");
			out.print("<td>" + resultSet.getString("model") +"</td>");
			out.print("<td>" + SQLConnection.convertSQLDate(resultSet.getDate("c_year")) +"</td>");
			out.print("<td>" + resultSet.getString("min_price") +"</td>");
			out.print("<td>" + resultSet.getString("cost") +"</td>");
			out.print("<td>" + resultSet.getString("date_active") +"</td>");
			if(pageContext.getSession().getAttribute("owner")!= null) {
				if(pageContext.getSession().getAttribute("owner").toString().equals(resultSet.getString("c_owner"))) {
					out.print("<td>Your car</td>");
				}else {
				printBetForm(out, Double.parseDouble(resultSet.getString("cost")), resultSet.getString("car_id"));
				}
			}
			out.print("</tr>");
		}
		out.print("</table>");
	}
	
	private void printBetForm(JspWriter out, double lastBetCost, String carId) throws IOException {
		out.print("<td><form method = \"POST\" action = \"BetServlet\">");
		out.print("<input type=\"hidden\" name=\"carId\" value=\"" + carId +"\">");
		out.print("<input type=\"number\" step=\"0.01\" name=\"price\" min = \"" + (lastBetCost + 100.0) + "\" required>");
		out.print("<input class = \"tablebtn\" type=\"submit\" value=\"Make bet\">");
		out.print("</form></td>");
	}

	public String getMinprice() {
		return minprice;
	}

	public void setMinprice(String minprice) {
		this.minprice = minprice;
	}

	public String getMaxprice() {
		return maxprice;
	}

	public void setMaxprice(String maxprice) {
		this.maxprice = maxprice;
	}
}