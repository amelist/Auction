package com.auctionapp;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.classes.SQLConnection;

@WebServlet("/UserReg")
public class UserReg extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public UserReg() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String login = request.getParameter("login");
		String password = request.getParameter("pass");
		String name = request.getParameter("username");
		createUser(login, password, name);
		String path = "/LoginServlet";
		ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
        requestDispatcher.forward(request, response);
	}
	
	private void createUser(String login, String pass, String username) {
		if(SQLConnection.getConn() != null) {
			String command = "INSERT INTO users (login, us_pass, username) Values (?, ?, ?)";
			PreparedStatement preparedStatement;
			try {
				preparedStatement = SQLConnection.getConn().prepareStatement(command);
				preparedStatement.setString(1, login);
				preparedStatement.setString(2, pass);
				preparedStatement.setString(3, username);
				int rows = preparedStatement.executeUpdate();
	            
	            System.out.printf("%d rows added", rows);
			} catch (SQLException ex) {
				System.out.println(ex);
			}
			
		}
		else {
			System.out.println("Error");
		}
	}

}
