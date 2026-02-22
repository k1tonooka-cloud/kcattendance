/**
 * 
 */
function onCheck(i) {
	//document.getElementById("action").value = "checkbox";
	document.getElementById("no").value = i;
	document.getElementById("EmpMasterForm").submit();
}

function updatealert() {
	var msg = document.getElementById("msg").value;
	if( msg != "") {
		alert(msg);
		location.href="/emp_master";
	}
}
