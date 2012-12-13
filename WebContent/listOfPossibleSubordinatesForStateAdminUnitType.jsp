<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="./style.css" type="text/css">
	<title>BoarderGuard - Possible Subordinates for Admin Unit Type</title>
	
	<script type="text/javascript">
		function selectSubOrdinate(id) {
			var exitMode = document.getElementById("exitMode");
			exitMode.value="selectedSubOrdinate";
			
			var subId = document.getElementById("subId");
			subId.value = id;
			
			document.getElementById("listOfPossibileSubordinatesForStateAdminUnitType").submit();
		}
		
		
		function cancelSelect() {
			var exitMode = document.getElementById("exitMode");
			exitMode.value="cancel";
			document.getElementById("listOfPossibileSubordinatesForStateAdminUnitType").submit();
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
			<b>Select subordinate for <c:out value="${unitType.name}"/></b><br><br>
			
			<form 	method	= "POST" 
					id		= "listOfPossibileSubordinatesForStateAdminUnitType" 
					action	= "?form=listOfPossibileSubordinatesForStateAdminUnitType&id=${unitType.state_admin_unit_type_id}">
			
				<input type="hidden" id="subId" 	name="subId"	value="">					
				<input type="hidden" id="exitMode"	name="exitMode"	value="">

				
				<table>
<!-- Select subOrdinate -->						
				
					<tr align="left">
						<th width="100px">Code</th>
						<th width="150px">Name</th>
						<th width="50px"></th>
					</tr>
					
						<c:forEach var="posSubOrdType" items="${possibleSubordinateUnitTypes}">
							<tr>
								<td>
									 <c:out value="${posSubOrdType.code}" />
								</td>
								<td>
									<c:out value="${posSubOrdType.name}" />
								</td>
								<td>
									<input 	type    = "button" 
								  			value   = "Select" 
								  			name    = "btnSelectSubOrdinate" 
								  			class   = "largeButton" 
								  			onclick = "selectSubOrdinate('${posSubOrdType.state_admin_unit_type_id}')">	
								</td>
							
							</tr>
						</c:forEach>
						
<!-- Cancel Select -->						
						<tr height="40px" valign="bottom">
							<td></td>
							<td></td>
							<td align="right">
				
									
								  	<input 	type    = "button" 
								  			value   = "Cancel" 
								  			name    = "btnCancelSelect" 
								  			class   = "largeButton"
								  			onclick = "cancelSelect()">
							</td>
						</tr>
				</table>
				<br><br><br>
				Note:<br>
				Shown are only these Types that can be subordinates of <c:out value="${unitType.name}"/>.
				
			</form>
		    
	    </div>
	</div>
	
	<div id="footer">
		<jsp:include page="footer.jsp"/>
	</div>

</body>
</html>