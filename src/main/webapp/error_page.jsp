<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Помилка</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-image: url('/image/welcomeBackground.jpg');
            background-size: cover;
            margin: 20px;
        }

        .container {
            max-width: 600px;
            margin: 0 auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        h1 {
            color: #ff0000;
        }

        p {
            margin-bottom: 20px;
        }

        .back-to-home {
            display: block;
            margin-top: 20px;
            text-align: center;
        }

        .back-to-home a {
            color: #4caf50;
            text-decoration: none;
        }

        .back-to-home a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<div class="container">
    <h1>Помилка</h1>
    <p>Сталася помилка під час вашого запиту</p>

    <div class="back-to-home">
        <a href="/">Повернутися на головну сторінку</a>
    </div>
</div>

</body>
</html>