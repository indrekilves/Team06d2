/** 
 * Note: FormId comes from calling *.js 
 */




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




function changeType(id){
	storeAndSubmit(id, "changeType");
}



function toggleTypeRelatedWidgets(typeId){
	var typeId = document.getElementById('typeId').value;

	var isDisabled = (typeId) ? false : true;

    document.getElementById('bossUnitId').disabled=isDisabled;
    document.getElementById('addSubOridinateUnit').disabled=isDisabled;
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