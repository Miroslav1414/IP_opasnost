function checkUsername(){
	var username = document.getElementById("username");
	var error = document.getElementById("usernameError");
	
	
	if (username.value != ""){
		if(username.value.length < 2){
			error.innerHTML = "Username must be between 2 and 20 characters";
	        username.style.border = "solid red";
	        //username.setCustomValidity("Username must be between 2 and 20 characters");
	      }
	      else if (!(username.value.charAt(0)>='a' && username.value.charAt(0)<='z' ) || 
	      (username.value.charAt(0)>='A' && username.value.charAt(0)<='Z' )){
	        error.innerHTML = "The first letter must be a character";
	        username.style.border = "solid red";
	      }
	      else {
	        error.innerHTML = "";
	        username.style.border = "solid green";
	      }
		}
	else {
		error.innerHTML = "";
        username.style.border = "1px solid #ced4da";
	}
	
}


function checkFirstName(){
	var fname = document.getElementById("firstname");
	var error = document.getElementById("firstnameError");
	
	var letters = /^[A-Za-z]+$/;
	
	if(fname.value != ""){
		if(fname.value.length < 2){
			error.innerHTML = "Firstname must be between 2 and 50 characters";
			fname.style.border = "solid red";
		}
		else if (!fname.value.match(letters)){
			error.innerHTML = "Firstname must contain only letters";
			fname.style.border = "solid red";
		}
		else {
	        error.innerHTML = "";
	        fname.style.border = "solid green";
	      }
	}
	else {
        error.innerHTML = "";
        fname.style.border = "1px solid #ced4da";
      }
}

function checkLastName(){
	var fname = document.getElementById("lastname");
	var error = document.getElementById("lastnameError");
	
	var letters = /^[A-Za-z]+$/;
	
	if(fname.value != ""){
		if(fname.value.length < 2){
			error.innerHTML = "Lastname must be between 2 and 50 characters";
			fname.style.border = "solid red";
		}
		else if (!fname.value.match(letters)){
			error.innerHTML = "Lastname must contain only letters";
			fname.style.border = "solid red";
		}
		else {
	        error.innerHTML = "";
	        fname.style.border = "solid green";
	      }
	}
	else {
        error.innerHTML = "";
        fname.style.border = "1px solid #ced4da";
      }
}

function checkPassword(){
	var pass = document.getElementById("password");
	var error = document.getElementById("passwordError");
	
	if(pass.value != ""){
		if(pass.value.length < 6){
			error.innerHTML = "Password must be between 6 and 20 characters";
			pass.style.border = "solid red";
		}
		else {
	        error.innerHTML = "";
	        pass.style.border = "solid green";
	      }
	}
	else {
        error.innerHTML = "";
        fname.style.border = "1px solid #ced4da";
      }
} 

function checkSeccondPassword(){
	var pass1 = document.getElementById("password");
	var pass2 = document.getElementById("password2");
	var error = document.getElementById("password2Error");
	
	if(pass1.value != "" && pass2.value != "" && pass1.value == pass2.value){
			pass2.style.border = "solid green";
			error.innerHTML = "";
		}
	else if (pass2.value == ""){
	        error.innerHTML = "";
	        pass2.style.border = "1px solid #ced4da";
	      }
	else {
        error.innerHTML = "Passwords doesn't match";
        pass2.style.border = "solid red";
      }
} 

function checkEmail(){
	var email = document.getElementById("email");
	var error = document.getElementById("emailError");
	
	var letters = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
	
	if(email.value != ""){
		if (!email.value.match(letters)){
			error.innerHTML = "Email is not vaild";
			email.style.border = "solid red";
		}
		else {
	        error.innerHTML = "";
	        email.style.border = "solid green";
	      }
	}
	else {
        error.innerHTML = "";
        email.style.border = "1px solid #ced4da";
      }
	
}

function canRegister(){
	var error = document.getElementById("emailError");
	var error1 = document.getElementById("password2Error");
	var error2 = document.getElementById("passwordError");
	var error3 = document.getElementById("usernameError");
	var error4 = document.getElementById("firstnameError");
	var error5 = document.getElementById("lastnameError");
	var errorMessage = document.getElementById("registerError");
	
	if (error.value =="" && error1.value =="" && error2.value =="" && 
			error3.value =="" && error4.value =="" && error5.value ==""){
		errorMessage.innerHTML = "";
		true;}
	else {
		errorMessage.innerHTML = "All fields must be valid!";
		return false
	}
	
}


















