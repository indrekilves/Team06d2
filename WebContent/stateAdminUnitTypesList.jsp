<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>BoarderGuard - Admin Unit Types</title>
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
		<b>State Admin Unit types</b><br><br>

		<table>
		<tr align="left">
			<th width="40px">ID</th>
			<th width="100px">Code</th>
			<th width="150px">Name</th>
			<th width="50px"></th>
		</tr>
		
			<c:forEach var="type" items="${stateAdminUnitTypes}">
				<tr>
					<td>
						 <c:out value="${type.state_admin_unit_type_id}" />
					</td>
					<td>
						 <c:out value="${type.code}" />
					</td>
					<td>
						<c:out value="${type.name}" />
					</td>
					<td>
						<a href="?action=editStateAdminUnitType&id=${type.state_admin_unit_type_id}">Edit</a>
					</td>
				
				</tr>
			</c:forEach>
		<tr height="60px" valign="bottom">
			<td></td>
			<td></td>
			<td></td>
			<td>
				<a href="?action=addStateAdminUnitType">Add</a>
			</td>
		</tr>
		</table>
		</div>
	</div>
	
	<div id="footer">
		<jsp:include page="footer.jsp"/>
	</div>
	


</body>
</html>