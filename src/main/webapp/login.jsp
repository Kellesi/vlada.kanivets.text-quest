<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-image: url('/image/welcomeBackground.jpg');
            background-size: cover;
            margin: 0;
            text-align: center;
        }

        #container {
            width: 300px;
            margin: 50px auto;
            padding: 20px;
            background-color: rgba(255, 255, 255, 0.8);
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
            text-align: center;
        }

        label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
            color: #333;
        }

        input {
            width: 100%;
            padding: 8px;
            margin-bottom: 16px;
            box-sizing: border-box;
        }

        input[type="submit"] {
            background-color: #4caf50;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }

        p.error-message {
            color: red;
            margin-top: 16px;
        }
    </style>
</head>
<body>
<jsp:include page="header.jsp"/>
<div id="container">
    <form action="/quest/login2" method="post">

        <label for="login">Логін:</label>
        <input type="text" id="login" name="login" required>

        <label for="password">Пароль:</label>
        <input type="password" id="password" name="password" required>

        <input type="submit" value="Логін">

        <c:if test="${not empty requestScope.errorMessage}">
            <p class="error-message">${requestScope.errorMessage}</p>
        </c:if>
    </form>
</div>
</body>
</html>
