<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html xml:lang>
<head>
<meta charset="UTF-8">
<title>View Questions</title>
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
		<caption>Displaying all Questions</caption>
		<tr>
			<th>Question Id</th>
			<th>Title</th>
			<th>Difficulty</th>
			<th>Option A</th>
			<th>Option B</th>
			<th>Option C</th>
			<th>Option D</th>
			<th>Topic</th>
			<th>Answer</th>
			<th>Update Question</th>
			<th>Delete Question</th>
		</tr>
		<c:forEach items="${viewQuestions}" var="view">
			<tr>
				<td><c:out value="${view.questionNum}" /></td>
				<td><c:out value="${view.title}" /></td>
				<td><c:out value="${view.difficulty}" /></td>

				<c:forEach items="${view.options}" var="opt">
					<td>${opt.value}</td>
				</c:forEach>

				<td><c:out value="${view.topic }" /></td>
				<td><c:out value="${view.answer}" /></td>
				<td><button class="button update-button"
						onclick="location.href='updateQuestionsGetOpts?id=${view.questionNum}'">Update
						Question</button></td>
				<td><button class="button delete-button"
						onclick="location.href='deleteQ?id=${view.questionNum}'">Delete
						Question</button></td>
			</tr>
		</c:forEach>
	</table>
	<br>
	<form action="QuestionHome.jsp">
		<button type="submit">Back</button>
	</form>

</body>
</html>
