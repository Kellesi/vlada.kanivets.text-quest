<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>NavBar</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }

        .navbar {
            color: #ffffff;
            padding: 10px;
            display: flex;
            justify-content: space-between;
        }

        .logo {
            color: #ffffff;
            font-size: 24px;
            font-weight: bold;
            text-decoration: none;
        }

        .logo:hover {
            text-decoration: underline;
            cursor: pointer;
        }

        .nav-links {
            display: flex;
            align-items: center;
        }

        .nav-link {
            color: #ffffff;
            text-decoration: none;
            margin-right: 15px;
        }

        .nav-link:hover {
            text-decoration: underline;
            cursor: pointer;
        }

        .account-icon {
            width: 30px;
            height: 30px;
            margin-right: 5px;
        }
    </style>
</head>
<body>
<%
    boolean isLoggedIn = session.getAttribute("isLoggedIn") != null;
%>
<div class="navbar">
    <a href="../" class="logo">Text Quest</a>
    <div class="nav-links">
        <% if (isLoggedIn) { %>
        <a href="/quest/logout" class="nav-link">Logout</a>
        <a href="/quest/account" class="nav-link">
            <img src="/image/account-icon.png" alt="Account" class="account-icon">
        </a>
        <% } else { %>
        <a href="/quest/login" class="nav-link">Залогуватись</a>
        <a href="/quest/registration2" class="nav-link">Реєстрація</a>
        <% } %>
    </div>
</div>

</body>
</html>