<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Welcome to the Text Adventure Game</title>
    <link rel="stylesheet" href="css/index.css"/>
</head>
<body>
<jsp:include page="header.jsp"/>
<div id="container">
    <h1>Дослідження підземелля</h1>
    <p>Ти знаходишся у темному підземеллі, освітленому лише мерехтливим світлом смолоскипів.
        Позаду тебе двері, через які ти сюди потрапив. Попереду лежить лабіринт темних коридорів та загадкових кімнат.
        Ти чуєш шепотіння вітру і мертвого мовчання підземель.
        Твоє завдання - дослідити ці катакомби, розкрити їх таємниці і битися з тими силами, що притаїлися у темряві.
        Попереду на тебе чекають вибори, небезпеки та загадки. Який шлях ти обереш?</p>

    <form action="/quest/stage" method="get">
        <button id="startButton" type="submit">Розпочати</button>
    </form>
</div>

</body>
</html>