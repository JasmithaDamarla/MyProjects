<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html xml:lang>
<head>
<meta charset="ISO-8859-1">
<title>Sign Up user</title>
<style>
body {
	background-color: #f5f5f5;
	font-family: Arial, sans-serif;
	margin: 0;
	padding: 0;
}

h1 {
	text-align: center;
	margin-top: 50px;
	color: #333;
	text-shadow: 1px 1px 1px rgba(0, 0, 0, 0.3);
}

form {
	margin: 50px auto;
	padding: 20px;
	background-color: #fff;
	border-radius: 5px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
	max-width: 500px;
	text-align: center;
}

table {
	width: 100%;
}

td {
	padding: 10px;
}

label {
	display: block;
	font-weight: bold;
	margin-bottom: 10px;
}

input[type=text], input[type=password] {
	width: 100%;
	padding: 10px;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box;
	margin-bottom: 20px;
	font-size: 16px;
	color: #666;
	background-color: #f7f7f7;
	box-shadow: inset 0 1px 2px rgba(0, 0, 0, 0.1);
}

input[type=text]:focus, input[type=password]:focus {
	border-color: #2ecc71;
	box-shadow: 0 0 5px rgba(46, 204, 113, 0.5);
}

input[type=submit] {
	background-color: #3498db;
	color: #fff;
	padding: 10px 20px;
	border: none;
	border-radius: 4px;
	cursor: pointer;
	font-size: 16px;
	transition: background-color 0.3s ease;
}

input[type=submit]:hover {
	background-color: #2980b9;
}

.error-msg {
	color: #c0392b;
	margin-top: 5px;
	margin-bottom: 0;
	font-weight: bold;
	font-size: 14px;
}
</style>

</head>
<body>
	<h1>Sign Up User</h1>
	<form action="/registerUser" method="POST">
		<table>
			<caption>Registering User into App</caption>
			<tr>
				<th></th>
				<th></th>
			</tr>
			<tr>
				<td><label for="username">Username:</label></td>
				<td><input type="text" id="username" name="username" required></td>
			</tr>
			<tr>
				<td><label for="password">Password:</label></td>
				<td><input type="password" id="password" name="password"
					required></td>
			</tr>
			<tr>
				<td><label for="confirm">Confirm Password:</label></td>
				<td><input type="password" id="confirm" name="confirm" required></td>
			</tr>
		</table>
		<input type="submit" value="Sign Up">
	</form>

</body>
</html>