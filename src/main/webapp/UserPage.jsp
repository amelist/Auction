<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "aat" uri = "auctionapptags" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="style/table-style.css" type="text/css">
<link rel="stylesheet" href="style/position.css" type="text/css">
<link rel="stylesheet" href="style/button-style.css" type="text/css">
<title>User's page</title>
</head>
<body>
	<aat:userpage userId = '<%=session.getAttribute("owner").toString()%>'/>
</body>
</html>