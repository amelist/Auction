package com.auctionapp;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.classes.*;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(SQLConnection.getConn() != null) {
			request.setCharacterEncoding("UTF-8");
			String login = request.getParameter("login");
			String password = request.getParameter("pass");
			int ownerId = findUser(login, password);
			if(ownerId == -1) {
				String path = request.getContextPath() + "/LoginPage.html";
				response.sendRedirect(path);
			}else {
			String path = request.getContextPath() + "/UserPage.jsp";
			HttpSession session = request.getSession();
			session.setAttribute("owner", ownerId);
			session.setMaxInactiveInterval(60*30);
			response.sendRedirect(path);
			}
		}
		else {
			System.out.println("Error");		}
	}
	
	private int findUser(String login, String pass) {
		if(SQLConnection.getConn() != null) {
			String command = "SELECT user_id FROM users WHERE login = ? AND us_pass = ?";
			PreparedStatement preparedStatement;
			try {
				preparedStatement = SQLConnection.getConn().prepareStatement(command);
				preparedStatement.setString(1, login);
				preparedStatement.setString(2, pass);
				ResultSet resultSet = preparedStatement.executeQuery();
				if(resultSet.next()) {
					return resultSet.getInt("user_id");
				}
			}catch (SQLException ex){
				System.out.println(ex);
			}
			
		}else {
			System.out.println("Error");
		}
		return -1;
	}
}
