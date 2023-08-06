<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> --%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html xml:lang>
<head>
<meta charset="UTF-8">
<title>Update Question</title>

<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f2f2f2;
}

.form {
	background-color: white;
	padding: 20px;
	border-radius: 10px;
	box-shadow: 0px 0px 5px 2px rgba(0, 0, 0, 0.2);
	margin: 50px auto;
	max-width: 800px;
}

h1 {
	text-align: center;
	margin-top: 50px;
}

table {
	width: 100%;
	border-collapse: collapse;
	margin-top: 20px;
}

td, th {
	padding: 10px;
	border: 1px solid #ccc;
}

input[type="text"], select {
	width: 100%;
	padding: 8px;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box;
	font-size: 16px;
}

input[type="submit"], button {
	background-color: #4CAF50;
	color: white;
	padding: 10px 20px;
	border: none;
	border-radius: 4px;
	cursor: pointer;
	font-size: 16px;
}

input[type="submit"]:hover, button:hover {
	background-color: #3e8e41;
}

.required:after {
	content: "*";
	color: red;
}
</style>
</head>
<body>
	<h1>Update Question</h1>
	<form method="POST" action="/updateQ"  class="form">
		<c:set var="question" value="${question}" />
		<c:set var="opts" value="${opts }"/>
		<c:set var="num" value="${num }"/>
		<table>
			<caption></caption>
			<tr>
				<th>Column</th>
				<th>Value</th>
			</tr>
			<tr>
				<td><label >Question ID:</label></td>
				<td><input type="text" id="id" name="id"
					value="${num}" readonly /></td>
			</tr>
			<tr>
				<td><label for="title">Question Title:</label></td>
				<td><input type="text" id="title" name="title"
					value="${question.title}" required /></td>
			</tr>
			<tr>
				<td><label for="difficulty">Difficulty *</label></td>
				<td><select name="difficulty" id="difficulty" required>
						<option value="" disabled selected>Select a difficulty</option>
						<option value="easy"
							${question.difficulty eq "easy" ? 'selected' :''}>Easy</option>
						<option value="medium"
							${question.difficulty eq "medium" ? 'selected' :''}>Medium</option>
						<option value="hard"
							${question.difficulty eq "hard" ? 'selected' :''}>Hard</option>

				</select></td>
			</tr>

			<tr>
				<td><label for="optA">Options : </label></td>
				<td><input type="text" id="opts" name="opts"
					value="${opts}" required /></td>
			</tr>
			<tr>
				<td><label for="topic">Topic:</label></td>
				<td><input type="text" id="topic" name="topic"
					value="${question.topic}" required /></td>
			</tr>
			<tr>
				<td><label for="answer">Answer:</label></td>
				<td><input type="text" id="answer" name="answer"
					value="${question.answer}" required /></td>
			</tr>
		</table>
		<br>
		<br> <input type="submit" value="Update" />
	</form>
	<br><br>
	<form action="UpdateQuestion.jsp">
		<button type="submit" value="Back">Back</button>
	</form>

</body>
</html>
