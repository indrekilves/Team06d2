<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="./style.css" type="text/css">

</head>
<body>
	<table width="300px">
    	<tr style="border: 1px solid black;background-color:#D3D3D3;">
    		<th align="left">Subordinates</th>
    		<th></th>				    						    		
    	</tr>
    	
<!-- list of subordinates -->
		<c:forEach var="subType" items="${unitType.subordinateAdminUnitTypes}">
			<tr style="border: 1px solid black;">
				<td>
					<c:out value="${subType.name}" />
				</td>
				<td align="right">
				  	<input 	type    = "button" 
				  			value   = "Remove" 
				  			name    = "btnRemoveSubType" 
				  			class   = "largeButton" 
				  			onclick = "removeSubOrdinate('${subType.state_admin_unit_type_id}')">		  			
				</td>			
			</tr>
		</c:forEach>
		
<!-- add new subordinate -->		
		<tr height="40px" valign="bottom">
			<td></td>
			<td align="right">

					
				  	<input 	type    = "button" 
				  			value   = "Add" 
				  			name    = "btnAddSubType" 
				  			class   = "largeButton"
				  			onclick = "addSubOrdinate()">
			</td>
		</tr>
   	</table>
</body>
</html>