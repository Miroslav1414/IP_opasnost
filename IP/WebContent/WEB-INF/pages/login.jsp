<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>




<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>

<link rel="stylesheet" type="text/css"	href="bootstrap/css/bootstrap.css">

</head>
<body>
	<div class=header>
		<%@include file="parts/header.jsp"%>
	</div>
	<div class="container-fluid">
		<div class="row">
			<div class="col-xl-4 col-md-3 col-sm-12 col-xs-12"></div>
			<div class="col-xl-4 col-md-6 col-sm-12 col-xs-12" >
				<form method="POST" action="?action=login">
				
				<div class="d-flex justify-content-center align-items-center">
					<h3>Prijava na sistem</h3>					
				</div>
				<hr style="height: 20px;">
					
					<div class="form-group ">
						<label class="d-flex justify-content-center align-items-center" for="uesrname">Korisnicko ime</label> 
						<input type="text"
							class="form-control" id="username" name="username"
							value="<%=session.getAttribute("username")!=null? session.getAttribute("username").toString():""%>">
					</div>
					<div class="form-group">
						<label class="d-flex justify-content-center align-items-center" for="password">Lozinka</label>
						<input type="password"
							class="form-control" id="password" name="password">
					</div>
					<div class="d-flex justify-content-center">
						<button type="submit" class="btn btn-primary" style="width: 100%;">Prijavi se</button>
					</div>
					<div class="form-group">
						<a href="?action=register">Registruj se</a>
					</div>
					<div class="d-flex justify-content-center align-items-center text-danger">
						<%=session.getAttribute("notification")!=null? session.getAttribute("notification").toString():""%>
					</div>
					
				</form>
			</div>

		</div>
	</div>




	<script src="bootstrap/js/jquery.js"></script>
	<script src="bootstrap/js/popper.min.js"></script>
	<script src="bootstrap/js/bootstrap.js"></script>
</body>
</html>