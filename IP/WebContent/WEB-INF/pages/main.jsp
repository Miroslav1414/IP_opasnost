<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@page import="etf.ip.projektni.dto.VremenskaPrognoza"%>
<jsp:useBean id="registrovaniKorisnik"	type="etf.ip.projektni.beans.UserBean" scope="session" />
<jsp:useBean id="helperBean" type="etf.ip.projektni.beans.HelperBean"	scope="session" />

<html>
<head>
<meta charset="ISO-8859-1">
<title>GLAVNA STRANICA</title>


<link rel="stylesheet" type="text/css"
	href="bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="css/nekeklase.css">
<script src="bootstrap/js/jquery.js"></script>
<script src="bootstrap/js/popper.min.js"></script>
<script src="bootstrap/js/bootstrap.js"></script>
<script type="text/javascript" src="js/ajax.js"></script>
<script src="https://polyfill.io/v3/polyfill.min.js?features=default"></script>
<script
	src="https://maps.googleapis.com/maps/api/js?callback=initMap&libraries=&v=weekly"
	defer></script>

</head>
<body
	onload="getVijesti('<%out.print(registrovaniKorisnik.getUser().getUsername());%>',1)">

	<div class=header>
		<%@include file="parts/header.jsp"%>
	</div>

	<div>
		<%@include file="parts/navbar.jsp"%>
	</div>


	<div class="container-fluid">
		<div class="row">
			<div class="col-xl-2 col-md-3 col-sm-12 col-xs-12">

				<% 
				
						if (registrovaniKorisnik.getUser().getProfilePicturex() != null && registrovaniKorisnik.getUser().getProfilePicturex().length() >1){
							
							out.println("<img  src=\"data:image/jpg;base64," + registrovaniKorisnik.getUser().getProfilePicturex() + "\" ");
							
						}
							
						else if (registrovaniKorisnik.getUser().getFlag() != null)
							out.println("<img src=\"" +registrovaniKorisnik.getUser().getFlag() + "\"");
						else
							out.println("<img src=\"pictures\\noprofile.jpg\"");
					%>
				id="profilnaSlika" alt="nema slike" name="profilnaSlika"
				class="avatar-change form-control mx-auto d-block"/>
				<h3 class="text-center"><%=registrovaniKorisnik.getUser().getFirstName() %></h3>
				<h3 class="text-center"><%=registrovaniKorisnik.getUser().getLastName() %></h3>
				<h5 class="text-center">
					Broj prijava:
					<%=registrovaniKorisnik.getUser().getLoginCounter() %></h5>
				<div id="notifikacije" name="notifikacije"></div>

			</div>
			<div class="col-xl-8 col-md-7 col-sm-12 col-xs-12">
				<div class="" id="vijesti2">
				


					<nav class="navbar navbar-expand pozadina_posta" >
						<a class="nav-link" href="#" role="button" id="buttonLink" onclick="showLink()">Objavi link</a> 
						<a class="nav-link" href="#" role="button" id="buttonSlika" onclick="showSlika()">Objavi Tekst sa slikom</a>
						<a class="nav-link" href="#" role="button" id="buttonYT" onclick="showYT()">Objavi YouTube video</a> 
						<a class="nav-link" href="#" role="button" id="buttonVideo" onclick="showVideo()">Objavi svoj video</a>
					</nav>

					<div class="jumbotron" id="objavaLinka" style="display: none;">
						<div class="d-flex justify-content-center align-items-center text-danger">
							<p id = "link_poruka">
						</div>
						<div class="form-group">
							<label for="link_naslov">Naslov</label> <input
								class="form-control" id="link_naslov" name="link_naslov"
								type="text" maxlength="255">
						</div>
						<div class="form-group">
							<label for="link_link">Link</label> <input class="form-control"
								id="link_link" name="link_link" type="text" maxlength="400">
						</div>
						<div class="form-group">
							<a class="btn btn-success float-left" href="#" role="button"
								onclick="addLink('<%=registrovaniKorisnik.getUser().getUsername().toString()%>')">Objavi</a>
							<a class="btn btn-danger float-right" href="#" role="button"
								onclick="sakrijDivove()">Otkazi</a>
						</div>
						<div class="d-flex justify-content-center align-items-center text-danger">
							<p id = "link_poruka">
						</div>

					</div>

					<div class="jumbotron" id="objavaSlike" style="display: none;">
						<div class="d-flex justify-content-center align-items-center text-danger">
							<p id = "slika_poruka">
						</div>
						<div class="form-group">
							<label for="slika_naslov">Naslov</label> <input
								class="form-control" id="slika_naslov" name="slika_naslov"
								type="text" maxlength="255">
						</div>
						<div class="form-group">
							<label for="slika_tekst">Tekst:</label>
							<textarea class="form-control" rows="5" id="slika_tekst"
								name="slika_tekst" maxlength="5000"></textarea>
						</div>
						<div>
							<img src="" id="slika_slika" name="slika_slika" /> <input
								class="form-group" accept="image/x-png,image/gif,image/jpeg" type="file" name="slika_file" id="slika_file"
								size="50" onchange="uploadSlike('slika_slika','slika_file')" />
						</div>

						<a class="btn btn-success float-left" href="#" role="button"
							onclick="addTekstSaSlikom('<%=registrovaniKorisnik.getUser().getUsername().toString()%>')">Objavi</a>
						<a class="btn btn-danger float-right" href="#" role="button"
							onclick="sakrijDivove()">Otkazi</a>
					</div>

					<div class="jumbotron" id="objavaYTVidea" style="display: none;">
						<div class="d-flex justify-content-center align-items-center text-danger">
							<p id = "yt_poruka">
						</div>
						<div class="form-group">
							<label for="yt_naslov">Naslov</label> <input class="form-control"
								id="yt_naslov" name="yt_naslov" type="text">
						</div>
						<div class="form-group">
							<label for="yt_link">Link sa YouTube</label> <input
								class="form-control" id="yt_link" name="yt_link" type="text">
						</div>
						<a class="btn btn-success float-left" href="#" role="button"
							onclick="addYTVideo('<%=registrovaniKorisnik.getUser().getUsername().toString()%>')">Objavi</a>
						<a class="btn btn-danger float-right" href="#" role="button"
							onclick="sakrijDivove()">Otkazi</a>
					</div>

					<div class="jumbotron" id="objavaVidea" style="display: none;">
						<div class="d-flex justify-content-center align-items-center text-danger">
							<p id = "video_poruka">
						</div>
						<div class="form-group">
							<label for="video_naslov">Naslov</label> <input
								class="form-control" id="video_naslov" name="video_naslov"
								type="text">
						</div>
						<div>
							<input class="form-group" type="file" name="video_video"
								id="video_video" size="50" />
						</div>
						<a class="btn btn-success float-left" href="#" role="button"
							onclick="addVideo('<%=registrovaniKorisnik.getUser().getUsername().toString()%>')">Objavi</a>
						<a class="btn btn-danger float-right" href="#" role="button"
							onclick="sakrijDivove()">Otkazi</a>

					</div>


					<div style="display: none">
						<textarea rows="10" cols="10" id="tekst" name="tekst"></textarea>
						<br> <input id="youtube" name="youtube"> <br> <input
							type="file" id="file" name="file" size="50" /> <br>
					</div>



				</div>

				<div id="vijesti" class="container"></div>


			</div>

			<div class="col-xl-2 col-md-2 col-sm-12 col-xs-12">
				<table class="table table-sm">
					<tbody>
						<%
						if (helperBean.getNizVremenskigPrognoza() != null){
						for(VremenskaPrognoza vp : helperBean.getNizVremenskigPrognoza()){%>
						<tr>
							<td align="center">
								<h5>
									<% out.println(vp.getImeGrada()); %>
								</h5> <img src="<% out.print(vp.getIkonica());%>" alt="slika" />
								<h6>
									<% out.println(vp.getOpis()); %>
								</h6>
								<h5>
									Temp: <%out.print(vp.getTemperatura()); %>
								</h5>
							</td>
						</tr>
						<%	}}
					%>
					</tbody>
				</table>
			</div>
		</div>
	</div>



</body>
</html>