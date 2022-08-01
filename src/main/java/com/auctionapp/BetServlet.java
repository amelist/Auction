package com.auctionapp;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.classes.SQLConnection;

@WebServlet("/BetServlet")
public class BetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		if(request.getAttribute("carId") == null) request.setAttribute("carId", request.getParameter("carId"));
		int rows = setActive(Integer.parseInt(request.getAttribute("carId").toString()));
		rows = addBet(Double.parseDouble(request.getParameter("price")),
				Integer.parseInt(request.getAttribute("carId").toString()),
				Integer.parseInt(request.getSession().getAttribute("owner").toString()));
		String path = request.getContextPath() + "/UserPage.jsp";
		response.sendRedirect(path);
	}
	
	private int addBet(double cost, int carId, int ownerId) {
		if(SQLConnection.getConn() != null) {
			String command = "INSERT INTO bets(betdate, cost, bet_user, bet_car) Values (?, ?, ?, ?)";
			PreparedStatement preparedStatement;
			try {
				preparedStatement = SQLConnection.getConn().prepareStatement(command);
				preparedStatement.setTimestamp(1, new java.sql.Timestamp(new Date().getTime()));
				preparedStatement.setDouble(2, cost);
				preparedStatement.setInt(3, ownerId);
				preparedStatement.setInt(4, carId);
				return preparedStatement.executeUpdate();
			}catch (SQLException ex) {
				System.out.println(ex);
			}
		}else {
			System.out.println("Error");
		}
		return -1;
	}
	
	private int setActive(int carId) {
		System.out.println(carId);
		if(SQLConnection.getConn() != null) {
			String command = "UPDATE cars SET is_active = ?, date_active = ? WHERE car_id = ?";
			PreparedStatement preparedStatement;
			try {
				preparedStatement = SQLConnection.getConn().prepareStatement(command);
				preparedStatement.setBoolean(1, true);
				preparedStatement.setTimestamp(2, setDateActive());
				preparedStatement.setInt(3, carId);
				return preparedStatement.executeUpdate();
			}catch (SQLException ex) {
				System.out.println(ex);
			}
		}else {
			System.out.println("Error");
		}
		return -1;
	}
	
	private java.sql.Timestamp setDateActive()
	{
		Calendar nowIs = new GregorianCalendar();
		nowIs.add(Calendar.DAY_OF_MONTH, 3);
		java.sql.Timestamp temp = new java.sql.Timestamp(nowIs.getTime().getTime());
		return temp;
	}
}
