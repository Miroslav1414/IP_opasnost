<%@page import="etf.ip.projektni.beans.UserBean"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<jsp:useBean id="registrovaniKorisnik"	type="etf.ip.projektni.beans.UserBean" scope="session" />
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Izmjena podataka</title>

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

			<div class="col-12 col-sm-12 col-md-12 col-lg-8 col-xl-6 mx-auto">
				<form method="POST" action="?action=izmjenaPodataka"
					enctype="multipart/form-data">
					
					<div class="d-flex justify-content-center align-items-center">
						<h3>Izmjena podataka</h3>					
					</div>
					<hr style="height: 20px;">

					<div>
						<% 
						if (registrovaniKorisnik.getUser().getProfilePicturex() != null)
							out.println("<img src=\"data:image/jpg;base64," + registrovaniKorisnik.getUser().getProfilePicturex() + "\" class=\"avatar\"/>");
						else if (registrovaniKorisnik.getUser().getFlag() != null)
							out.println("<img src=\"" +registrovaniKorisnik.getUser().getFlag() + "\" class=\"avatar\" />");
						else
							out.println("<img src=\"pictures\\noprofile.jpg\" class=\"avatar\" />");
						%>
						<a class="btn btn-primary" href="?action=promjenaProfilneSlike">Izmjeni sliku</a> 
						
					</div>

					<div class="form-group">
						<label for="email">E-mail</label> 
						<input type="email" class="form-control" id="email" name="email" maxlength="100"
							value="<%= registrovaniKorisnik.getUser().getEmail()%>"
							disabled="disabled">
					</div>

					<div class="form-group">
						<label for="uesrname">Korisnicko ime</label> 
						<input type="text"
							class="form-control" id="username" name="username" maxlength="20"
							value="<%= registrovaniKorisnik.getUser().getUsername() %>"
							disabled="disabled">
					</div>

					<div class="form-group">
						<label for="firstname">Ime</label> 
						<input type="text"
							class="form-control" id="firstname" name="firstname"
							value="<%= registrovaniKorisnik.getUser().getFirstName()%>"
							maxlength="50" required="required">
					</div>
					<div class="form-group">
						<label for="lastname">Prezime</label> 
						<input type="text"
							class="form-control" id="lastname" name="lastname" maxlength="50"
							value="<%= registrovaniKorisnik.getUser().getLastName()%>"
							required="required">
					</div>

					<div class="form-group">
						<label>Drzava</label>
						<select class="form-control" id="country" name="country"
							onchange="ucitajRegije()" required>
							<option>Izaberite drzavu</option>
						</select>
					</div>

					<div class="form-group">
						<input type="text" class="form-control" id="flag" name="flag"
							hidden="true">
					</div>

					<div class="form-group">
						<label>Regija</label>
						<select class="form-control" id="region" name="region"
							onchange="ucitajGradove()" disabled="true"
							value="<%= registrovaniKorisnik.getUser().getState()%>">
							<option>Izaberite regiju</option>
						</select>
					</div>

					<div class="form-group">
						<label>Grad</label>
						<select class="form-control" id="city" name="city" disabled="true">
							<option>Izaberite grad</option>
						</select>
					</div>

					<div class="form-group">
						<input type="text" class="form-control" id="country2"
							name="country2" hidden="true"
							value="<%= registrovaniKorisnik.getUser().getState()%>">
					</div>

					<div class="form-group">
						<input type="text" class="form-control" id="region2"
							name="region2" hidden="true"
							value="<%= registrovaniKorisnik.getUser().getRegion()%>">
					</div>

					<div class="form-group">
						<input type="text" class="form-control" id="city2" name="city2"
							hidden="true"
							value="<%= registrovaniKorisnik.getUser().getCity()%>">
					</div>

					<div class="form-check">
						<input type="checkbox" class="form-check-input"
							id="obavjestenjaEmail" name="obavjestenjaEmail"
							<%=registrovaniKorisnik.getUser().getEmailNotification() !=null? "checked":""%>>
						<label class="form-check-label" for="obavjestenjaEmail">Primanje
							notifikacija o hitnim slucajevima na email</label>
					</div>

					<div class="form-check">
						<input type="checkbox" class="form-check-input"
							id="obavjestenjaApp" name="obavjestenjaApp"
							<%=registrovaniKorisnik.getUser().getAppNotification() !=null? "checked":""%>>
						<label class="form-check-label" for="obavjestenjaApp">Primanje
							notifikacija o hitnim slucajevima unutar aplikacije</label>
					</div>


					<div class="d-flex justify-content-center">
						<button type="submit" class="btn btn-primary" style="width: 100%; margin-bottom: 50px; margin-top: 10px;">Sacuvaj</button>
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
	<script src="js/ValidacijaPolja.js"></script>
	<script src="js/RestZaDrzave.js"></script>
</body>
</html>