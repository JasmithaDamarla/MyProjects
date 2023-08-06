<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html xml:lang>
<head>
<meta charset="ISO-8859-1">
<title>Delete Question</title>
<style>
body {
	background-color: #f2f2f2;
	font-family: Arial, sans-serif;
}

form {
	margin: 20px auto;
	padding: 20px;
	border: 1px solid #ccc;
	max-width: 500px;
	background-color: #fff;
}

input[type="text"] {
	padding: 10px;
	margin-bottom: 10px;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box;
	width: 100%;
}

button[type="submit"] {
	background-color: #4CAF50;
	border: none;
	color: #fff;
	padding: 10px 20px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 16px;
	border-radius: 4px;
	cursor: pointer;
}

button[type="submit"]:hover {
	background-color: #45a049;
}

button[type="submit"]:active {
	background-color: #3e8e41;
}
</style>
</head>
<body>
	<form action="deleteQ" method="post">
		<p>Enter Question Id</p>
		<input type="text" name="id" id="id" required> <br>
		<button type="submit" value="Delete">Delete</button>
	</form>
	
	<form action="QuestionHome.jsp">
		<button type="submit" value="Back">Back</button>
	</form>
</body>
</html>
