<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="etf.ip.projektni.beans.KategorijeOpasnostiBean"%>
<%@ page import="etf.ip.projektni.dto.KategorijeOpasnosti"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Unos nove objave</title>



<link rel="stylesheet" type="text/css"
	href="bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="css/nekeklase.css">
<script src="bootstrap/js/jquery.js"></script>
<script src="bootstrap/js/popper.min.js"></script>
<script src="bootstrap/js/bootstrap.js"></script>
<script src="js/ValidacijaPolja.js"></script>
<script src="js/RestZaDrzave.js"></script>
<script type="text/javascript" src="js/opasnost.js"></script>
<script src="https://polyfill.io/v3/polyfill.min.js?features=default"></script>
<script
	src="https://maps.googleapis.com/maps/api/js?callback=initMap&libraries=&v=weekly"
	defer></script>

</head>
<body onload="inicijalizacija()">

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
				<form method="POST" action="?action=objaviOpasnost"
					onsubmit=" return dodajKategorijeUTxtInput()">

					<div class="d-flex justify-content-center align-items-center">
						<h3>Unos opasnosti</h3>					
					</div>
					<hr style="height: 20px;">

					<div class="form-group">
						<textarea class="form-control" rows="3" id="opis" name="opis"
							placeholder="Opis opasnosti" required="required" maxlength="1000"></textarea>
					</div>

					<div class="row">
						<div id="kategorije_izbor_lista"
							class="col-xl-6 col-md-6 col-sm-6 col-xs-6">
							<label for="kategorije_izbor">Izaberi kategoriju</label>
							<ul id="kategorije_izbor" name="kategorije_izbor"
								class="list-group">
								<%
									for(KategorijeOpasnosti ko : new KategorijeOpasnostiBean().getNizKategorijeOpasnosti())
									{%>
								<li id="izbor_<%out.print(ko.getId()); %>"
									class="list-group-item">
									<%out.print(ko.getKategorija()); %>
								</li>
								<%}
								%>
							</ul>
						</div>

						<div class="col-xl-6 col-md-6 col-sm-6 col-xs-6">
							<label for="kategorije_odabrane">Odabrane kategorije</label>
							<ul id="kategorije_odabrane" name="kategorije_odabrane"
								class="list-group">

							</ul>
						</div>

					</div>

					<div id="latilon" name="latilon">
						<input type="hidden" id="lat" name="lat"> 
						<input type="hidden" id="lon" name="lon"> 
						<input type="hidden" id="sveKategorije" name="sveKategorije">
						<div class="form-check">
							<input type="checkbox" class="form-check-input" id="hitno"
								name="hitno"> <label class="form-check-label"
								for="obavjestenjaApp">Hitna obavijest</label>
						</div>
					</div>

					<div id="map" name="map" style="margin-top: 10px;margin-bottom: 10px;"></div>
					
					<div class="d-flex justify-content-center align-items-center text-danger">
						<p id="poruka"></p>
					</div>

					<div class="d-flex justify-content-center">
						<button type="submit" class="btn btn-primary" style="width: 100%; margin-top: 25px; margin-bottom: 50px;">Objavi</button>
					</div>


					
				</form>
			</div>

		</div>
	</div>





</body>
</html>