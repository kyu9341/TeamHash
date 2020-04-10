<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
	<h1> memRemoveOk </h1>
	
	ID : ${mem.memId}<br />
	
	<P>  The time on the server is ${serverTime}. </P>
	
	<a href="/lec19/resources/html/index.html"> Go Main </a>
</body>
</html>