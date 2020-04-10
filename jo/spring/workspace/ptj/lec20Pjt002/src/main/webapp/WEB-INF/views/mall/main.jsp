<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${cp}/resources/css/normal.css" />
</head>
<body>

	<h1>MALL MAIN</h1>
	
	<table>
		<tr>
			<td>구분</td>
			<td>${mall.gender}</td>
		</tr>
	</table>
	
	<c:choose>
		<c:when test="${mall.gender == 'M'}">
			남성 상품 노출
		</c:when>
		<c:when test="${mall.gender == 'W'}">
			여성 상품 노출
		</c:when>
		<c:otherwise>
			남성, 여성 상품 노출
		</c:otherwise>
	</c:choose>

</body>
</html>