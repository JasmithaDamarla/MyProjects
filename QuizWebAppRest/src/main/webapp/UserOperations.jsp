<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html xml:lang>
<head>
<meta charset="ISO-8859-1">
<title>User Operations</title>
<style>
    body {
        background-color: #f1f1f1;
        margin: 0;
        padding: 0;
    }
    .container {
        width: 80%;
        margin: 0 auto;
        text-align: center;
    }
    h1 {
        margin-top: 50px;
        font-size: 3em;
        color: #333;
    }
    .btn {
        display: inline-block;
        padding: 10px 20px;
        margin: 10px;
        border-radius: 5px;
        font-size: 1.2em;
        color: #fff;
        text-decoration: none;
        cursor: pointer;
        transition: background-color 0.3s ease;
    }
    .btn-take-quiz {
        background-color: #1abc9c;
    }
    .btn-take-quiz:hover {
        background-color: #148f77;
    }
    .btn-view-score {
        background-color: #3498db;
    }
    .btn-view-score:hover {
        background-color: #2c3e50;
    }
    .btn-score-board {
        background-color: #e67e22;
    }
    .btn-score-board:hover {
        background-color: #d35400;
    }
    .btn-logout {
        background-color: #7f8c8d;
        position: absolute;
        top: 20px;
        right: 20px;
    }
    .btn-logout:hover {
        background-color: #95a5a6;
    }
</style>
</head>
<body>
    <div class="container">
        <h1>User Operations</h1>
        <c:set var="user" value="${user}" scope="session"/>
        <div>
            <a href="/takeQuiz" class="btn btn-take-quiz">Take Quiz</a>
            <a href="/viewMyScore" class="btn btn-view-score">View My Score</a>
            <a href="/viewAllUsers" class="btn btn-score-board">Score Board</a>
            <a href="UserHome.jsp" class="btn btn-logout">Log out</a>
        </div>
    </div>
</body>
</html>
