<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xml:lang>
<head>
<meta charset="UTF-8">
<title>Admin Home</title>
<style>
body {
	font-family: Arial, sans-serif;
	font-size: 16px;
	line-height: 1.5;
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	height: 100vh;
	background-color: #F5F5F5;
}

h1 {
	color: #223;
	text-align: center;
	font-size: 36px;
	font-weight: bold;
	margin-bottom: 40px;
}

p {
	color: #333333;
	text-align: center;
	margin-top: 0;
}

.container {
	max-width: 800px;
	width: 100%;
	padding: 40px;
	background-color: #FFFFFF;
	border-radius: 8px;
	box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
	display: flex;
	justify-content: space-between;
	align-items: center;
	position: relative;
}

.button-container {
	display: flex;
	justify-content: center;
	align-items: center;
	margin-top: 20px;
}

button {
	background-color: #0066CC;
	color: #FFFFFF;
	border: none;
	padding: 10px 20px;
	border-radius: 4px;
	font-size: 16px;
	cursor: pointer;
	margin: 0 10px;
	transition: background-color 0.2s ease-in-out;
}

button:hover {
	background-color: #0052A3;
}

.logout-btn {
	background-color: #CC0000;
	color: #FFFFFF;
	border: none;
	padding: 10px 20px;
	border-radius: 4px;
	font-size: 16px;
	cursor: pointer;
	position: absolute;
	top: 20px;
	right: 20px;
	transition: background-color 0.2s ease-in-out;
}

.logout-btn:hover {
	background-color: #990000;
}
</style>
</head>
<body>
	<div class="container">
		<h1>Admin Home</h1>
		<form action="UserHome.jsp">
			<button class="logout-btn" type="submit" value="Logout">Logout</button>
		</form>
	</div>
	<div class="container">
		<div class="button-container">
			<button onclick="window.location.href='QuestionHome.jsp'">Question
				Library</button>
			<button onclick="window.location.href='QuizHome.jsp'">Quiz
				Library</button>
			<button onclick="window.location.href='AddAdmin.jsp'">Add Admin</button>
			<button onclick="window.location.href='/viewAllUsersAdmin'">Score
				Board</button>
		</div>
	</div>
	<p>This is the admin home page. You can use the buttons above to
		access the question library, quiz library, and score board.</p>
</body>
</html>
