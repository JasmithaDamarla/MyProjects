<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html xml:lang>
<head>
<meta charset="ISO-8859-1">
<title>Quiz Admin Dashboard</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f2f2f2;
}

h1 {
	color: #333;
	text-align: center;
	margin-top: 50px;
}

.button-container {
	text-align: center;
	margin-top: 50px;
}

.button {
	display: inline-block;
	background-color: #4CAF50;
	color: #fff;
	padding: 10px 20px;
	text-align: center;
	text-decoration: none;
	border-radius: 4px;
	border: none;
	cursor: pointer;
	margin: 10px;
}

.button:hover {
	background-color: #3e8e41;
}

a {
	color: #333;
	text-decoration: none;
}

a:hover {
	text-decoration: underline;
}

.back-button {
	position: absolute;
	top: 20px;
	right: 20px;
	background-color: #333;
	color: #fff;
	border: none;
	padding: 10px 20px;
	border-radius: 4px;
	cursor: pointer;
	font-size: 16px;
}

.back-button:hover {
	background-color: #555;
}
</style>
</head>
<body>
	<button class="back-button" onclick="location.href='AdminHome.jsp'">Back</button>
	<h1>Quiz Admin Dashboard</h1>
	<div class="button-container">
		<a href="allQuestions" class="button"
			style="background-color: #2196F3;">Create Quiz</a> <a
			href="displayAllIdAndSelectId" class="button" style="background-color: #FF9800;">View
			Quiz</a> 
			<br>
			<a href="allQuiz" class="button"
			style="background-color: #691E63;">View All Quiz</a>
	</div>
</body>
</html>
