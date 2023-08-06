<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html xml:lang>
<head>
<meta charset="ISO-8859-1">
<title>All Users Score</title>
<style>
body {
	background-color: #f2f2f2;
}

.container {
	max-width: 600px;
	margin: 0 auto;
	padding: 20px;
	box-sizing: border-box;
}

table {
	border-collapse: collapse;
	width: 100%;
	font-family: Arial, sans-serif;
}

th, td {
	text-align: left;
	padding: 8px;
}

th {
	background-color: #2e75b6;
	color: white;
}

tr:nth-child(even) {
	background-color: #f2f2f2;
}

button {
	background-color: #4CAF50;
	color: white;
	padding: 8px 12px;
	border: none;
	border-radius: 4px;
	cursor: pointer;
	width: 100%;
	font-family: Arial, sans-serif;
}

button:hover {
	background-color: #3e8e41;
}
</style>
</head>
<body>
	<div class="container">
		<table>
			<caption></caption>
			<tr>
				<th>UserId</th>
				<th>Score ( in % )</th>
				<th>QuizId Taken</th>
			</tr>
			<c:forEach items="${userList}" var="user">
				<tr>
					<td><c:out value="${user.getUserName()}" /></td>
					<td><c:out value="${user.getScore()}" /></td>
					<td><c:out value="${user.getQuiz().getQuizId() }"/></td>
				</tr>
			</c:forEach>
		</table>

		<form action="AdminHome.jsp">
			<button type="submit" value="Back">Back</button>
		</form>
	</div>
</body>
</html>
