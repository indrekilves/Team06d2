<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Edit Admin Unit</title>
	<meta 	http-equiv="Content-Type" 	content="text/html; charset=UTF-8">
	<link  	type="text/css"				rel="stylesheet"	 href="./css/style.css">
 	<script type="text/javascript" 		src="js/unitsReport.js"></script> 
	
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
	
			<form method="POST" id="unitsReport"> 
	
				<input type="hidden" id="origin" 	name="origin"	value="">					
				<input type="hidden" id="exitMode"	name="exitMode"	value="">
				
				<input type="text" name="date1" id="date1" class="datePicker" />

				
				
<!-- Filters --><table>
					<tr>
						<td>Date:</td>
						<td>Type:</td>
						<td/>						
					</tr>
					<tr>
<!-- Date --> 			<td><input name="date" 	value="" class="datePicker"></td>
						<td>
<!-- Type -->				<select name="typeId">
								<option value=""></option>
							
								<c:forEach var="type" items="${types}">
									<option value="${type.state_admin_unit_type_id}">${type.name}</option>
								</c:forEach>
							</select>
						</td>
<!-- Refresh -->		<td>
						 	<input 	type	= "button" 
						 			value	= "Refresh" 
						 			class 	= "largeButton"  
						 			onclick	= "refreshReport()">
						</td>
					</tr>
				</table>	
				
				<c:forEach var="unit" items="${units}">
					<c:out value="${unit.name}" /><br>
				</c:forEach>
				
			</form>	
						
		</div>
	</div>

	<div id="footer">
		<jsp:include page="footer.jsp"/>
	</div>

</body>
</html>
