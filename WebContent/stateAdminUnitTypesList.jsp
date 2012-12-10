<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>State Admin Unit Types</title>
</head>
<body>
	<h2>State Admin Unit Types</h2>

	<c:forEach var="type" items="${stateAdminUnitTypes}">
		ID: <c:out value="${type.state_admin_unit_type_id}" />
		Code: <c:out value="${type.code}" />
		Name: <c:out value="${type.name}" />

		<a href="?action=editStateAdminUnitType&id=${type.state_admin_unit_type_id}">Edit</a>
		<br />
	</c:forEach>
</body>
</html>