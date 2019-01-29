function checkName(obj) {
	var div=document.getElementsByClassName("message");
	var reg=new RegExp("^[A-Za-z]{1}([A-Za-z]|[-_]){0,6}$","g");
	var flag=reg.test(obj.value);
	if(flag==false){
	    div[0].style.display="block";
	}
	else{
	    div[0].style.display="none";
	}
}

function checkPassword(obj) {
	var div=document.getElementsByClassName("message");
	var reg=new RegExp("^([0-9]|[A-Za-z]|[_]){6,10}$","g");
	var flag=reg.test(obj.value);
	if(flag==false){
	    div[1].style.display="block";
	}
	else{
	    div[1].style.display="none";
	}	
}

function checkPasswordAgain(obj) {
	var password=document.getElementById("password");
	var div=document.getElementsByClassName("message");
	var flag=obj.value==password.value;
	if(flag==false){
	    div[2].style.display="block";
	}
	else{
	    div[2].style.display="none";
	}
}
