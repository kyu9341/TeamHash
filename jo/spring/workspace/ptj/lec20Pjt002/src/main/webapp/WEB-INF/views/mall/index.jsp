<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${cp}/resources/css/normal.css" />
<style>
	td {text-align: center; }
</style>
</head>
<body>

	<h1>MALL INDEX</h1>
	
	<form:form action="${cp}/mall/main" method="get" commandName="mall">
		<table>
			<tr>
				<td>구분</td>
				<td>
					<form:radiobutton path="gender" value="M"/>남성, 
					<form:radiobutton path="gender" value="W"/>여성
				</td>
			</tr>
			<tr>
				<td colspan="2"><form:checkbox path="cookieDel"/>쿠키삭제</td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="OK"></td>
			</tr>
		</table>
	</form:form>
	
	<c:choose>
		<c:when test="${gender == 'M'}">
			남성 상품 노출
		</c:when>
		<c:when test="${gender == 'W'}">
			여성 상품 노출
		</c:when>
		<c:otherwise>
			남성, 여성 상품 노출
		</c:otherwise>
	</c:choose>

</body>
</html>