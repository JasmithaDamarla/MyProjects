<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html xml:lang>
<head>
<meta charset="ISO-8859-1">
<title>Create Quiz</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f2f2f2;
	margin: 0;
	padding: 0;
}

form {
	margin: 50px auto;
	padding: 20px;
	background-color: #fff;
	border-radius: 5px;
	box-shadow: 0px 0px 10px #ccc;
	width: 500px;
}

h1 {
	text-align: center;
	margin-top: 0;
	margin-bottom: 20px;
	color: #333;
}

table {
	width: 100%;
	margin-bottom: 20px;
	border-collapse: collapse;
}

table td, table th {
	padding: 10px;
	border: 1px solid #ccc;
}

table th {
	text-align: left;
	background-color: #f2f2f2;
	color: #333;
}

input[type="text"], input[type="number"] {
	padding: 8px;
	border-radius: 5px;
	border: 1px solid #ccc;
	width: 100%;
	box-sizing: border-box;
}

input[type="submit"], button[type="submit"] {
	background-color: #008CBA;
	color: #fff;
	border: none;
	padding: 10px 20px;
	border-radius: 5px;
	cursor: pointer;
	font-size: 16px;
	margin-top: 20px;
}

button[type="submit"] {
	background-color: #f44336;
}

input[type="submit"]:hover, button[type="submit"]:hover {
	background-color: #004e66;
}
</style>
</head>
<body>
	<form action="/quizCreate" method="post">
		
		<table>
			<caption><b>Create Quiz</b></caption>
			
			<tr>
				<th><label for="title">Quiz Title:</label></th>
				<td><input type="text" id="title" name="title" required></td>
			</tr>
			<tr>
				<th><label for="questions">Question Numbers:</label></th>
				<td>
				<c:forEach items="${questionsList}" var="question">
					<input type="checkbox" name="list" value="${question.questionNum}"/>
				<c:out value="${question.title}" />
				<br>
				</c:forEach>
				</td>
			</tr>
			<tr>
				<th><label for="marks">Total Marks:</label></th>
				<td><input type="number" id="marks" name="marks"></td>
			</tr>
		</table>
		<input type="submit" value="Create Quiz">
	</form>

	<form action="QuizHome.jsp">
		<button type="submit">Back</button>
	</form>
</body>
</html>
