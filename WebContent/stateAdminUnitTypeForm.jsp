<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>BoarderGuard - Edit Admin Unit Type</title>
	<link rel="stylesheet" href="./style.css" type="text/css">
	<script type="text/javascript">
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
	
	
		function removeSubOrdinate(id) {
			var exitMode = document.getElementById("exitMode");
			exitMode.value="removeSubOrdinate";
	
			var subId = document.getElementById("subId");
			subId.value = id;
			
			document.getElementById("stateAdminUnitTypeForm").submit();
		}
	
	
		function addSubOrdinate() {
			var exitMode = document.getElementById("exitMode");
			exitMode.value="addSubOrdinate";
			document.getElementById("stateAdminUnitTypeForm").submit();
		}
	
	</script>
	
</head>


<body>

	<div id="header">
		<jsp:include page="header.jsp"/>
	</div>
	
	<div id="navigation">
   		<jsp:include page="navigation.jsp"/>
	</div>	
	
	<div id="content">
	 	<div class="pad10">
	
			<c:if test="${not empty errors}">
				<div style="color: red">
					<c:forEach var="error" items="${errors}">
						<c:out value="${error}"></c:out>
						<br />
					</c:forEach>
				</div>
				<br />
				<br />
			</c:if>
		
			<form 	method	= "POST" 
					id		= "stateAdminUnitTypeForm" 
					action	= "?form=stateAdminUnitTypeForm&id=${unitType.state_admin_unit_type_id}">
				
				<input type="hidden" id="subId" 	name="subId"	value="">					
				<input type="hidden" id="exitMode"	name="exitMode"	value="">
					
				<table>
					<tr>
<!-- Left panel --> 	<td>
 	  						<jsp:include page="stateAdminUnitTypeFormLeftPanel.jsp"/>			    	
						</td>
					
					
<!-- Center panel -->	<td width="15px"></td>
						
<!-- Right panel -->	<td valign="top">
							<jsp:include page="stateAdminUnitTypeFormRightPanel.jsp"/>			    	
						</td>
						
					</tr>
					<tr>
						<td></td>
					    <td></td>
						<td align="right">	
							<table>
								<tr height="60px" valign="bottom">
<!-- Save button -->				<td>			
									 	<input type="button" value="Save" name="btnSave" class="largeButton"  onclick="saveForm()">
									</td>
<!-- Cancel button -->				<td>
								    	<input type="button" value="Cancel" name="btnCancel" class="largeButton" onclick="cancelForm()">
									</td>
								</tr>						
							</table>
						</td>	
					</tr>	 	
				</table>
			</form>	
						
		</div>
	</div>

	<div id="footer">
		<jsp:include page="footer.jsp"/>
	</div>

</body>
</html>