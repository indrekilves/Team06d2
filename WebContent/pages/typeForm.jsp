<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Edit Admin Unit</title>
	<meta 	http-equiv="Content-Type" 	content="text/html; charset=UTF-8">
	<link  	type="text/css"				rel="stylesheet" 	href="./style.css">
 	<script type="text/javascript" 		src="js/typeForm.js"></script> 
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
		
			<form method="POST" id="typeForm"> 
	
				<input type="hidden" id="id"		name="id"		value="">					
				<input type="hidden" id="subId" 	name="subId"	value="">					
				<input type="hidden" id="origin" 	name="origin"	value="">					
				<input type="hidden" id="exitMode"	name="exitMode"	value="">
		
					
				<table>
					<tr>
<!-- Left panel --> 	<td>
 	  						<jsp:include page="typeFormLeftPanel.jsp"/>			    	
						</td>
					
					
<!-- Center panel -->	<td width="15px"></td>
						
<!-- Right panel -->	<td valign="top">
							<jsp:include page="typeFormRightPanel.jsp"/>			    	
						</td>
						
					</tr>
					<tr>
						<td></td>
					    <td></td>
						<td align="right">	
							<table>
								<tr height="60px" valign="bottom">
<!-- Save button -->				<td>			
									 	<input 	type	= "button" 
									 			id		= "saveType"
									 			value	= "Save" 
									 			class 	= "largeButton"  
									 			onclick	= "saveForm('${type.state_admin_unit_type_id}')">
									</td>
<!-- Cancel button -->				<td>
									 	<input 	type	= "button" 
									 			id 		= "cancelType"
									 			value	= "Cancel" 
									 			class 	= "largeButton"  
									 			onclick	= "cancelForm('${type.state_admin_unit_type_id}')">
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