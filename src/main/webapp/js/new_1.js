var number=1;

function addComments() {
	var date=new Date;
	var creatDiv=document.createElement("div");
	var value="Comments  "+number+"  :   ";
	value+=document.getElementById("txt").value;
	value+="  /**/发布于"+date.getFullYear()+"年"+(date.getMonth()+1)+"月"+date.getDate()+"日";
	number++;
	var txt=document.createTextNode(value);
	creatDiv.appendChild(txt);
	creatDiv.setAttribute("class","creatDiv")
	var div=document.getElementsByClassName("frameThree");
	var div1=div[0].childNodes;
	div[0].insertBefore(creatDiv,document.getElementById("end"));
}
