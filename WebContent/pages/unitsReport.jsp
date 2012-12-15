<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Edit Admin Unit</title>
	<meta 	http-equiv="Content-Type" 	content="text/html; charset=UTF-8">
	<link  	type="text/css"				rel="stylesheet"	 href="./css/style.css">
 	<script type="text/javascript" 		src="js/unitsReport.js"></script> 
	
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
	
			<form method="POST" id="unitsReport"> 
	
				<input type="hidden" id="id"		name="id"		value="">					
				<input type="hidden" id="origin" 	name="origin"	value="">					
				<input type="hidden" id="exitMode"	name="exitMode"	value="">
				
				<table>
					<tr>
						<td>
<!-- Filters -->			<table>
								<tr>
									<td style="width: 170px;">Date:</td>					
									<td style="width: 170px;">Type:</td>
									<td style="width: 80px;"/>						
								</tr>
								<tr>
		<!-- Date --> 			<td>				
									<input 	name  = "date" 
											class = "datePicker"
											type  = "text"
											style = "width: 120px;" 
											value ="<fmt:formatDate 	value   = "${lastDate}"  
					 													type    = "date"  
					 													pattern = "dd.MM.yyyy"/>"
					 				 /> 
					 			</td>	 
		
		<!-- 
		<input name="date" 	value="" class="datePicker" style="width: 120px;" value ="${lastDate}"></td>
		 -->	
			
									<td>
		<!-- Type -->					<select name="selTypeId" style="width: 150px">
											<option value=""></option>
										
											<c:forEach var="type" items="${types}">
												
												<c:set var="selected" value=""/>
						    	
										    	<c:if test="${type.state_admin_unit_type_id == lastTypeId}">
										     		<c:set var="selected" value="selected=\"selected\""/>
										    	</c:if>
												
												<option value="${type.state_admin_unit_type_id}" ${selected}>${type.name}</option>
												
												
												
											</c:forEach>
										</select>
									</td>
		<!-- Refresh button -->			<td>
									 	<input 	type	= "button" 
									 			value	= "Refresh" 
									 			class 	= "largeButton"  
									 			onclick	= "refreshReport()">
									</td>
								</tr>
							</table>	
						</td>
					</tr>
					
<!-- Spacer -->		<tr height="30px"/>
	
					<tr>
						<td>
<!-- Results -->			<c:forEach var="unit" items="${units}">
								<table style="width:100%">
								 	<tr class = "tableWithBorder">
	<!-- Boss Unit -->					<th align="left"><c:out value="${unit.name}" /></th>
									</tr>
									
									<c:forEach var="subOrdinate" items="${unit.subordinateUnits}">
	<!-- Sub Unit Name -->				<tr style="border: 1px solid black;">
											<td>	
												<table style="width:100%">
													<tr>
														<td><c:out value="${subOrdinate.name}" /></td>
	<!-- View subUnit -->								<td align="right">
														 	<input 	type	= "button" 
														 			value	= "View" 
														 			class 	= "largeButton"  
														 			onclick	= "showSelectedEntry('${subOrdinate.state_admin_unit_id}')">
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</c:forEach>
	<!-- Spacer -->								<tr height="20px"/>
								</table>
							</c:forEach>
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
