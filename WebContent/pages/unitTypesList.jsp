<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Possible Subordinates for Admin Unit Type</title>
	<meta 	http-equiv="Content-Type" 	content="text/html; charset=UTF-8">
	<link  	type="text/css"				rel="stylesheet"	 href="./css/style.css">
 	<script type="text/javascript" 		src="js/unitTypesList.js"></script> 
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
			<b>Select type for <c:out value="${unit.name}"/></b><br><br>
			
			<form method="POST"	id="unitTypesList">

				<input type="hidden" id="id"		name="id"		value="">
				<input type="hidden" id="typeId" 	name="typeId"	value="">		
				<input type="hidden" id="origin" 	name="origin"	value="">					
				<input type="hidden" id="exitMode"	name="exitMode"	value="">

				
				
				<table>
<!-- Select subOrdinate -->						
				
					<tr align="left">
						<th width="100px">Code</th>
						<th width="150px">Name</th>
						<th width="50px"></th>
					</tr>
					
						<c:forEach var="type" items="${types}">
							<tr>
								<td>
									 <c:out value="${type.code}" />
								</td>
								<td>
									<c:out value="${type.name}" />
								</td>
								<td>
									<input 	type    = "button" 
								  			value   = "Select" 
								  			name    = "btnSelectSubOrdinate" 
								  			class   = "largeButton" 
								  			onclick = "selectType('${unit.state_admin_unit_id}', '${type.state_admin_unit_type_id}')">	
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
								  			onclick = "cancelTypeSelect('${unit.state_admin_unit_id}')">
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