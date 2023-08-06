<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html xml:lang>
<head>
<meta charset="UTF-8">
<title>Display Quiz</title>
<style>
table {
	border-collapse: collapse;
	margin: 20px;
	width: 80%;
}

th, td {
	padding: 10px;
	text-align: left;
	vertical-align: top;
	border: 1px solid #ddd;
}

th {
	background-color: #f2f2f2;
	
	color: #666;
}

button {
	padding: 10px;
	margin: 20px;
	border-radius: 5px;
	background-color: #4CAF50;
	color: white;
	border: none;
	cursor: pointer;
}

button:hover {
	background-color: #3e8e41;
}
</style>
</head>
<body>

	<table>
		<caption></caption>
		<c:set var="view" value="${viewQuiz}" />
		<tr>
			<th>Quiz Id</th>
			<td><c:out value="${view.quizId}" /></td>
		</tr>
		<tr>
			<th>Quiz Title</th>
			<td><c:out value="${view.quizTitle}" /></td>
		</tr>
		<tr>
			<th>Question Numbers</th>
			<td><c:forEach items="${view.questions }" var="question">
					<c:out value="${question.displayQuestion()}" />
					<br>
				</c:forEach></td>
		</tr>
		<tr>
			<th>Total Marks</th>
			<td><c:out value="${view.marks}" /><br></td>
		</tr>
	</table>

	<form action="displayAllIdAndSelectId">
		<button type="submit">Back</button>
	</form>

</body>
</html>
