<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>You are dead</title>
    <style>
        body {
            background-image: url('/image/dead.jpg');
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
            background-color: #af3232;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        #startButton:hover {
            background-color: #962a2a;
        }
    </style>
</head>
<body>
<jsp:include page = "header.jsp"/>
<%
    String description = (String) request.getAttribute("description");
%>
<div id="container">
    <h1>Ви померли</h1>
    <p><%= description%></p>

    <form action="../" method="get">
        <button id="startButton" type="submit">Спробувати ще</button>
    </form>
</div>


</body>
</html>