// Here's defined the formId - implementation is in common.js

document.write("<script type='text/javascript' src='js/common.js'></script>");

var formId = "unitForm";



function onLoadUnitForm(){
	if (isComingFromReport()){
		disableForm();
	    document.getElementById('cancelUnit').disabled=false;
	} else {
		toggleTypeRelatedWidgets();
	}
}


function isComingFromReport(){
	var pathName = window.location.pathname; 
	return pathName.indexOf("UnitsReportController") !== -1;
}


function toggleTypeRelatedWidgets(typeId){
	var typeId = document.getElementById('typeId').value;

	var isDisabled = (typeId) ? false : true;

    document.getElementById('bossUnitId').disabled=isDisabled;
    document.getElementById('addSubOridinateUnit').disabled=isDisabled;
}



function disableForm(){ 
	var i;
    for (i=0;i<document.forms.unitForm.elements.length;i++){ 
        document.forms.unitForm[i].disabled=true; 
    } 
}  




function changeType(id){
	storeAndSubmit(id, "changeType");
}



