<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html xml:lang>
<head>
    <meta charset="ISO-8859-1">
    <title>Quiz Already Taken</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #F9F9F9;
            text-align: center;
        }

        h1 {
            color: #333;
        }

        button {
            background-color: #008CBA;
            color: #FFF;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }

        button:hover {
            background-color: #006F8C;
        }
    </style>
</head>
<body>
    <h1>Quiz Already Taken</h1>
    <p>You have already taken the quiz.</p>
    <form action="UserOperations.jsp">
        <button type="submit">Back</button>
    </form>
</body>
</html>
