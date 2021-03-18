var brojUcitanihVijesti = 1;
var pocetakNotifikacija = 1;
var idDodanihVijesti = [];
var korisnickoIme;

function sakrijDivove() {
	document.getElementById("objavaLinka").style.display = "none";
	document.getElementById("objavaSlike").style.display = "none";
	document.getElementById("objavaYTVidea").style.display = "none";
	document.getElementById("objavaVidea").style.display = "none";
}

function showLink() {
	sakrijDivove();
	document.getElementById("objavaLinka").style.display = "block";
}

function showSlika() {
	sakrijDivove();
	document.getElementById("objavaSlike").style.display = "block";
}

function showYT() {
	sakrijDivove();
	document.getElementById("objavaYTVidea").style.display = "block";
}

function showVideo() {
	sakrijDivove();
	document.getElementById("objavaVidea").style.display = "block";
}

function getNotifikacije(){	
	var request = new XMLHttpRequest();
	request.onreadystatechange = function() {
		if ((request.readyState == 4) && (request.status == 200)) {
			var niz = JSON.parse(request.responseText);
			for (var iii = 0; iii < niz.length; iii++) {
				pocetakNotifikacija += niz.length
				var result = (niz[iii]);

				dodajOpasnostDiv('notifikacije', result.id, result.naslov, formatDatum(result.datumObjave), result.opis,
					result.lon, result.lat, result.hitna, result.kategorijeOpasnostis);	
			}	
			//console.log("poziv metode za notifikacije");		
		}
		
	};
	request.open("GET", "api/Vijest/getNotifikacije/"+ pocetakNotifikacija + "/" + korisnickoIme, true);
	request.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
	request.send();
}

function getVijesti(username, kraj) {
	korisnickoIme = username;
	var request = new XMLHttpRequest();
	request.onreadystatechange = function() {
		if ((request.readyState == 4) && (request.status == 200)) {
			var niz = JSON.parse(request.responseText);
			brojUcitanihVijesti += niz.length;
			for (var i = 0; i < niz.length; i++) {
				var result = (niz[i]);

				if (result.tip == "R") {
					dodajPostDiv(true, "R" + result.naslov, result.naslov, formatDatum(result.datumObjave), null,
						null, result.tip, result.link, result.opis);
				}
				else if (result.tip == "O") {
					dodajOpasnostDiv('vijesti', result.id, result.naslov, formatDatum(result.datumObjave), result.opis,
						result.lon, result.lat, result.hitna, result.kategorijeOpasnostis);
				}
				else if (!idDodanihVijesti.includes(result.id) && result.tip != "O") {

					if (result.tip == "T") {
						dodajPostDiv(true, result.id, result.naslov, formatDatum(result.datumObjave), result.usernameAutora,
							result.avatarKorisnika, result.tip, result.slika, result.tekst);
					}
					else if (result.tip == "V") {
						dodajPostDiv(true, result.id, result.naslov, formatDatum(result.datumObjave), result.usernameAutora,
							result.avatarKorisnika, result.tip, result.slika, null);
					}
					else {
						dodajPostDiv(true, result.id, result.naslov, formatDatum(result.datumObjave), result.usernameAutora,
							result.avatarKorisnika, result.tip, result.link, null);
					}
					dodajKomentar(result.id, username);
				}
			}
			getKomentari();
			
			
		console.log("poziv metode");
		}
		
	};
	request.open("GET", "api/Vijest/getVijesti/" + brojUcitanihVijesti + "/" + kraj, true);
	request.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
	request.send();

}

setInterval( function() { getVijesti(korisnickoIme,1); }, 30 * 1000 );
setInterval(function(){getNotifikacije();},5*1000);



var myVar = setInterval (function() {imeFunkcije(parametar1); }, 20  * 1000 );

function myStopFunction() {
  clearInterval(myVar);
}

function getKomentari() {
	var request = new XMLHttpRequest();
	request.onreadystatechange = function() {
		if ((request.readyState == 4) && (request.status == 200)) {
			vijesti = JSON.parse(request.responseText);
			for (var i = 0; i < vijesti.length; i++) {
				var vijest = vijesti[i];
				var div = document.getElementById("card_body_komentari_" + vijest.id).innerHTML = "";
				nizKomentara = (vijest.komentari);
				for (j = 0; j < nizKomentara.length; j++) {
					var komentar = nizKomentara[j];
					dodajKomentarDiv(vijest.id, komentar);
				}
			}
		}
	};
	request.open("GET", "api/Vijest/getKomentari", true);
	request.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
	request.send();
}

