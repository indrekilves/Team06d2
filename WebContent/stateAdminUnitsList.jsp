<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>BoarderGuard - Admin Units</title>
	<link rel="stylesheet" href="./style.css" type="text/css">

	<script type="text/javascript">
		function editUnit(id) {
			var exitMode = document.getElementById("exitMode");
			exitMode.value = "editUnitType";
			
			var idWidget = document.getElementById("id");
			idWidget.value = id;
			
			document.getElementById("listOfStateAdminUnits").submit();
		}
		

		function removeUnit(id) {
			var exitMode = document.getElementById("exitMode");
			exitMode.value = "removeUnitType";
			
			var idWidget = document.getElementById("id");
			idWidget.value = id;
			
			document.getElementById("listOfStateAdminUnits").submit();
		}
		
		function addUnit() {
			var exitMode = document.getElementById("exitMode");
			exitMode.value = "addUnitType";
			document.getElementById("listOfStateAdminUnits").submit();
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
			<b>State Admin Units</b><br><br>


			<form 	method	= "POST" 
					id		= "listOfStateAdminUnits" 
					action	= "?form=listOfStateAdminUnits">
			
				<input type="hidden" id="id" 		name="id"		value="">					
				<input type="hidden" id="exitMode"	name="exitMode"	value="">

				<table>
					<tr align="left">
						<th width="40px">ID</th>
						<th width="100px">Code</th>
						<th width="150px">Name</th>
						<th width="100px">Type</th>
						<th></th>
						<th width="20px"></th>
						<th></th>
					</tr>
					
						<c:forEach var="unit" items="${units}">
							<tr>
<!-- Id -->						<td>
									 <c:out value="${unit.state_admin_unit_id}" />
								</td>
<!-- Code -->					<td>
									 <c:out value="${unit.code}" />
								</td>
<!-- Name -->					<td>
									<c:out value="${unit.name}" />
								</td>
<!-- Type -->					<td>
									<c:out value="${unit.type.name}" />
								</td>

<!-- Edit button -->			<td>
									<input 	type    = "button" 
								  			value   = "Edit" 
								  			name    = "btnEdit" 
								  			class   = "largeButton" 
								  			onclick = "editUnit('${unit.state_admin_unit_id}')">	
								</td>
								<td></td>	
<!-- Remove button -->			<td>
									<input 	type    = "button" 
								  			value   = "Remove" 
								  			name    = "btnRemove" 
								  			class   = "largeButton" 
								  			onclick = "removeUnit('${unit.state_admin_unit_id}')">	
								</td>							
							</tr>
						</c:forEach>
					<tr height="60px" valign="bottom">
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
<!-- Add button -->		<td>
						 	<input 	type	= "button" 
						 			value   = "Add" 
						 			name    = "btnAdd" 
						 			class   = "largeButton"  
						 			onclick = "addUnit()">
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