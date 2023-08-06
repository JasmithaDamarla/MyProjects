<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html xml:lang>
<head>
<meta charset="UTF-8">
<title>View All Quiz</title>
<style>
table {
	border-collapse: collapse;
	width: 100%;
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
}

.update-button {
	background-color: #5bc0de;
}

.delete-button {
	background-color: #d9534f;
}

form {
	background-color: #fff;
	padding: 20px;
	border-radius: 10px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
	margin: 0 auto;
	width: 97%;
}
.button:hover {
	background-color: #333;
	color: #FFF;
	box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.5);
}

button:hover {
	background-color: #3e8e41;
}
</style>
</head>
<body>
	<table border="1">
		<caption>Displaying all Quiz</caption>
		<tr>
			<th>Quiz Id</th>
			<th>Title</th>
			<th>Questions</th>
			<th>Marks</th>
			<th>Update Question</th>
			<th>Delete Question</th>
		</tr>
		<c:forEach items="${allQuizList}" var="view">
		<tr>
			<td><c:out value="${view.quizId}" /></td>
			<td><c:out value="${view.quizTitle}" /></td>
			<td>
				<c:forEach items="${view.questions}" var="question">
					<c:out value="${question.displayQuestion()}" />
				</c:forEach>
			
			</td>
			<td><c:out value="${view.marks}" /></td>
			<td><button class="button update-button"
						onclick="location.href='updateQuizGetId?id=${view.quizId}'">Update
						Quiz</button></td>
				<td><button class="button delete-button"
						onclick="location.href='quizDelete?id=${view.quizId}'">Delete
						Quiz</button></td>
		</tr>
		</c:forEach>
	</table>
	<br>
	<form action="QuizHome.jsp">
		<button type="submit">Back</button>
	</form>

</body>
</html>
