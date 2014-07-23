<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>OTA Manage</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
	
	<!-- Le styles -->
	<link href="css/bootstrap.css" rel="stylesheet"/>
	<link href="css/font-awesome.min.css" rel="stylesheet"/>
	<link href="css/style.css" rel="stylesheet"/>
	<link href="css/freesale.css"  rel="stylesheet"/>
	
	<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
	<!--[if lt IE 9]>
      <script src="../assets/js/html5shiv.js"></script>
    <![endif]-->

</head>

<body>
<div class="navbar">
	<div class="navbar-inner">
		<div class="container-fluid">
			<a class="brand" href="#">OTA Manage</a>
			
			<p class="navbar-text pull-right"><a href="<%=request.getContextPath()%>/templates/account-create.jsp" class="btn btn-primary">Create account</a></p>
		</div>
	</div>
</div>
<div id="body">
	<div class="new-user">
		<h3>Welcome to OTA Manage, please login.</h3>
		<% if(request.getParameter("causa") != null) { %>
			<% if(request.getParameter("causa").equalsIgnoreCase("wrongpass")){ %>
				<div class="loginError">
					Incorrect Username or Password.. Please try again.
				</div>
			<% } %>
			<% if(request.getParameter("causa").equalsIgnoreCase("logout")){ %>
				<div class="logoutCorrect">
					You've been logged out successfully.
					<%  request.getSession().invalidate(); %>
				</div>
			<% } %>
		<% } %>
		<form action="<c:url value='j_spring_security_check' />"  method='POST'>
			<fieldset class="with-ico">
				<label>Email</label>
				<input type="text"  name="j_username" value="">
				<span class="icon-envelope-alt"></span>
			</fieldset>
			<fieldset class="with-ico">
				<label>Password</label>
				<input type="password" value="" name="j_password">
				<span class="icon-lock"></span>
			</fieldset>
			<fieldset class="submit">
				<input type="submit" value="Login" class="btn btn-danger btn-large">
				<p class="note"><a href="#">Forgot my password.</a></p>
			</fieldset>
		</form>
	</div>
</div>
<!--/.fluid-container--> 

<script src="http://code.jquery.com/jquery-1.8.3.min.js"></script> 
<script src="js/bootstrap.min.js"></script> 
<script src="js/plugins.js"></script> 
<script src="js/calendar.js"></script> 
<script src="js/main.js"></script>
</body>
</html>
