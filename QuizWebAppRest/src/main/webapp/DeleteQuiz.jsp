<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html xml:lang>
<head>
<meta charset="ISO-8859-1">
<title>View Quiz</title>
<style>
body {
	background-color: #f2f2f2;
	font-family: Arial, sans-serif;
}

h1 {
	text-align: center;
	margin-top: 30px;
	margin-bottom: 30px;
}

form {
	background-color: #fff;
	padding: 20px;
	border-radius: 10px;
	box-shadow: 0px 0px 10px #c9c9c9;
	margin: 0 auto;
	max-width: 400px;
}

label {
	display: block;
	margin-bottom: 10px;
}

input[type="text"], input[type="number"] {
	width: 100%;
	padding: 10px;
	border-radius: 5px;
	border: 1px solid #ccc;
	margin-bottom: 20px;
	box-sizing: border-box;
}

button[type="submit"] {
	background-color: #4CAF50;
	color: #fff;
	border: none;
	border-radius: 5px;
	padding: 10px 20px;
	cursor: pointer;
	margin-top: 20px;
	width: 100%;
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

button[type="submit"]:hover {
	background-color: #3e8e41;
}

button[type="submit"]:focus {
	outline: none;
}

button.back-button {
	background-color: #ccc;
	color: #fff;
	border: none;
	border-radius: 5px;
	padding: 10px 20px;
	cursor: pointer;
	margin-top: 20px;
	position: absolute;
	top: 10px;
	right: 10px;
}

button.back-button:hover {
	background-color: #333;
}
</style>
</head>

<body>
	<h1>Delete Quiz</h1>
	<button class="back-button" onclick="location.href='QuizHome.jsp'">Back</button>
	<form action="quizDelete" method="post">
		<label for="id">Quiz :</label> 
			
			<select name="id" id="id" required>
						<option value="" disabled selected>Select quizTitle</option>
						<c:forEach items="${displayIds}" var="view">
							<option value="${view.quizId}">${view.quizTitle}</option>
						</c:forEach>
				</select>
		<button type="submit" value="Delete">Delete</button>
	</form>
	<br>
</body>
</html>
