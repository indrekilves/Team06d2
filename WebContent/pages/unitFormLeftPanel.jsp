<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta 	http-equiv="Content-Type" 	content="text/html; charset=UTF-8">
	<link  	type="text/css"				rel="stylesheet"	 href="./css/style.css">
</head>

<body>
	<table>
<!-- Code -->
	   	<tr>
	    	<td width="100px">Code</td> 		
		   	<td><input name="code" 	value="${unit.code}"></td>	
	    </tr>
	
<!-- Name -->
	    <tr class="tall">
		    <td>Name</td>		
		    <td><input name="name" 	value="${unit.name}"></td>	
	    </tr>
	    
<!-- Comment -->
		<tr class="tall">
			<td valign="top">Comment</td>
			<td>
				<textarea name="comment">${unit.comment}</textarea>
			</td>
	    </tr>

<!-- Type -->
	    <tr class="tall">
		    <td>Type</td>		
		    <td>
		    	<table  width="205px">
		    		<tr> 
		    			<td >
			    			<c:out value="${unit.type.name}"/>
			    		</td>
<!-- Type button -->	<td align="right">
						 	<input 	type	= "button" 
						 			value	= "Change" 
						 			class 	= "largeButton"  
						 			onclick	= "changeType('${unit.state_admin_unit_id}')">
						</td>
		    		</tr>
		    	</table>
		    </td>	
	    </tr>
	    
 <!-- Subordinate -->
	    
	   	<tr class="tall">
			<td>Subordinate of</td>
			<td>
	
				 
			<select name="bossUnitId" id="bossUnitId">
				<option value=""></option>
			
				<c:forEach var="entry" items="${bossUnits}">
			    	<c:set var="selected" value=""/>
			    	
			    	<c:if test="${entry.state_admin_unit_id == unit.bossUnit.state_admin_unit_id}">
			     		<c:set var="selected" value="selected=\"selected\""/>
			    	</c:if>
	
					<option value="${entry.state_admin_unit_id}" ${selected}>${entry.name}</option>
				</c:forEach>
			</select>
		
			</td>
	
		</tr>
		
		<!-- From date  -->
		<!-- TODO: convert to jQuery based datePicker / calender widget -->
		<tr class="tall">
			<td>From</td>
			<td>
				<input 	name  = "fromDate" 
						type  = "text" 
						value ="<fmt:formatDate 	value   = "${unit.fromDate}"  
													type    = "date" 
													pattern = "dd.MM.yyyy"/>"
				 />
			</td>
			
		</tr>
		
		<!-- To date  -->
		<!-- TODO: convert to jQuery based datePicker / calender widget -->
		<tr class="tall">
			<td>To</td>
			<td>
				<input 	name  = "toDate" 
						type  = "text" 
						value ="<fmt:formatDate 	value   = "${unit.toDate}"  
													type    = "date" 
													pattern = "dd.MM.yyyy"/>"
				 />
			</td>
			
		</tr>
		
	</table>
</body>
</html>