function dodajOpasnostDiv(imeDivElementa, id, naslov, datumObjave, opis, lon, lat, hitno, nizKategorija) {
	var parent = document.getElementById(imeDivElementa);

	//card div
	card = document.createElement("div");
	card.setAttribute("id", id);
	card.classList.add("card");
	card.classList.add("overflow-auto");
	card.classList.add("pozadina_posta");
	card.setAttribute("style", "width: 100%; margin-top: 10px;");


	//card header div
	header = document.createElement("div");
	header.classList.add("card-header");
	header.classList.add("d-flex");
	header.classList.add("justify-content-between");
	header.classList.add("align-items-center");
	card.appendChild(header);

	//naslov
	imeIPrezimeH5 = document.createElement("h5");
	imeIPrezimeH5.innerHTML = naslov;
	header.appendChild(imeIPrezimeH5);


	//datum
	datumP = document.createElement("p");
	small = document.createElement("small")
	datumP.appendChild(small);
	small.innerHTML = datumObjave;
	header.appendChild(datumP);

	//card body
	body = document.createElement("div");
	body.id = "card_body_" + id;
	body.classList.add("card-body");
	card.appendChild(body);

	//kategorije
	for (var i = 0; i < nizKategorija.length; i++) {
		kat = document.createElement("p");
		kat.innerHTML = nizKategorija[i];
		kat.classList.add("kategorija");
		body.appendChild(kat);
	}

	//opis
	if (opis != null) {
		opisDiv = document.createElement("p");
		opisDiv.innerHTML = opis.replace(/\n/g, "</p>\n<p>");
		body.appendChild(opisDiv);

	}

	//mapa
	if (lat != 0 && lon != 0) {
		var mapa = document.createElement("div");
		mapa.classList.add("mapa");
		body.appendChild(mapa);
		var map = new google.maps.Map(mapa, {
			zoom: 15,
			center: { lat: lat, lng: lon }
		});
		map.panTo({ lat: lat, lng: lon });
		var marker = new google.maps.Marker({
			position: { lat: lat, lng: lon },
			map: map
		});
	}
	
	
	parent.appendChild(card);
	parent.insertBefore(card, parent.firstChild);
}


function dodajPostDiv(naVrh, id, naslov, datumObjave, imeIPrezime, avatar, tip, link, tekst) {


	var parent = document.getElementById("vijesti");

	//card div
	card = document.createElement("div");
	card.setAttribute("id", id);
	card.classList.add("card");
	card.classList.add("overflow-auto");
	card.classList.add("pozadina_posta");
	card.setAttribute("style", "width: 100%; margin-top: 10px;");



	//card header div
	header = document.createElement("div");
	header.classList.add("card-header");
	header.classList.add("d-flex");
	header.classList.add("justify-content-between");
	header.classList.add("align-items-center");
	card.appendChild(header);

	if (tip != "R") {
		//grupa za ime i sliku div
		grupa = document.createElement("div");
		grupa.classList.add("d-flex");
		grupa.classList.add("justify-content-between");
		header.appendChild(grupa);

		//avatar
		avatarImg = document.createElement("img");
		avatarImg.classList.add("avatar-small");
		avatarImg.src = avatar;

		//ime i prezime
		imeIPrezimeH5 = document.createElement("h5");
		imeIPrezimeH5.innerHTML = imeIPrezime;
		grupa.appendChild(avatarImg);
		grupa.appendChild(imeIPrezimeH5);
	}

	//datum
	datumP = document.createElement("p");
	small = document.createElement("small")
	datumP.appendChild(small);
	small.innerHTML = datumObjave;
	header.appendChild(datumP);

	//card body
	body = document.createElement("div");
	body.id = "card_body_" + id;
	body.classList.add("card-body");
	card.appendChild(body);

	//naslov
	naslovH3 = document.createElement("h3");
	naslovH3.innerHTML = naslov;
	body.appendChild(naslovH3);

	//u zavisnosti od tipa
	//ako je tip L = link

	if (tip == "Y") {
		iframe = document.createElement("iframe");
		iframe.setAttribute('width', '100%');
		iframe.setAttribute('height', '400px');
		iframe.src = "https://www.youtube.com/embed/" + link;
		iframe.setAttribute('frameborder', '0');
		iframe.setAttribute('allow', 'accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture');
		iframe.setAttribute('allowfullscreen', '1');
		body.appendChild(iframe);
	}
	else if (tip == "T" || tip == "R") {
		tekstTag = document.createElement("p");
		tekstTag.innerHTML = tekst.replace(/\n/g, "</p>\n<p>");
		body.appendChild(tekstTag);


		if (tip != "R" && link != "dW5kZWZpbmVk" && link != null) {

			slika = document.createElement("img")
			slika.setAttribute('width', '100%');
			slika.src = "data:image/jpg;base64," + link;
			body.appendChild(slika);
		}

	}
	else if (tip == "V") {
		video = document.createElement("video");
		video.src = "data:video/mp4;base64," + link
		video.setAttribute('width', '100%');
		video.autoplay = false;
		video.controls = true;
		body.appendChild(video);
	}

	if (tip == "L" || tip == "R") {
		//link
		linkA = document.createElement("a");
		linkA.href = link;
		if (tip == "R")
			linkA.innerHTML = "read more";
		else
			linkA.innerHTML = link;
		body.appendChild(linkA);
	}

	//card body za komentar
	body = document.createElement("div");
	body.id = "card_body_komentari_" + id;
	body.classList.add("card-body");
	card.appendChild(body);


	if (naVrh) {
		parent.appendChild(card);
		parent.insertBefore(card, parent.firstChild);
	}
	else {
		parent.appendChild(card);
	}


	sakrijDivove()

}

