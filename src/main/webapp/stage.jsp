<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Text Quest</title>
    <title>Text Adventure Game - Stage</title>
    <style>
        body {
            background-image: url('/image/welcomeBackground.jpg');
            background-size: cover;
            background-color: #343434;
            text-align: center;
            font-family: Arial, sans-serif;
        }

        #container {
            max-width: 600px;
            margin: 20px auto;
            padding: 20px;
            background-color: rgba(255, 255, 255, 0.8);
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
        }

        #sceneImage {
            max-width: 100%;
            border-radius: 5px;
            margin-bottom: 20px;
        }

        #sceneText {
            font-size: 18px;
            margin-bottom: 20px;
        }

        #choicesContainer {
            width: 100%;
            height: fit-content;
        }

        #row, #cell {
            width: 100%;
            height: fit-content;
        }

        #choiceButton {
            width: 100%;
            margin: 0 10px;
            padding: 10px 20px;
            font-size: 16px;
            align-content: start;
            background-color: #464646;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        #choiceButton:hover {
            background-color: #757575;
        }
    </style>
</head>
<body>
<jsp:include page = "header.jsp"/>
<%@ page import="ua.javarush.textquest.entity.StepChoice" %>


<%
    String description = (String) request.getAttribute("description");
    StepChoice[] choices = (StepChoice[]) request.getAttribute("choises");
    String stageImg = (String) request.getAttribute("stageImg");
%>

<div id="container">
    <img id="sceneImage" src="<%=stageImg%>" alt="Stage">
    <div id="sceneText">
        <p><%= description %>
        </p>
    </div>

        <table id="choicesContainer">
            <%
                for (int i = 0; i < choices.length; i++) {

            %>
            <tr id="row">
                <td id="cell">
                    <form action="/quest/stage?choice=${choices[i].getId()}" method="get">
                        <button id="choiceButton" type="submit" name="choice"
                                value="<%= choices[i].getId()%>"><%= choices[i].getText() %>
                        </button>
                    </form>
                </td>
            </tr>
            <%
                }
            %>
        </table>
</div>

</body>
</html>