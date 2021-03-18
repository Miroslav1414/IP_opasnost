<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<jsp:useBean id="registrovaniKorisnik"
	type="etf.ip.projektni.beans.UserBean" scope="session" />
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Izmjena profilne slike</title>
<link rel="stylesheet" type="text/css"
	href="bootstrap/css/bootstrap.css">
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
			<div class="col-xl-4 col-md-6 col-sm-12 col-xs-12">
			
			<div class="d-flex justify-content-center align-items-center">
				<h3>Izmjena profilne slike</h3>					
			</div>
			<hr style="height: 20px;">

				<form action="?action=izmjeniSliku" method="post"
					enctype="multipart/form-data">
					<% 
						if (registrovaniKorisnik.getUser().getProfilePicturex() != null && registrovaniKorisnik.getUser().getProfilePicturex().length() >1){
							
							out.println("<img  src=\"data:image/jpg;base64," + registrovaniKorisnik.getUser().getProfilePicturex() + "\" ");
							
						}
							
						else if (registrovaniKorisnik.getUser().getFlag() != null)
							out.println("<img src=\"" +registrovaniKorisnik.getUser().getFlag() + "\"");
						else
							out.println("<img src=\"pictures\\noprofile.jpg\"");
					%>
					id="profilnaSlika" alt="Profilna slika" name="profilnaSlika"
					class="avatar-change form-control mx-auto d-block"/> <input
						class="form-control " type="file" id="file" name="file" size="50"
						onchange="uploadSlike()" /> <br /> <input class="form-control btn btn-primary"
						type="submit" value="Upload File" />


				</form>
			</div>

		</div>
	</div>



	<script src="bootstrap/js/bootstrap.js"></script>
	<script src="bootstrap/js/jquery.js"></script>
	<script src="bootstrap/js/popper.min.js"></script>
	<script src="js/RestZaDrzave.js"></script>
</body>
</html>