function addLink(username) {
	var object = {
		naslov: document.getElementById("link_naslov").value,
		link: document.getElementById("link_link").value,
		usernameAutora: username
	}
	
	if (object.naslov == "" || object.naslov == null ){
		document.getElementById("link_poruka").innerHTML = "Naslov mora biti upisan";
		return;
	}		
	else if (object.link == "" || object.link == null ){
		document.getElementById("link_poruka").innerHTML = "Link mora biti upisan";
		return;
	}
	
	document.getElementById("link_naslov").value = "";
	document.getElementById("link_link").value = "";
	document.getElementById("link_poruka").innerHTML = "";
		
	
	console.log(object);
	var request = new XMLHttpRequest();
	request.onreadystatechange = function() {
		if ((request.readyState == 4) && (request.status == 200)) {
			var result = JSON.parse(request.responseText);
			dodajPostDiv(true, result.id, result.naslov, formatDatum(result.datumObjave), result.usernameAutora,
				result.avatarKorisnika, result.tip, result.link, null)
			dodajKomentar(result.id, username);
			idDodanihVijesti.push(result.id);

		}
	};
	request.open("POST", "api/Vijest/AddLink", true);
	request.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
	request.send(JSON.stringify(object));
}

function addYTVideo(username) {

	var video_id = document.getElementById("yt_link").value.split('v=')[1];
	
	if (document.getElementById("yt_naslov").value== "" || document.getElementById("yt_naslov").value == null ){
		document.getElementById("yt_poruka").innerHTML = "Naslov mora biti upisan";
		return;
	}		
	else if (document.getElementById("yt_link").value == "" || document.getElementById("yt_link").value == null ){
		document.getElementById("yt_poruka").innerHTML = "Link mora biti upisan";
		return;
	}
	
	
	
	
	var ampersandPosition = video_id.indexOf('&');
	if (ampersandPosition != -1) {
		video_id = video_id.substring(0, ampersandPosition);
	}

	var object = {
		naslov: document.getElementById("yt_naslov").value,
		link: video_id,
		usernameAutora: username
	}
	
	document.getElementById("yt_naslov").value = "";
	document.getElementById("yt_link").value = "";
	document.getElementById("yt_poruka").innerHTML = "";

	var request = new XMLHttpRequest();
	request.onreadystatechange = function() {
		if ((request.readyState == 4) && (request.status == 200)) {
			var result = JSON.parse(request.responseText);

			dodajPostDiv(true, result.id, result.naslov, formatDatum(result.datumObjave), result.usernameAutora,
				result.avatarKorisnika, result.tip, result.link, null)
			dodajKomentar(result.id, username);
			idDodanihVijesti.push(result.id);
		}
	};

	request.open("POST", "api/Vijest/AddYTVideo", true);
	request.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
	request.send(JSON.stringify(object));

}


