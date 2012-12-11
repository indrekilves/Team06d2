<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


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
		
			<table>
				<tr>
					<td>
						<!-- Left panel -->
						<form 	method	= "POST" 
							name	= "saveStateAdminUnitTypeForm" 
							action	= "?action=saveStateAdminUnitType&id=${unitType.state_admin_unit_type_id}"
							id 		= "saveForm">
							
					    	<jsp:include page="stateAdminUnitTypeFormLeftPanel.jsp"/>			    	
						</form>	
					</td>
				
				
					<td width="15px"></td>
					
					<td valign="top">
						<!-- Right panel -->
						<jsp:include page="stateAdminUnitTypeFormRightPanel.jsp"/>			    	
					</td>
					
				</tr>
				<tr>
					<td></td>
				    <td></td>
					<td align="right">	
						<table>
							<tr height="60px" valign="bottom">
								<td>			
									<!-- Save button -->
								 	<input type="submit" value="Save" name="btnSave" form = "saveForm" class="largeButton">
								</td>
								<td>
									<!-- Cancel button -->					
									<form 	method	= "POST" 
											name	= "CancelStateAdminUnitType" 
											action 	= "?action=cancelStateAdminUnitType&id=${unitType.state_admin_unit_type_id}" >
								    	
								    	<input type="submit" value="Cancel" name="btnCancel" class="largeButton">
									</form>
								</td>
							</tr>						
						</table>
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