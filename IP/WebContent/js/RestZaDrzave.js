var battutaKey = "8052682286bd042e4162f44b115f1e53"; 
//
//870120fc71fa0debee6db71fe77cd77f --istekao
//8f401324dff48574e915373d6fb0de82 --403 gresku baca
//8052682286bd042e4162f44b115f1e53 --novi kljuc

//ako proslijedim (collback=imeFunkcije, imeFunkcije) onda ce se odraditi dio u zagradama unutar ove funkcije
//ako proslijedim (collback=imeFunkcije, nesto), onda ce se pozvati funkcija kojoj su proslijedjeni podaci
async function JsonpHttpRequest(url, callback) {
	return new Promise((resolve) => {
		var e = document.createElement('script');
		e.src = url;
		document.head.appendChild(e);
		window[callback] = (data) => {
			resolve(data);
		}
	});
}
 

 var map = new Map();


 window.onload = function () {

 	var request = new XMLHttpRequest();
 	request.open('GET', 'https://restcountries.eu/rest/v2/region/europe', false);
 	request.onload = function () {
 		var data = JSON.parse(request.responseText);
 		//document.getElementById("country").innerHTML = "";
 		for (i = 0; i < data.length; i++) {
 			{
 				document.getElementById("country").innerHTML += "<option value = \"" + data[i].alpha2Code + "\">" +
 					data[i].name + "</option>";
 				map.set(data[i].alpha2Code,data[i].flag);
 			}
 		};
 	};
 	request.send();
 		
 	
 	postaviPolja()
 }


 async function postaviPolja() {
	if (document.getElementById("country2").value == null || document.getElementById("country2").value == ""){
		
	}
	else {
		selectElement("country", document.getElementById("country2").value);
	 	await ucitajRegije();
	 	selectElement("region", document.getElementById("region2").value);
	 	await ucitajGradove();
	 	selectElement("city", document.getElementById("city2").value);
	}
 	
 }

 function selectElement(id, valueToSelect) {
 	let element = document.getElementById(id);
 	element.value = valueToSelect;
 }



 async function ucitajRegije(){
 	var alfa = country[country.selectedIndex].value;	
 	if (alfa == "Izaberi drzavu"){
 		document.getElementById("region").innerHTML = "<option>Izaberi regiju</option>";
 		region.disabled = true;
 		document.getElementById("city").innerHTML = "<option>Izaberi grad</option>";
 		city.disabled = true;
 	}
 	else{
 		document.getElementById("flag").value = map.get(alfa);
 		region.disabled = false;	 		
 		const data = await JsonpHttpRequest('http://battuta.medunes.net/api/region/' + alfa + '/all/?key=' + battutaKey + '&callback=popuniRegijeCb', "popuniRegijeCb");
		popuniRegije(data);
 	}
 }


 function popuniRegije(data){
 	if(data.length == 0 ) {
 		document.getElementById("region").innerHTML ="<option>None</option>";
 		document.getElementById("city").innerHTML ="<option>None</option>";
 		region.disabled = true;
 		city.disabled = true;
 	}
 	else{
 		document.getElementById("region").innerHTML ="<option>Izaberi regiju</option>";
 		for (i =0; i< data.length; i++){
 			document.getElementById("region").innerHTML += "<option value = \"" +data[i].region + "\">" + 
 			data[i].region + "</option>"
 		}
 	document.getElementById("city").innerHTML ="<option>Izaberi grad</option>";
 	city.disabled = true;
 	}
 	
 }

 async function ucitajGradove() {
 	var alfa = country[country.selectedIndex].value;
 	var reg = region[region.selectedIndex].value;
 	if (reg == "Izaberi regiju"){
 		city.disabled = true;
 	}
 	else{
 		city.disabled = false;
 		var res = encodeURI(reg);
 		const data = await JsonpHttpRequest('http://battuta.medunes.net/api/city/' + alfa + '/search/?region=' + res + '&key=' + battutaKey + '&callback=popuniGradoveCb', "popuniGradoveCb");
		popuniGradove(data);
 	}
 	
 }

 function popuniGradove(data){
 	if(data.length == 0 ) {
 		document.getElementById("city").innerHTML ="<option>None</option>";
 		city.disabled = true;
 	}
 	else{
 		document.getElementById("city").innerHTML ="";
 		for (i =0; i< data.length; i++){
 			document.getElementById("city").innerHTML += "<option value = \"" +data[i].city + "\">" + 
 			data[i].city + "</option>"
 		}
 	}
 	
 }

 function uploadSlike(){
 	
	 var slika = document.getElementById("file").files[0];	
	 var ispis = document.getElementById("profilnaSlika");
	ispis.style = "display:block;";
	ispis.setAttribute('width','100%');
	ispis.setAttribute('height','100%');

	 	var reader = new FileReader();
	 	reader.onload = (function (img){
	 		return function (e) {
	 			img.src = e.target.result;
	 		};
	 	})(ispis);
	 	reader.readAsDataURL(slika);
 }



