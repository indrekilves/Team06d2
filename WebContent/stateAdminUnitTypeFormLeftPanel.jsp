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
	<table>
	   	<!-- Code -->
	   	<tr>
	    	<td>Code</td> 		
		   	<td><input name="code" 	value="${unitType.code}"></td>	
	    </tr>
	
			    	<!-- Name -->
	    <tr>
		    <td>Name</td>		
		    <td><input name="name" 	value="${unitType.name}"></td>	
	    </tr>
	    
			    	<!-- Comment -->
		<tr>
			<td valign="top">Comment</td>
			<td><textarea 	name = "comment" 
							cols = "35"
							rows = "10">${unitType.comment}
				</textarea>
			</td>
	    </tr>
	    
	    <!-- Subordinate -->
	    
	   	<tr>
			<td>Subordinate of</td>
			<td>
	
				 
			<select name="bossAdminUnitTypeId">
				<c:forEach var="entry" items="${unitTypes}">
			    	<c:set var="selected" value=""/>
			    	
			    	<c:if test="${entry.state_admin_unit_type_id == unitType.bossAdminUnitType.state_admin_unit_type_id}">
			     		<c:set var="selected" value="selected=\"selected\""/>
			    	</c:if>
	
					<option value="${entry.state_admin_unit_type_id}" ${selected}>${entry.name}</option>
				</c:forEach>
			</select>
		
			</td>
	
		</tr>
		
		<!-- From date  -->
		<!-- TODO: convert to jQuery based datePicker / calender widget -->
		<tr>
			<td>From</td>
			<td>
				<input 	name  = "fromDate" 
						type  = "text" 
						value ="<fmt:formatDate 	value   = "${unitType.fromDate}"  
													type    = "date" 
													pattern = "dd.MM.yyyy"/>"
				 />
			</td>
			
		</tr>
		
		<!-- To date  -->
		<!-- TODO: convert to jQuery based datePicker / calender widget -->
		
		<tr>
			<td>To</td>
			<td>
				<input 	name  = "toDate" 
						type  = "text" 
						value ="<fmt:formatDate 	value   = "${unitType.toDate}"  
													type    = "date" 
													pattern = "dd.MM.yyyy"/>"
				 />
			</td>
			
		</tr>
		
	</table>
</body>
</html>