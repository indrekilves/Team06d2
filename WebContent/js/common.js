/** 
 * Note: FormId comes from calling *.js 
 */

// Include jQuery

document.write("<link href='css/smoothness/jquery-ui-1.9.2.custom.css' rel='stylesheet'>");
document.write("<script src='js/jquery-1.8.3.js'></script>");
document.write("<script src='js/jquery-ui-1.9.2.custom.js'></script>");
document.write("<script src='js/my_jquery_functions.js'></script>");




// Entry points from Unit and UnitType lists 




function showSelectedEntry(id) {
	storeAndSubmit(id, "showSelectedEntry");
}




function removeSelectedEntry(id) {
	storeAndSubmit(id, "removeSelectedEntry");
}




function addEntry() {
	storeAndSubmit("", "addEntry");
}




// Entry points from Unit and UnitType forms 




function saveForm(id){
	storeAndSubmit(id, "saveForm");
}




function cancelForm(id) {
	storeAndSubmit(id, "cancelForm");
}




function removeSubOrdinate(id, subId) {
	setSubId(subId);
	storeAndSubmit(id, "removeSubOrdinate");
}




function addSubOrdinate(id) {
	storeAndSubmit(id, "addSubOrdinate");
}






//Entry points from Unit and UnitType possible subOrdinates lists 




function selectSubOrdinate(id, subId) {
	setSubId(subId);
	storeAndSubmit(id, "selectSubOrdinate");
}




function cancelSubordinateSelect(id) {
	storeAndSubmit(id, "cancelSubordinateSelect");
}




// Entry points from type selection list




function selectType(id, typeId){
	setTypeId(typeId);
	storeAndSubmit(id, "selectType");
}




function cancelTypeSelect(id){
	storeAndSubmit(id, "cancelTypeSelect");
}




// Helpers



function storeAndSubmit(id, exitMode){
	// FormID comes from calling *.js

	setId(id);
	setExitMode(exitMode);			
	setOrigin(formId);			
	submitForm(formId);		
}




// Setters 




function setId(id){
	var idWidget   = document.getElementById("id");
	idWidget.value = id;	
}



function setSubId(subId){
	var subIdWidget   = document.getElementById("subId");
	subIdWidget.value = subId;			
}




function setTypeId(typeId){
	var typeIdWidget   = document.getElementById("typeId");
	typeIdWidget.value = typeId;			
}




function setExitMode(exitMode){
	var emWidget   = document.getElementById("exitMode");
	emWidget.value = exitMode;
}




function setOrigin(origin){
	var originWidget   = document.getElementById("origin");
	originWidget.value = origin;
}




// Submit




function submitForm(formId){
	document.getElementById(formId).submit();
}