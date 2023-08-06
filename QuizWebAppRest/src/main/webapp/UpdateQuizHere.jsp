<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html xml:lang>
<head>
<meta charset="UTF-8">
<title>Update Quiz</title>
<style>body {
	font-family: Arial, sans-serif;
	background-color: #f2f2f2;
	margin: 0;
	padding: 0;
}

.form {
	margin: 50px auto;
	padding: 20px;
	background-color: #fff;
	border-radius: 5px;
	box-shadow: 0px 0px 10px #ccc;
	width: 500px;
}

h1 {
	text-align: center;
	margin-top: 0;
	margin-bottom: 20px;
	color: #333;
}

table {
	width: 100%;
	margin-bottom: 20px;
	border-collapse: collapse;
}

table td, table th {
	padding: 10px;
	border: 1px solid #ccc;
}

table th {
	text-align: left;
	background-color: #f2f2f2;
	color: #333;
}

input[type="text"], input[type="number"] {
	padding: 8px;
	border-radius: 5px;
	border: 1px solid #ccc;
	width: 100%;
	box-sizing: border-box;
}

input[type="submit"], button[type="submit"] {
	background-color: #008CBA;
	color: #fff;
	border: none;
	padding: 10px 20px;
	border-radius: 5px;
	cursor: pointer;
	font-size: 16px;
	margin-top: 20px;
}

button[type="submit"] {
	background-color: #f44336;
}

input[type="submit"]:hover, button[type="submit"]:hover {
	background-color: #004e66;
}
</style>
</head>
<body>
	<div class="container">
		<h1>Update Quiz</h1>
		<form action="/quizUpdate" method="get">
			<c:set var="quiz" value="${quiz}" />
			<c:set var="questions" value="${questionsList}" />
			<table>
				<caption>Quiz Update need to enter fields that need to be
					modified</caption>
					<br>

				<tr>
					<th><label>Quiz ID:</label></th>
					<td><input type="text" id="quizId" name="quizId"
						value="${quiz.quizId}" readonly="readonly"></td>
				</tr>
				<tr>
					<th><label for="title">Quiz Title:</label></th>
					<td><input type="text" id="title" name="title"
						value="${quiz.quizTitle}" required></td>
				</tr>
				<tr>
					<th><label for="questionsList">Questions :</label></th>
					<td>
						  <c:forEach items="${questions}" var="question">
						    <c:set var="checked" value="false" />
						    <c:forEach items="${quiz.questions}" var="quizQuestion">
						      <c:if test="${quizQuestion.questionNum == question.questionNum}">
						        <c:set var="checked" value="true" />
						      </c:if>
						    </c:forEach>
						    <input type="checkbox" name="list" value="${question.questionNum}" <c:if test="${checked}">checked</c:if> />
						    <c:out value="${question.title}" />
						    <br>
						  </c:forEach>
					</td>

				</tr>
				<tr>
					<th><label for="marks">Total Marks:</label></th>
					<td><input type="number" id="marks" name="marks"
						value="${quiz.marks}"></td>
				</tr>
			</table>
			<b><input type="submit" value="Update Quiz" class="update-button" style="background-color: #9C27B0;"></b>
		</form>
		<br> <br>
		<form action="displayAllIdAndSelectIdUpdate">
			<button class="back-button" type="submit">Back</button>
		</form>
	</div>
</body>
</html>
