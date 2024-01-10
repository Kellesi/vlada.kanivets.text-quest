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
            background-color: rgba(255, 255, 255, 0.8); /* Прозрачность белого цвета для контейнера */
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
        }

        #startButton {
            padding: 10px 20px;
            font-size: 18px;
            background-color: #4CAF50; /* Цвет кнопки start */
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        #startButton:hover {
            background-color: #45a049; /* Измененный цвет кнопки при наведении */
        }
    </style>
</head>
<body>

<div id="container">
    <h1>Welcome to the Text Adventure Game</h1>
    <p>This is a thrilling text-based adventure where your choices shape the story.</p>

    <form action="./quest/stage" method="get">
        <button id="startButton" type="submit">Start</button>
    </form>
</div>


</body>
</html>