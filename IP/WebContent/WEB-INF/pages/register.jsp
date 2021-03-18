<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Register</title>
	
	<link href="bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet">
	<link href="css/nekeklase.css" type="text/css" rel="stylesheet">
	<script src="js/ValidacijaPolja.js"></script>
</head>
<body>

	<div class=header>
		<%@include file="parts/header.jsp"%>
	</div>
	<div>
		<%@include file="parts/navbarLogin.jsp"%>
	</div>
	
	<div class="container-fluid">
		<div class="row">
			<div class="col-xl-4 col-md-3 col-sm-12 col-xs-12"></div>
			<div class="col-xl-4 col-md-6 col-sm-12 col-xs-12">
				<form method="POST" action="?action=register2" >
					
					<div class="d-flex justify-content-center align-items-center">
						<h3>Registracija</h3>					
					</div>
					<hr style="height: 20px;">
					
					<div class="form-group">
						<label class="d-flex justify-content-center align-items-center" for="uesrname">Korisnicko ime</label> 
						<input type="text" class="form-control" id="username" name="username" maxlength="20"
							onblur="checkUsername()" required="required">
					</div>
					<div class="form-group">
						<label class="d-flex justify-content-center align-items-center" for="firstname">Ime</label>
						<input type="text" class="form-control" id="firstname" name="firstname"
							maxlength="50" onblur="checkFirstName()" required="required">
					</div>
					<div class="form-group">
						<label class="d-flex justify-content-center align-items-center" for="lastname">Prezime</label> 
						<input type="text" class="form-control" id="lastname" name="lastname" maxlength="50"
							onblur="checkLastName()" required>
					</div>
					<div class="form-group">
						<label class="d-flex justify-content-center align-items-center" for="password">Lozinka</label>
						<input type="password" class="form-control" id="password" name="password" maxlength="20"
							onblur="checkPassword()" required>
					</div>
					<div class="form-group">
						<label class="d-flex justify-content-center align-items-center" for="password2">Ponovite lozinku</label>
						 <input	type="password" class="form-control" id="password2"
							name="password2" maxlength="20" onkeyup="checkSeccondPassword()" required>
					</div>
					<div class="form-group">
						<label class="d-flex justify-content-center align-items-center" for="email">E-mail</label> 
						<input type="email"	class="form-control" id="email" name="email" maxlength="100"
							onkeyup="checkEmail()" required>
					</div>


					<div class="d-flex justify-content-center">
						<button type="submit" class="btn btn-primary" style="width: 100%;">Registruj se</button>
					</div>
					
					<div class="d-flex justify-content-center align-items-center text-danger">
						<%=session.getAttribute("notification")!=null? session.getAttribute("notification").toString():""%>
					</div>
				</form>
			</div>

		</div>
	</div>


	

</body>
</html>