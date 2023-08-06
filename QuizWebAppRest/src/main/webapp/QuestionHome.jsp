<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xml:lang>
<head>
    <meta charset="UTF-8">
    <title>Question Library</title>
    
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #F5F5F5;
        }

        h1 {
            color: #333;
            text-align: center;
            margin-top: 50px;
            margin-bottom: 30px;
        }

        .button-container {
            display: flex;
            justify-content: center;
            align-items: center;
            flex-wrap: wrap;
            margin-top: 50px;
        }

        .button {
            display: flex;
            justify-content: center;
            align-items: center;
            background-color: #FFF;
            color: #333;
            padding: 20px 40px;
            text-align: center;
            text-decoration: none;
            border-radius: 4px;
            border: none;
            cursor: pointer;
            margin: 10px;
            width: 200px;
            height: 80px;
            font-size: 18px;
            font-weight: bold;
            box-shadow: 0px 2px 5px rgba(0,0,0,0.3);
            transition: background-color 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
        }

        .button:hover {
            background-color: #333;
            color: #FFF;
            box-shadow: 0px 2px 5px rgba(0,0,0,0.5);
        }

        .create-button {
            background-color: #5cb85c;
        }

        

        .view-button {
            background-color: #f0ad4e;
        }


        a {
            color: #333;
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
        }

        .back-button {
            position: absolute;
            top: 20px;
            right: 20px;
            background-color: #333;
            color: #fff;
            border: none;
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s ease-in-out;
        }
        .back-button:hover {
            background-color: #555;
        }
    </style>
</head>
<body>
    <button class="back-button" onclick="location.href='AdminHome.jsp'">Back</button>
    <div class="container">
        <h1>Question Library</h1>
        <div class="button-container">
            <button class="button create-button" onclick="location.href='CreateQuestion.jsp'">Create Question</button>
            <button class="button view-button" onclick="location.href='viewQ'">View Question</button>
        </div>
    </div>
</body>
</html>
