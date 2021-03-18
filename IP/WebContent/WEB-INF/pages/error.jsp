<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link href="bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="css/nekeklase.css">
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
			<div class="col-xl-4 col-md-6 col-sm-12 col-xs-12" >
				Uputili ste nevalidan zahtjev!
			</div>
		</div>		
	</div>
</body>
</html>