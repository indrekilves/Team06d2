<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h2>State Admin Unit Type</h2>

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


	<form method="POST" name="editStateAdminUnitTypeForm" action="?id=${stateAdmintUnitType.state_admin_unit_type_id}" >

	    ID: 	<c:out value="${stateAdmintUnitType.state_admin_unit_type_id}"/><br/>
	    Code:	<input name="code" 	value="${stateAdmintUnitType.code}"><br/>
	    Name:	<input name="name" 	value="${stateAdmintUnitType.name}"><br/>
	
	    <input type="submit" value="Save">
	
	</form>

</body>
</html>