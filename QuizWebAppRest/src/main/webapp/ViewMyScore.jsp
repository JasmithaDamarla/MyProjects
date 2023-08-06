<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html xml:lang>
<head>
<meta charset="ISO-8859-1">
<title>My Score</title>
<style>
body {
    font-family: Arial, Helvetica, sans-serif;
    background-color: #F2F2F2;
}

h1, h2, h3, h4, h5, h6 {
    color: #3F51B5;
}

.container {
    max-width: 600px;
    margin: 0 auto;
    text-align: center;
    padding: 20px;
    background-color: #FFFFFF;
    border-radius: 10px;
    box-shadow: 0px 0px 10px 0px rgba(0,0,0,0.5);
}

button {
    background-color: #3F51B5;
    color: #FFFFFF;
    border: none;
    padding: 10px 20px;
    border-radius: 5px;
    cursor: pointer;
    margin-top: 20px;
}
</style>
</head>
<body>
	<div class="container">
		<c:set var="score" value="${scoreValue}" />
		<b><c:out value="${score}" /></b>
		<form action="UserOperations.jsp">
			<button type="submit" value="Back">Back</button>
		</form>
	</div>
</body>
</html>