function addTekstSaSlikom(username) {

	var formData = new FormData();
	formData.append("slikUpis", document.getElementById("slika_file").files[0]);
	formData.append("naslov", document.getElementById("slika_naslov").value);
	formData.append("tekst", document.getElementById("slika_tekst").value);
	formData.append("usernameAutora", username);
	
	
	if (document.getElementById("slika_file").files[0] == "" || document.getElementById("slika_file").files[0] == null ){
		document.getElementById("slika_poruka").innerHTML = "Slika mora biti odabrana";
		return;
	}		
	else if (document.getElementById("slika_naslov").value == "" || document.getElementById("slika_naslov").value == null ){
		document.getElementById("slika_poruka").innerHTML = "Naslov mora biti upisan";
		return;
	}
	else if (document.getElementById("slika_tekst").value == "" || document.getElementById("slika_tekst").value == null ){
		document.getElementById("slika_poruka").innerHTML = "Tekst mora biti upisan";
		return;	
	}
	
	//document.getElementById("slika_file").files[0] = null;
	document.getElementById("slika_naslov").value = "";
	document.getElementById("slika_tekst").value = "";
	document.getElementById("slika_poruka").innerHTML = "";


	var request = new XMLHttpRequest();
	request.onreadystatechange = function() {
		if ((request.readyState == 4) && (request.status == 200)) {
			var result = JSON.parse(request.responseText);

			dodajPostDiv(true, result.id, result.naslov, formatDatum(result.datumObjave), result.usernameAutora,
				result.avatarKorisnika, result.tip, result.slika, result.tekst)
			dodajKomentar(result.id, username);
			idDodanihVijesti.push(result.id);
		}
	};
	request.open("POST", "multipart/Multipart/AddTekstSaSlikom", true);
	//request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(formData);
}

function addVideo(username) {

	var formData = new FormData();
	formData.append("slikUpis", document.getElementById("video_video").files[0]);
	formData.append("naslov", document.getElementById("video_naslov").value);
	formData.append("usernameAutora", username);
	
	if (document.getElementById("video_video").files[0] == "" || document.getElementById("video_video").files[0] == null ){
		document.getElementById("video_poruka").innerHTML = "Video mora biti odabran";
		return;
	}		
	else if (document.getElementById("video_naslov").value == "" || document.getElementById("video_naslov").value == null ){
		document.getElementById("video_poruka").innerHTML = "Naslov mora biti upisan";
		return;
	}
	
	//document.getElementById("video_video").files[0] = null;
	document.getElementById("video_naslov").value = "";
	document.getElementById("video_poruka").innerHTML = "";


	var request = new XMLHttpRequest();
	request.onreadystatechange = function() {
		if ((request.readyState == 4) && (request.status == 200)) {
			var result = JSON.parse(request.responseText);

			dodajPostDiv(true, result.id, result.naslov, formatDatum(result.datumObjave), result.usernameAutora,
				result.avatarKorisnika, result.tip, result.slika, null)
			dodajKomentar(result.id, username);
			idDodanihVijesti.push(result.id);

		}
	};
	request.open("POST", "multipart/Multipart/AddVideo", true);
	//request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(formData);
}

function dodajKomentar(id, username) {
	theKid = document.createElement("div");
	theKid.setAttribute("id", "komentar_" + id);
	theKid.classList.add("card");
	theKid.classList.add("overflow-auto");
	theKid.classList.add("pozadina_komentara");

	//tekst
	inputText = document.createElement("input");
	inputText.classList.add("form-control");
	inputText.type = "text";
	inputText.setAttribute("placeholder", "Unestie komentar");
	inputText.setAttribute("maxLength","1000");
	inputText.id = "tekst_komentar_" + id;

	//slika
	inputFile = document.createElement("input");
	inputFile.type = "file";
	inputFile.classList.add("form-control");
	inputFile.setAttribute("accept", "image/x-png,image/gif,image/jpeg");
	inputFile.id = "slika_komentar_" + id;

	//dugme
	dugme = document.createElement("a");
	dugme.classList.add("btn");
	dugme.classList.add("btn-primary");
	dugme.classList.add("text-center");
	dugme.setAttribute("role", "button");
	dugme.setAttribute("onclick", "dodajKomentarNaObjavu(" + id + ",'" + username + "')");
	dugme.innerHTML = "Dodaj komentar";


	theKid.appendChild(inputText);
	theKid.appendChild(inputFile);
	theKid.appendChild(dugme);


	theParent = document.getElementById("card_body_" + id);
	theParent.appendChild(theKid);
}

