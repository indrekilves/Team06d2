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
    <h2>State Admin Unit Type updated</h2>
    	    
    ID: 	<c:out value="${stateAdmintUnitType.state_admin_unit_type_id}"/><br/>
    Code:	<c:out value="${stateAdmintUnitType.code}"/><br/>
    Name:	<c:out value="${stateAdmintUnitType.name}"/><br/>
	
</body>
</html>