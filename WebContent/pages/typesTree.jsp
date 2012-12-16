<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>State Admin Unit Types tree</title>
	<link rel="stylesheet" href="css/style.css" type="text/css">
 	<link rel="stylesheet" href="css/jquery.treeview.css" /> 
 	
	<script	src="js/jquery-1.8.3.js"></script>
	<script	src="js/jquery-ui-1.9.2.custom.js"></script>
	<script src="js/jquery.cookie.js" type="text/javascript"></script>
	<script src="js/jquery.treeview.js" type="text/javascript"></script>
	<script src="js/jquery.treeview.async.js" type="text/javascript"></script>


	<script type="text/javascript">
		$(document).ready(function() {
			$("#black").treeview({
				url : "TypesTreeController"
			})
		});
	</script>

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
	
			<p>State Admin Unit Types tree</p>
	
			<!-- jsTreeview -->
			<ul id="black">
			</ul>
			
		</div>
	</div>

	<div id="footer">
		<jsp:include page="footer.jsp"/>
	</div>

</body>

	
</html>