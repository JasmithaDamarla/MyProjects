<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html xml:lang>
<head>
	<meta charset="UTF-8">
	<title>Take Quiz</title>
	<style>
		body {
			background-color: #F7F7F7;
			font-family: Arial, sans-serif;
			text-align: center;
			margin: 0;
			padding: 0;
		}
		
		h1 {
			color: #505050;
			margin-top: 20px;
		}
		
		form {
			background-color: #FFFFFF;
			border-radius: 10px;
			box-shadow: 0px 0px 10px #888888;
			margin: 20px auto;
			max-width: 800px;
			padding: 20px;
			text-align: left;
		}
		
		form p {
			margin-top: 10px;
		}
		
		form input[type="submit"] {
			background-color: #32CD32;
			border: none;
			border-radius: 20px;
			color: #FFFFFF;
			cursor: pointer;
			font-size: 16px;
			margin-top: 20px;
			padding: 10px 20px;
			transition: background-color 0.2s;
		}
		
		form input[type="submit"]:hover {
			background-color: #228B22;
		}
		
		form label {
			font-weight: bold;
			margin-right: 10px;
		}
		
		form input[type="radio"] {
			margin-right: 5px;
		}
		
	</style>
</head>
<body>
	<h1>Take Quiz</h1>
	<form action="/updateScore" method="post">
		<c:set var="user" value="${user}" scope="session"/>
		<c:set var="quiz" value="${quiz}" scope="session" />
		<br>
		<p>"${quiz.quizTitle}" ("${quiz.marks }"M)</p>
		<c:forEach items="${quiz.getQuestions()}" var="question">
			<p>
				<label>${question.title}</label>
			</p>
			<p>
				<c:forEach items="${question.options}" var="opt">
					<input type="radio" name="${question.questionNum }" value="${opt.key}"/>${opt.value }
					
					<br>
					
				</c:forEach>
			</p>
		</c:forEach>
		<input type="submit" value="Finish"/>
	</form>
</body>
</html>
