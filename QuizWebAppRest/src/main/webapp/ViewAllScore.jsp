<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html xml:lang>
<head>
	<meta charset="UTF-8">
	<title>All Users Score</title>
	<style>
		body {
			background-color: #f1f3f6;
			font-family: 'Segoe UI', sans-serif;
			margin: 0;
			padding: 0;
		}
		table {
			background-color: #fff;
			border-collapse: collapse;
			box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
			margin: 30px auto;
			width: 80%;
			border-radius: 10px;
			overflow: hidden;
		}
		th, td {
			border: 1px solid #dee2e6;
			padding: 12px;
			text-align: center;
			color: #444;
			font-size: 14px;
			font-weight: 400;
		}
		th {
			background-color: #333;
			color: #fff;
			font-weight: bold;
			text-transform: uppercase;
			letter-spacing: 2px;
			font-size: 12px;
		}
		caption {
			font-size: 24px;
			font-weight: bold;
			margin-bottom: 20px;
			text-align: center;
			color: #333;
			text-shadow: 1px 1px 0 #fff;
			line-height: 1.2;
		}
		form {
			display: flex;
			justify-content: center;
			margin-top: 30px;
		}
		button {
			background-color: #2a9d8f;
			color: #fff;
			border: none;
			border-radius: 4px;
			cursor: pointer;
			font-size: 16px;
			padding: 12px 20px;
			transition: background-color 0.3s ease;
			box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3);
		}
		button:hover {
			background-color: #247d6d;
		}
	</style>
</head>
<body>
	<table>
		<caption>Scoreboard of All Users</caption>
		<tr>
			<th>User ID</th>
			<th>Score ( in % )</th>
		</tr>
		<c:forEach items="${userList}" var="user">
			<tr>
				<td><c:out value="${user.getUserName()}" /></td>
				<td><c:out value="${user.getScore()}" /></td>
			</tr>
		</c:forEach>
	</table>

	<form action="UserOperations.jsp">
		<button type="submit" value="Back">Back</button>
	</form>
</body>
</html>
