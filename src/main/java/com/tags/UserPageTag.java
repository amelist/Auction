package com.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.classes.SQLConnection;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserPageTag extends BodyTagSupport{
	
	private static final long serialVersionUID = 5215876733190616646L;
	private String userId;
	
	@Override
	public int doStartTag() throws JspException {
		
		JspWriter out=pageContext.getOut();
		try {
			if(SQLConnection.getConn() != null) {
				String command1 = "SELECT username FROM users WHERE user_id = ?";
				String command2 = "SELECT * FROM cars WHERE c_owner = ?";
				String command3 = "SELECT car_id, c_owner, model, min_price, c_year, date_active, cost, "
						+ "(SELECT MAX(cost) FROM bets WHERE bets.bet_car = cars.car_id) AS maxbet FROM cars "
						+ "JOIN bets ON bets.bet_car = cars.car_id "
						+ "WHERE cost = (SELECT MAX(cost) FROM bets WHERE bets.bet_car = cars.car_id AND bets.bet_user = ?)";
				PreparedStatement preparedStatement;
				try {
					preparedStatement = SQLConnection.getConn().prepareStatement(command1);
					preparedStatement.setString(1, userId);
					printUserInfo(out, preparedStatement.executeQuery());
					preparedStatement = SQLConnection.getConn().prepareStatement(command2);
					preparedStatement.setString(1, userId);
					out.print("<form action = \"CarReg.html\" method = \"POST\"><input type=\"submit\" value=\"Add your car for sale\"></form>"
							+ "<form action = \"SearchPage.jsp\" method = \"POST\"><input type=\"submit\" value=\"Continue search\"></form>"
							+ "<form action = \"LogOutServlet\" method = \"POST\"><input type=\"submit\" value=\"Log Out\"></form>");
					printUserCars (out, preparedStatement.executeQuery());
					preparedStatement = SQLConnection.getConn().prepareStatement(command3);
					preparedStatement.setString(1, userId);
					printUserBets(out, preparedStatement.executeQuery());
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
	
	private void printUserInfo (JspWriter out, ResultSet resultSet) throws SQLException, IOException{	
		if(resultSet.next()){
			String username = resultSet.getString("username");
			out.print("Welcome, " + username + "!");
		}
	}
	
	private void printUserCars (JspWriter out, ResultSet resultSet) throws SQLException, IOException{
		out.print("<table class = \"cartable\">");
		out.print("<caption>Your cars</caption>");
		out.print("<tr>");
		out.print("<th>Lot #</th><th>Model</th><th>Start price</th><th>Year</th><th>Max Bet</th>");
		out.print("</tr>");
		while(resultSet.next()) {
			out.print("<tr>");
			out.print("<td>" + resultSet.getString("car_id") +"</td>");
			out.print("<td>" + resultSet.getString("model") +"</td>");
			out.print("<td>" + resultSet.getString("min_price") +"</td>");
			out.print("<td>" + SQLConnection.convertSQLDate(resultSet.getDate("c_year")) +"</td>");
			out.print("<td>" + printOnSaleForm(resultSet.getInt("is_active"), resultSet.getString("car_id"), resultSet.getString("min_price")) +"</td>");
			out.print("</tr>");
		}
		out.print("</table>");
	}
	
	private void printUserBets(JspWriter out, ResultSet resultSet) throws SQLException, IOException{
		out.print("<table class = \"cartable\">");
		out.print("<caption>Your bets</caption>");
		out.print("<tr>");
		out.print("<th>Lot #</th><th>Model</th><th>Start price</th><th>Year</th><th>Date to</th><th>Your bet</th><th>Max bet</th>");
		out.print("</tr>");
		while(resultSet.next()) {
			if(!resultSet.getString("c_owner").equals(userId)) {
			out.print("<tr>");
			out.print("<td>" + resultSet.getString("car_id") +"</td>");
			out.print("<td>" + resultSet.getString("model") +"</td>");
			out.print("<td>" + resultSet.getString("min_price") +"</td>");
			out.print("<td>" + SQLConnection.convertSQLDate(resultSet.getDate("c_year")) +"</td>");
			out.print("<td>" + resultSet.getString("date_active") +"</td>");
			out.print("<td>" + resultSet.getString("cost") +"</td>");
			out.print("<td>" + resultSet.getString("maxbet") +"</td>");
			printBetForm(out, Double.parseDouble(resultSet.getString("cost")), resultSet.getString("car_id"));
			out.print("</tr>");
			}
		}
	}
	
	private void printBetForm(JspWriter out, double lastBetCost, String carId) throws IOException {
		out.print("<td><form method = \"POST\" action = \"BetServlet\">");
		out.print("<input type=\"hidden\" name=\"carId\" value=\"" + carId +"\">");
		out.print("<input type=\"number\" step=\"0.01\" name=\"price\" min = \"" + (lastBetCost + 100.0) + "\" required>");
		out.print("<input class=\"tablebtn\" type=\"submit\" value=\"Make bet\">");
		out.print("</form></td>");
	}
	
	private String printOnSaleForm(int isOnSale, String carId, String minPrice)
	{
		if (isOnSale == 1) {
			if(SQLConnection.getConn() != null) {
				String command = "SELECT MAX(cost) FROM bets WHERE bet_car = ?";
				PreparedStatement preparedStatement;
				try {
					preparedStatement = SQLConnection.getConn().prepareStatement(command);
					preparedStatement.setString(1, carId);
					ResultSet rs = preparedStatement.executeQuery();
					if(rs.next()) {
						return rs.getString(1);
						}
					}catch (SQLException ex){
						System.out.println(ex);
					}		
			}else {
				System.out.println("SQL error");
			}
			return "error";
		}
		if (isOnSale == 0) {
			return "<form method = \"POST\" action = \"BetServlet\">"
					+ "<input type=\"hidden\" name=\"carId\" value=\"" + carId +"\">"
					+ "<input type=\"hidden\" name=\"price\" value=\"" + minPrice +"\">"
					+ "<input type=\"hidden\" name=\"owner\" value=\"" + userId +"\">"
					+ "<input type=\"hidden\" name=\"isactive\" value=\"" + 1 +"\">"
					+ "<input class=\"tablebtn\" type=\"submit\" value=\"Set on sale\"></form>";
		}
		return "error";
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
