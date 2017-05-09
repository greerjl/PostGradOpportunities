<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  %>
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
      background-color: #5cb85c;
      color:white !important;
      text-align: center;
      font-size: 30px;
  }
  .modal-footer {
      background-color: #f9f9f9;
  }
  </style>
<title>Future Finders</title>
</head>

<body>
<!-- Navbar -->
<div class="w3-top">
 <div class="w3-bar w3-theme-d2 w3-left-align">
  <a class="w3-bar-item w3-button w3-hide-medium w3-hide-large w3-right w3-hover-white w3-theme-d2" href="javascript:void(0);" onclick="openNav()"><i class="fa fa-bars"></i></a>
  <a href="#" class="w3-bar-item w3-button w3-teal"><i class="fa fa-home w3-margin-right"></i>Company</a>
  <button type="button" class="w3-bar-item w3-button w3-hide-small w3-hover-white" id="myBtn">Log In </button>
  <a href="reports.jsp" class="w3-bar-item w3-button w3-hide-small w3-hover-white">Reports</a>
  <a href="insert.jsp" class="w3-bar-item w3-button w3-hide-small w3-hover-white">Insert</a>
  <a href="update.jsp" class="w3-bar-item w3-button w3-hide-small w3-hover-white">Update</a>
  <a href="delete.jsp" class="w3-bar-item w3-button w3-hide-small w3-hover-white">Delete</a>
  <a href="logout.jsp" class="w3-bar-item w3-button w3-hide-small w3-hover-white">Log Out</a>
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
          <form action="loginHandler.jsp" role="form" method="POST">
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
  <h1 class="w3-xxxlarge w3-animate-bottom">Future Finders Interface</h1>
  </div>
</header>

<!-- Body -->
<div class="w3-row-padding w3-center w3-margin-top">
<div class="w3-third">
  <div class="w3-card-2 w3-container" style="min-height:460px">
  <h3>Reports</h3><br>
  <i class="fa fa-bar-chart w3-margin-bottom w3-text-theme" style="font-size:120px"></i>
  <p>Create lists based off of parameters</p>
  <p>Friendly view for understanding</p>
  <p>Aides in data analysis</p>
  </div>
</div>

<div class="w3-third">
  <div class="w3-card-2 w3-container" style="min-height:460px">
  <h3>Insert</h3><br>
  <i class="fa fa-plus-square w3-margin-bottom w3-text-theme" style="font-size:120px"></i>
  <p>Add new data easily</p>
  <p>Select table to add</p>
  <p>No need to know SQL</p>
  </div>
</div>

<div class="w3-third">
  <div class="w3-card-2 w3-container" style="min-height:460px">
  <h3>Update</h3><br>
  <i class="fa fa-pencil-square-o w3-margin-bottom w3-text-theme" style="font-size:120px"></i>
  <p>Change data with ease</p>
  <p>Quickly update when new data arrives</p>
  <p>Available for all tables</p>
  </div>
</div>
</div>
<br><br><br>
<div class="w3-center">
	<header class="w3-container w3-teal">
		<h2>About</h2>
	</header>
	<div class="w3-padding w3-white w3-display-container">
  		<p>This interface is used to connect to the Future Finders database: ff367. </p>
  		<p>Once connected, the user can fill out forms to insert, delete or modify what is currently stored.</p>
  		<p>We also have the capability to make complex reports so you can find exactly what you are looking for.</p>
	</div>	
</div>

<br>
<br>
<br>

<!-- Footer -->
<footer class="w3-container w3-theme-dark">
  <br>
  <%--<h3>Footer</h3>--%>
  <div style="position:relative;bottom:55px;" class="w3-tooltip w3-right">
    <span class="w3-text w3-theme w3-padding">Go To Top</span>&nbsp;   
    <a class="w3-text-white" href="#myHeader"><span class="w3-xlarge">
    <i class="fa fa-chevron-circle-up"></i></span></a>
  </div>
  <p>Created by Ryan Bolen, Jayme Greer, Carson Ketter, Nathan Kosylo, and Caleb Ice for CSCI 367 Spring 2017 using W3Schools Templates</p>
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