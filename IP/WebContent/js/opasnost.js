
//"use strict";

//let map;
let lastMarker;

function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 15,
        center: { lat: 44.766493, lng: 17.186708 }
    });

    map.addListener('click', function (e) {
        placeMarkerAndPanTo(e.latLng, map);
    });
}

function placeMarkerAndPanTo(latLng, map) {
    var marker = new google.maps.Marker({
        position: latLng,
        map: map
    });
    if (lastMarker) {
        lastMarker.setMap(null);
    }
    lastMarker = marker;
    map.panTo(latLng);
    //console.log(latLng.toString())
    let matches = latLng.toString().match(/\((.*?)\)/);
    //console.log(matches);
    let lat = matches[1].split(', ')[0];
    //console.log(parseFloat(lat).toFixed(6));

    document.getElementById("lat").value = parseFloat(matches[1].split(', ')[0]).toFixed(6);
    document.getElementById("lon").value = parseFloat(matches[1].split(', ')[1]).toFixed(6);
}

function inicijalizacija(){
	
	var ul = document.getElementById("kategorije_izbor_lista");
	var li = ul.getElementsByTagName("li");
	
	
	for(var i = 0;i<li.length;i++){
	    li[i].addEventListener("click", dodajKategoriju);
	}
	initMap();
}



function dodajKategoriju(e){
	ul = document.getElementById("kategorije_odabrane");
	
	li = document.createElement("li");
	li.classList.add("list-group-item");
	li.id = e.target.attributes.id.value.split("_")[1];
	li.innerHTML = e.target.innerHTML;
	li.addEventListener("click", izbaciKategoriju);
	ul.appendChild(li);
	
	e.target.style.display = "none";     
}

function izbaciKategoriju(e){
	
	li = document.getElementById("izbor_" + e.target.attributes.id.value);
	li.style.display = "block";    
	elementZaBrisanje = e.target;	
	var ul = elementZaBrisanje.parentElement;
   	ul.removeChild( elementZaBrisanje);
}	


function dodajKategorijeUTxtInput(){
	polje = document.getElementById("sveKategorije");
	polje.value = "";
	var lis = document.getElementById("kategorije_odabrane").getElementsByTagName("li");
	if (lis == null || lis.length == 0) {
		document.getElementById("poruka").innerHTML = "Morate da odaberete jednu ili vise kategoija";
		return false;	
	}
	
	for(i=0;i<lis.length ; i++){
		polje.value = polje.value +  lis[i].id + "#";
	}
	return true;
}



