<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html xml:lang>
<head>
	<meta charset="ISO-8859-1">
	<title>Start Quiz</title>
	<style>
		body {
			font-family: Arial, sans-serif;
			background-color: #f2f2f2;
		}
		h4 {
			color: #333;
			margin-top: 30px;
			margin-bottom: 20px;
			font-size: 24px;
			font-weight: bold;
			text-align: center;
		}
		form {
			display: flex;
			justify-content: center;
			align-items: center;
			margin-top: 50px;
		}
		button {
			background-color: #4CAF50;
			color: white;
			padding: 12px 20px;
			border: none;
			border-radius: 4px;
			cursor: pointer;
			font-size: 16px;
			transition: background-color 0.3s ease;
		}
		button:hover {
			background-color: #3e8e41;
		}
	</style>
</head>
<body>
	<h4>Click the button below to start the quiz</h4>
	<c:set var="user" value="${user}" scope="session"/>
	<form action="/startQuiz" method="post">
		<button type="submit" value="Start Quiz">Start Quiz</button>
	</form>
</body>
</html>
