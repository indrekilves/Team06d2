<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>BoarderGuard - Edit Admin Unit Type</title>
<link rel="stylesheet" href="./style.css" type="text/css">
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
		
		
			<form method="POST" name="editStateAdminUnitTypeForm" action="?id=${unitType.state_admin_unit_type_id}" >
		
			    <!--  ID: 	<c:out value="${stateAdmintUnitType.state_admin_unit_type_id}"/><br/> -->
			    <div id="leftContainer">
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
						
				    </table>
				</div>
				
				<div id="rightContainer">			
			    	<input type="submit" value="Save">
				</div>
				
			</form>
		</div>
	</div>

	<div id="footer">
		<jsp:include page="footer.jsp"/>
	</div>

</body>
</html>