function dodajKomentarDiv(id, result) {
	theParent = document.getElementById("card_body_komentari_" + id);

	//card div
	card = document.createElement("div");
	card.classList.add("card");
	card.classList.add("overflow-auto");
	card.classList.add("pozadina_komentara");
	card.setAttribute("style", "width: 100%; margin-top: 5px;");


	//card header div
	header = document.createElement("div");
	header.classList.add("card-header");
	header.classList.add("d-flex");
	header.classList.add("justify-content-between");
	header.classList.add("align-items-center");
	card.appendChild(header);

	//grupa za ime i sliku div
	grupa = document.createElement("div");
	grupa.classList.add("d-flex");
	grupa.classList.add("justify-content-between");
	header.appendChild(grupa);

	//avatar
	avatarImg = document.createElement("img");
	avatarImg.classList.add("avatar-small");
	avatarImg.src = result.avatarKorisnika;


	//ime i prezime
	imeIPrezimeH5 = document.createElement("h5");
	imeIPrezimeH5.innerHTML = result.usernameAutora;
	grupa.appendChild(avatarImg);
	grupa.appendChild(imeIPrezimeH5);

	//datum
	datumP = document.createElement("p");
	small = document.createElement("small")
	datumP.appendChild(small);
	small.innerHTML = formatDatum(result.datumObjave);
	header.appendChild(datumP);

	//card body
	body = document.createElement("div");
	body.classList.add("card-body");
	card.appendChild(body);

	//tekst komentara
	tekstKomentara = document.createElement("p");
	smallKomentara = document.createElement("small")
	tekstKomentara.appendChild(smallKomentara);
	smallKomentara.innerHTML = result.tekst;
	body.appendChild(tekstKomentara);

	if (result.slika != "dW5kZWZpbmVk" && result.slika != null) {

		//slika komentara
		slika = document.createElement("img")
		slika.setAttribute('width', '100%');
		slika.src = "data:image/jpg;base64," + result.slika;
		body.appendChild(slika);
	}
	theParent.appendChild(card);
	theParent.insertBefore(card, theParent.firstChild);

}

function dodajKomentarNaObjavu(id, username) {
	var formData = new FormData();
	formData.append("slika", document.getElementById("slika_komentar_" + id).files[0]);
	formData.append("tekst", document.getElementById("tekst_komentar_" + id).value);
	formData.append("usernameAutora", username);
	formData.append("idVijesti", id)
	
	if (document.getElementById("tekst_komentar_" + id).value == "" || document.getElementById("tekst_komentar_" + id).value == null ){
		alert("Tekst koentara mora biti upisan");
		return;
	}
	
	document.getElementById("tekst_komentar_" + id).value = "";
	

	var request = new XMLHttpRequest();
	request.onreadystatechange = function() {
		if ((request.readyState == 4) && (request.status == 200)) {
			var result = JSON.parse(request.responseText);
			dodajKomentarDiv(id, result);
		}

	};
	request.open("POST", "multipart/Multipart/AddKomentar", true);
	request.send(formData);

}

function formatDatum(datum) {
	datum = datum.substring(0, datum.length - 5);
	var m = new Date(datum);
	var dateString =
		m.getUTCFullYear() + "/" +
		("0" + (m.getUTCMonth() + 1)).slice(-2) + "/" +
		("0" + m.getUTCDate()).slice(-2) + " " +
		("0" + m.getUTCHours()).slice(-2) + ":" +
		("0" + m.getUTCMinutes()).slice(-2) + ":" +
		("0" + m.getUTCSeconds()).slice(-2);
	return dateString
}


function uploadSlike(idSlike, idFajla) {

	var slika = document.getElementById(idFajla).files[0];
	var ispis = document.getElementById(idSlike);
	ispis.style = "display:block;";
	ispis.setAttribute('width', '100%');
	//ispis.setAttribute('height','100%');


	var reader = new FileReader();
	reader.onload = (function(img) {
		return function(e) {
			img.src = e.target.result;
		};
	})(ispis);
	reader.readAsDataURL(slika);
}