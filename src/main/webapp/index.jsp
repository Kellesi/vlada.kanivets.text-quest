<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Welcome to the Text Adventure Game</title>
    <style>
        body {
            background-image: url('image/welcomeBackground.jpg');
            background-size: cover;
            text-align: center;
            font-family: Arial, sans-serif;
        }

        #container {
            max-width: 600px;
            margin: 50px auto;
            padding: 20px;
            background-color: rgba(255, 255, 255, 0.8);
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
        }

        #startButton {
            padding: 10px 20px;
            font-size: 18px;
            background-color: #4CAF50;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        #startButton:hover {
            background-color: #45a049;
        }
    </style>
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