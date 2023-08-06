<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html xml:lang>
<head>
<meta charset="ISO-8859-1">
<title>Update Question</title>
<style>
body {
	background-color: #f2f2f2;
}

select {
    width: 100%;
    padding: 10px;
    border-radius: 5px;
    border: 1px solid #ccc;
    margin-bottom: 20px;
    box-sizing: border-box;
    background-color: #fff;
}

select option {
    padding: 5px 10px;
}

select option:checked {
    background-color: #4CAF50;
    color: #fff;
}

form {
	background-color: #fff;
	padding: 20px;
	border-radius: 10px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
	margin: 0 auto;
	width: 50%;
}

table {
	margin-bottom: 20px;
}

td {
	padding: 10px;
}

input[type=text] {
	padding: 8px;
	border-radius: 5px;
	border: none;
	background-color: #f1f1f1;
	margin-bottom: 10px;
	width: 100%;
	box-sizing: border-box;
}

input[type=submit], button[type=submit] {
	background-color: #4CAF50;
	color: white;
	padding: 12px 20px;
	border: none;
	border-radius: 4px;
	cursor: pointer;
	margin-top: 20px;
}

input[type=submit]:hover, button[type=submit]:hover {
	background-color: #45a049;
}

button[type=submit] {
	background-color: #008CBA;
}
</style>
</head>
<body>

	<form method="POST" action="/updateQuizGetId">
		<table>
			<caption>
				<h1>Update Quiz</h1>
			</caption>
			<tr>
				<th></th>
				<th></th>
			</tr>
			<tr>
				<td><label>Quiz :</label></td>
				<td><select name="id" id="id" required>
						<option value="" disabled selected>Select quizTitle</option>
						<c:forEach items="${displayIdsAndSelect}" var="view">
							<option value="${view.quizId}">${view.quizTitle}</option>
						</c:forEach>
				</select></td>
			</tr>
		</table>
		<input type="submit" value="Update" /> <br>
	</form>
	<br>
	<form action="QuizHome.jsp">
		<button type="submit" value="Back">QuizHome</button>
	</form>

</body>
</html>
