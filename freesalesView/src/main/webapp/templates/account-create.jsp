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
	<link href="../css/bootstrap.css" rel="stylesheet">
	<link href="../css/font-awesome.min.css" rel="stylesheet">
	<link href="../css/style.css" rel="stylesheet">
	
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
			<p class="navbar-text pull-right"><a href="<%=request.getContextPath()%>/index.jsp" class="btn btn-primary">Login</a></p>
		</div>
	</div>
</div>
<div id="body">
	<div class="new-user">
		<h3>Create an account.</h3>
		<form action="#">
			<p>All fields are required and important. This text will go into 2 lines to show how it would look.</p>
			<fieldset class="with-ico">
				<label>Your name</label>
				<input type="text" value="">
				<span class="icon-user"></span>
			</fieldset>
			<fieldset class="with-ico">
				<label>Hotel</label>
				<input type="text" value="">
				<span class="icon-h-sign"></span>
			</fieldset>
			<fieldset class="with-ico">
				<label>Email</label>
				<input type="text" value="">
				<span class="icon-envelope-alt"></span>
			</fieldset>
			<fieldset class="with-ico">
				<label>Phone</label>
				<input type="text" value="">
				<span class="icon-phone"></span>
			</fieldset>
			<fieldset class="with-ico">
				<label>Password</label>
				<input type="text" value="">
				<span class="icon-lock"></span>
			</fieldset>
			<fieldset class="submit">
				<input type="submit" value="Create account" class="btn btn-danger btn-large">
				<p class="note">I agree to the terms and conditions.</p>
			</fieldset>
		</form>
	</div>
</div>
<!--/.fluid-container--> 

<script src="http://code.jquery.com/jquery-1.8.3.min.js"></script> 
<script src="../js/bootstrap.min.js"></script> 
<script src="../js/plugins.js"></script> 
<script src="../js/calendar.js"></script> 
<script src="../js/main.js"></script>
</body>
</html>
