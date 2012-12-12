function saveForm() {
	var exitMode = document.getElementById("exitMode");
	exitMode.value="save";
	document.getElementById("stateAdminUnitTypeForm").submit();
}


function cancelForm() {
	var exitMode = document.getElementById("exitMode");
	exitMode.value="cancel";
	document.getElementById("stateAdminUnitTypeForm").submit();
}


function removeSubOrdinate(subId) {
	var exitMode = document.getElementById("exitMode");
	exitMode.value="removeSubOrdinate";

	var subId = document.getElementById("subId").value;
	subId.value = subId;
	
	document.getElementById("stateAdminUnitTypeForm").submit();
}


function addSubOrdinate() {
	var exitMode = document.getElementById("exitMode");
	exitMode.value="addSubOrdinate";
	document.getElementById("stateAdminUnitTypeForm").submit();
}
