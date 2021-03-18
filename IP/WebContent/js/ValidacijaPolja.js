function checkUsername(){
	var username = document.getElementById("username");
	
	var letters = /^[A-Za-z]+[A-Za-z1-9]+$/;
	
	if (username.value != ""){
		if(username.value.length < 2 || username.value.length >20){
	        username.setCustomValidity("Korisnicko ime mora da ima od 2 do 20 slova");
	      }
	      else if (!username.value.match(letters)){
	    	username.setCustomValidity("Korisnicko ime mora da pocinje slovom");	        
	      }
	      else {
	        username.setCustomValidity("");
	      }
		}	
}


function checkFirstName(){
	var fname = document.getElementById("firstname");
	
	var letters = /^[A-Za-z]+$/;
	
	if(fname.value != ""){
		if(fname.value.length < 2){
			fname.setCustomValidity("Ime mora da ime od 2 do 50 slova");
		}
		else if (!fname.value.match(letters)){
			fname.setCustomValidity("Ime moze da sadrzai samo slova");
		}
		else {
			fname.setCustomValidity("");
	      }
	}
}

function checkLastName(){
	var fname = document.getElementById("lastname");
	
	var letters = /^[A-Za-z]+$/;
	
	if(fname.value != ""){
		if(fname.value.length < 2){
			fname.setCustomValidity("Prezime mora da ima od 2 do 50 slova");
		}
		else if (!fname.value.match(letters)){
			fname.setCustomValidity("Prezime moze da sadrzi samo slova");
		}
		else {
			fname.setCustomValidity("");
	      }
	}
}

function checkPassword(){
	var pass = document.getElementById("password");	
	
	
	if(pass == null || pass.value != ""){
		if(pass.value.length < 6){
			pass.setCustomValidity("Lozikna mora da ima od 6 do 20 karaktera");
		}
		else {
			pass.setCustomValidity("");
	      }
	}
} 

function checkSeccondPassword(){
	var pass1 = document.getElementById("password");
	var pass2 = document.getElementById("password2");
	
	if(pass1.value != null && pass2.value !=  null && 
	pass1.value != "" && pass2.value != "" && pass1.value == pass2.value){
		pass2.setCustomValidity("");
		}
	else if (pass2.value == ""){
		pass2.setCustomValidity("");
	      }
	else {
		pass2.setCustomValidity("Lozinke se ne poklapaju");
      }
} 

function checkEmail(){
	var email = document.getElementById("email");
	
	var letters = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
	
	if(email.value != ""){
		if (!email.value.match(letters)){

			email.setCustomValidity("Email nije validan");
		}
		else {
			email.setCustomValidity("");
	      }
	}
	
}


















