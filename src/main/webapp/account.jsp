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
    </style>
</head>
<body>
<jsp:include page="header.jsp"/>
<%
    Account account = (Account) session.getAttribute("account");
    String username = account.getName();
    int endingsReceived = account.getCollectedEndings();
    int deaths = account.getDeathCount();
    String savePoint = account.getSavePoint();
%>

<div class="account-info">
    <h2>Інформація про акаунт</h2>
    <p><strong>Ім'я користувача:</strong> <%= username %>
    </p>
    <p><strong>Кількість отриманих кінцівок:</strong> <%= endingsReceived %>
    </p>
    <p><strong>Кількість смертей персонажа:</strong> <%= deaths %>
    </p>

    <% if (savePoint != null) { %>
    <form action="/quest/stage" method="get">
        <button id="choiceButton" type="submit" name="choice" value=<%=savePoint%>>
            Продовжити
        </button>
    </form>
    <% } else { %>
    <p>Намає контрольної точки</p>
    <% } %>
</div>

</body>
</html>