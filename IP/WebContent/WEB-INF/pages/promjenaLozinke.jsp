<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

<link rel="stylesheet" type="text/css"	href="bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="css/nekeklase.css">
	<script src="bootstrap/js/jquery.js"></script>
	<script src="bootstrap/js/popper.min.js"></script>
	<script src="bootstrap/js/bootstrap.js"></script>
	<script src="js/ValidacijaPolja.js"></script>
</head>
<body>
	<div class=header>
		<%@include file="parts/header.jsp"%>
	</div>
	<div>
		<%@include file="parts/navbar.jsp"%>
	</div>
	<div class="container-fluid">
		<div class="row">
			<div class="col-xl-4 col-md-3 col-sm-12 col-xs-12"></div>
			<div class="col-xl-4 col-md-6 col-sm-12 col-xs-12">
				<form method="POST" action="?action=izmjeniLozinu">
				
					<div class="d-flex justify-content-center align-items-center">
						<h3>Izmjena lozinke</h3>					
					</div>
					<hr style="height: 20px;">

					
					<div class="form-group">
						<label class="d-flex justify-content-center align-items-center" for="password"> Nova lozinka</label> <input type="password"
							class="form-control" id="password" name="password" required="required"
							onblur="checkPassword()" maxlength="20">
					</div>
					<div class="form-group">
						<label class="d-flex justify-content-center align-items-center" for="password2">Ponovi novu lozinku</label> <input
							type="password" class="form-control" id="password2"
							name="password2" required="required"
							onkeyup="checkSeccondPassword()" maxlength="20">
					</div>
					<div class="d-flex justify-content-center">
						<button type="submit" class="btn btn-primary">Izmjeni</button>
					</div>

					<%=session.getAttribute("notification")!=null? session.getAttribute("notification").toString():""%>
				</form>
			</div>

		</div>
	</div>

</body>
</html>