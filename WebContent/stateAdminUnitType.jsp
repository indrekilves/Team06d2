<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="./style.css" type="text/css">
<title>BoarderGuard - Admin Unit Type</title>
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
			<b>State Admin Unit Type is updated</b><br><br>
	
		    ID: 			<c:out value="${stateAdmintUnitType.state_admin_unit_type_id}"/><br>
		    Code:			<c:out value="${stateAdmintUnitType.code}"/><br>
		    Name:			<c:out value="${stateAdmintUnitType.name}"/><br>
		    Comment:		<c:out value="${stateAdmintUnitType.comment}"/><br>
		    Subordinate of:	<c:out value="${stateAdmintUnitType.bossAdminUnitType.name}"/><br>
		    From date:		<c:out value="${stateAdmintUnitType.fromDate}"/><br>
		    To date:		<c:out value="${stateAdmintUnitType.toDate}"/><br>
		    SubOrdinates:	<c:forEach var="subType" items="${stateAdmintUnitType.subordinateAdminUnitTypes}">
								<c:out value="${subType.name}" /></br>
							</c:forEach>
		    
	    </div>
	</div>
	
	<div id="footer">
		<jsp:include page="footer.jsp"/>
	</div>
		
</body>
</html>