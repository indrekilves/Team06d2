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
	</div>

	<div id="footer">
		<jsp:include page="footer.jsp"/>
	</div>

</body>
</html>