// Here's defined the formId - implementation is in common.js
var formId = "unitsReport";

document.write("<script type='text/javascript' src='js/common.js'></script>");



function refreshReport(){	
	setExitMode("refreshReport");			
	setOrigin(formId);			
	submitForm(formId);		
}



