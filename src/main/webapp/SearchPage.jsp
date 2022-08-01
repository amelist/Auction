<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix = "aat" uri = "auctionapptags" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    %>
    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="style/table-style.css" type="text/css">
<link rel="stylesheet" href="style/position.css" type="text/css">
<link rel="stylesheet" href="style/button-style.css" type="text/css">
<title>Search car on auction</title>
</head>
<body>
	<aat:dbupdatesqript></aat:dbupdatesqript>
	<div class = "header">
	<form class = "searchform" method = "GET" action = "SearchPage.jsp">
	<table>
		<tr>
			<td><label for="minprice">Min. price:</label></td>
			<td><input type="number" step="0.01" id="minprice" name="mnprice" value = "<%= request.getParameter("mnprice") %>"><br></td>
			<td><label for="maxprice">Max. price:</label></td>
			<td><input type="number" step="0.01" id="maxprice" name="mxprice" value = "<%= request.getParameter("mxprice") %>"><br></td>
			<td><input class="btn" type="submit" value="Search"><br></td>
		</tr>
	</table>
	</form>
	<c:if test = "${sessionScope.owner == null}">
		<form class = "funcform" action = "LoginPage.html" method = "POST"><input class="btn" type="submit" value="Login"></form>
		<form class = "funcform" action = "UserReg.html" method = "POST"><input class="btn" class = "funcbutton" type="submit" value="Register"></form>
	</c:if>
	<c:if test = "${sessionScope.owner != null}">
		<form class = "funcform" action = "LogOutServlet" method = "POST"><input class="btn" type="submit" value="Log Out"></form>
		<form class = "funcform" action = "UserPage.jsp" method = "POST"><input class="btn" type="submit" value="My profile"></form>
	</c:if>
	</div>
	<div class = "tableblock">
	<aat:carout minprice = '<%= request.getParameter("mnprice") %>' maxprice = '<%= request.getParameter("mxprice") %>'>
	</aat:carout>
	</div>
</body>
</html>