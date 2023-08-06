<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xml:lang>
<head>
<meta charset="UTF-8">
<title>Create Question</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f2f2f2;
}

form {
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
	<h1>Create Question</h1>
	<form method="POST" action="/createQ">
		<table>
			<caption></caption>
			<tr>
				<th>Column</th>
				<th>Value</th>
			</tr>
			
			<tr>
				<td><label for="title" class="required">Title</label></td>
				<td><input type="text" id="title" name="title" required></td>
			</tr>
			<tr>
				<td><label for="difficulty">Difficulty *</label></td>
				<td><select name="difficulty" id="difficulty" required>
						<option value="" disabled selected>Select a difficulty</option>
						<option value="easy">Easy</option>
						<option value="medium">Medium</option>
						<option value="hard">Hard</option>
				</select></td>
			</tr>
			<tr>
				<td><label for="optA" class="required">Option a</label></td>
				<td><input type="text" id="opts" name="opts" required></td>
			</tr>
			<tr>
				<td><label for="topic">Topic:</label></td>
				<td><input type="text" name="topic" id="topic" required></td>
			</tr>
			<tr>
				<td><label for="answer">Answer *</label></td>
				<td><input type="text" id="answer" name="answer" required></td>
				
			</tr>
		</table>
		<br>
		<input type="submit" />
	</form>
	<form action="QuestionHome.jsp">
		<button type="submit">Back</button>
	</form>
</body>
</html>
