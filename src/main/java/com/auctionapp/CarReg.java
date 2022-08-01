package com.auctionapp;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.classes.SQLConnection;


@WebServlet("/CarReg")
public class CarReg extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		boolean isOnSale;
		if(request.getParameter("isactive") != null) isOnSale = true;
		else isOnSale = false;
		int carId = addCar(request.getParameter("model"),
				Double.parseDouble(request.getParameter("price")),
				Integer.parseInt(request.getParameter("year")),
				isOnSale,
				Integer.parseInt(request.getSession().getAttribute("owner").toString()));
		request.setAttribute("carId", carId);
		if(isOnSale) {
			String path = "/BetServlet";
			request.setAttribute("isactive", 1);
			ServletContext servletContext = getServletContext();
	        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
	        requestDispatcher.forward(request, response);
		}else {
			String path = request.getContextPath() + "/UserPage.jsp";
            response.sendRedirect(path);
		}
	}
	
	private int addCar(String model, double minPrice, int year, boolean isActive, int ownerId) {
		if(SQLConnection.getConn() != null) {
			String command1 = "INSERT INTO cars (model, min_price, c_year, is_active, date_active, c_owner) "
							+ "Values (?, ?, ?, ?, ?, ?)";
			String command2 = "SELECT MAX(car_id) FROM cars";
			PreparedStatement preparedStatement;
			try {
				preparedStatement = SQLConnection.getConn().prepareStatement(command1);
				preparedStatement.setString(1, model);
				preparedStatement.setDouble(2, minPrice);
				preparedStatement.setInt(3, year);
				preparedStatement.setBoolean(4, isActive);
				preparedStatement.setTimestamp(5, null);
				preparedStatement.setInt(6, ownerId);
				int rows = preparedStatement.executeUpdate();
				preparedStatement = SQLConnection.getConn().prepareStatement(command2);
				ResultSet resultSet = preparedStatement.executeQuery();
				if(resultSet.next()) {
					return resultSet.getInt(1);
				}
			} catch (SQLException ex) {
				System.out.println(ex);
			}
			
		}else {
			System.out.println("Error");
		}
		return -1;
	}
}
