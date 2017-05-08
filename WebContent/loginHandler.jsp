<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Log In Handler</title>
</head>
<body>
<jsp:useBean id="myUtil" class="dbUtil.Utilities" scope="session"></jsp:useBean>
<h1>Log In Form Handler</h1>

User Name: <%= request.getParameter("usrname") %> <br>
Password: <%= request.getParameter("psw") %>

<%
	String userName = request.getParameter("usrname");
	String password = request.getParameter("psw");
	//out.println("The current value of conn: " + myUtil.getConn() + "<br>");
	myUtil.openDB(userName, password);
	//out.println("The value of conn after open: " + myUtil.getConn()+ "<br>");
	//response.sendRedirect(request.getHeader("referer"));
%>
	<jsp:forward page="index.jsp" />
</body>
</html>