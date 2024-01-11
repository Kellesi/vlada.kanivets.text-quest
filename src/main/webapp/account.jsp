<%@ page import="ua.javarush.textquest.entity.Account" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Account</title>
    <style>
        body {
            background-image: url('/image/welcomeBackground.jpg');
            background-size: cover;
            text-align: center;
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }

        .account-info {
            max-width: 600px;
            margin: 50px auto;
            padding: 20px;
            background-color: rgba(255, 255, 255, 0.8);
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
        }

        .btn {
            padding: 10px 20px;
            font-size: 18px;
            background-color: #4CAF50;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            margin-top: 10px;
        }

        .btn:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
<jsp:include page = "header.jsp"/>
<%
    Account account = (Account) session.getAttribute("account");
    String username = account.getName();
    int endingsReceived = account.getCollectedEndings();
    int deaths = account.getDeathCount();
%>

<div class="account-info">
    <h2>Інформація про акаунт</h2>
    <p><strong>Ім'я користувача:</strong> <%= username %>
    </p>
    <p><strong>Кількість отриманих кінцівок:</strong> <%= endingsReceived %>
    </p>
    <p><strong>Кількість смертей персонажа:</strong> <%= deaths %>
    </p>
</div>

</body>
</html>