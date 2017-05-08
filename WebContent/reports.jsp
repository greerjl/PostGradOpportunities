<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-theme-teal.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <style>
  .modal-header, h4, .close {
      color:white !important;
      text-align: center;
      font-size: 30px;
  }
  .modal-footer {
      background-color: #f9f9f9;
  }
  tr:nth-child(even) {background-color: #f2f2f2}
  </style>
<title>Company</title></head>
<body>
<jsp:useBean id="myUtil" class="dbUtil.Utilities" scope="session"></jsp:useBean>

<!-- Navbar -->
<div class="w3-top">
 <div class="w3-bar w3-theme-d2 w3-left-align">
  <a class="w3-bar-item w3-button w3-hide-medium w3-hide-large w3-right w3-hover-white w3-theme-d2" href="javascript:void(0);" onclick="openNav()"><i class="fa fa-bars"></i></a>
  <a href="index.jsp" class="w3-bar-item w3-button w3-teal"><i class="fa fa-home w3-margin-right"></i>Company</a>
  <button type="button" class="w3-bar-item w3-button w3-hide-small w3-hover-white" id="myBtn">Log In </button>
  <a href="#" class="w3-bar-item w3-button w3-hide-small w3-hover-white">Reports</a>
  <a href="insert.jsp" class="w3-bar-item w3-button w3-hide-small w3-hover-white">Insert</a>
  <a href="update.jsp" class="w3-bar-item w3-button w3-hide-small w3-hover-white">Update</a>
  <a href="delete.jsp" class="w3-bar-item w3-button w3-hide-small w3-hover-white">Delete</a>
  <a href="#" class="w3-bar-item w3-button w3-hide-small w3-hover-white">Log Out</a>
 </div>
</div>
<div class="container ">
  <!-- Modal -->
  <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header w3-teal" style="padding:35px 50px;">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="w3-teal"><span class="glyphicon glyphicon-lock w3-teal"></span> Login</h4>
        </div>
        <div class="modal-body" style="padding:40px 50px;">
          <form action="loginHandler.jsp" role="form">
            <div class="form-group">
              <h5> Using Database: ff367 </h5>
              <label for="usrname"><span class="glyphicon glyphicon-user"></span> Username</label>
              <input type="text" class="form-control" name="usrname" id="usrname" placeholder="Enter email">
            </div>
            <div class="form-group">
              <label for="psw"><span class="glyphicon glyphicon-eye-open"></span> Password</label>
              <input type="password" class="form-control" name="psw" id="psw" placeholder="Enter password">
            </div>
              <button type="submit" class="btn btn-success btn-block w3-teal"><span class="glyphicon glyphicon-off"></span> Login</button>
          </form>
        </div>
      </div>    
    </div>
  </div> 
</div>
 
<!-- Header -->
<header class="w3-container w3-theme w3-padding" id="myHeader">
  <i onclick="w3_open()" class="fa fa-bars w3-xlarge w3-button w3-theme"></i> 
  <div class="w3-center">
  <h1 class="w3-xxxlarge w3-animate-bottom">Company Interface</h1>
  <h3 class="w3-large"> Reports</h3>
  </div>
</header>
<!-- Form -->
<div class="w3-row-padding w3-center">
	<div class="w3-half" style="margin: 0 auto; margin-top:20px; width: 100%;">
	<form class="w3-container w3-card-4">
	  <h2>Employees work on project controlled by department</h2>
	  <div class="w3-section w3-center">      
	    <input class="w3-input" name="dnum" type="text" required>
	    <label>Department number:  </label>
	  </div>

	  <button type="submit">Submit</button>
	</form>
	</div>
</div>

<!-- Form -->
<div class="w3-row-padding w3-center">
	<div class="w3-half" style="margin: 0 auto; margin-top:20px; margin-bottom: 20px; width: 100%;">
	<form class="w3-container w3-card-4">
	  <h2>Employees that work on project with</h2>
	  <div class="w3-section w3-center">      
	    <input class="w3-input" name="fname" type="text" required>
	    <label>First Name:  </label>
	    <input class="w3-input" name="lname" type="text" required>
	    <label>Last Name:  </label>
	  </div>

	  <button type="submit">Submit</button>
	</form>
	</div>
</div>


<%
	//For Employees work on project controlled by department(dno)
   try {
	String dnumber = request.getParameter("dnum");
	int dnum = Integer.parseInt(dnumber);
	ResultSet rs = myUtil.employeeOnProjectByDNO(dnum);
	%>
	<TABLE cellpadding="15" border="1" style="margin: 0 auto; margin-bottom:50px; width: 90%;">
	<%
	while(rs.next()) { %>
		<tr> <th> Last Name </th> <th> First Name </th> <th> PNO </th> <th> Hours </th></tr>
		<tr>
		<td> <%=rs.getString(1) %></td>
		<td> <%=rs.getString(2) %></td>
		<td> <%=rs.getInt(3) %></td>
		<td> <%=rs.getDouble(4)  %>
		</tr>	<% 
	}//while
	%></TABLE>
	<% 
   } catch(NumberFormatException n){

   }

	// For Employees that work on project with(fname, lname)
	try {
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		ResultSet rs = myUtil.worksWith(fname, lname);
		%>
		<TABLE cellpadding="15" border="1" style="margin: 0 auto; margin-bottom:50px; width: 90%;">
		<%
		while(rs.next()) { %>
			<tr> <th> First Name </th> <th> Last Name </th> <th> Salary </th> <th> DNO </th></tr>
			<tr>
			<td> <%=rs.getString(1) %></td>
			<td> <%=rs.getString(2) %></td>
			<td> $<%=rs.getInt(3) %></td>
			<td> <%=rs.getInt(4)  %>
			</tr>	<% 
		}//while
		%></TABLE>
		<% 
	   } catch(NumberFormatException n){

	   }
	
	
%>

<!-- Footer -->
<footer class="w3-container w3-theme-dark">
  <h3>Footer</h3>
  <div style="position:relative;bottom:55px;" class="w3-tooltip w3-right">
    <span class="w3-text w3-theme w3-padding">Go To Top</span>&nbsp;   
    <a class="w3-text-white" href="#myHeader"><span class="w3-xlarge">
    <i class="fa fa-chevron-circle-up"></i></span></a>
  </div>
  <p>Created by Jayme Greer for CSCI 367 Spring 2017 using W3schools Templates</p>
</footer>

<!-- Script for Sidebar-->
<script>
// Side navigation
function w3_open() {
    var x = document.getElementById("mySidebar");
    x.style.width = "100%";
    x.style.fontSize = "40px";
    x.style.paddingTop = "10%";
    x.style.display = "block";
}

function w3_close() {
    document.getElementById("mySidebar").style.display = "none";
}

$(document).ready(function(){
    $("#myBtn").click(function(){
        $("#myModal").modal();
    });
});
</script>
</body>
</html>