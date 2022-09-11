<%@ page import="ro.ubb.model.User" %>
<%--
  Created by IntelliJ IDEA.
  User: Adrian
  Date: 21-May-20
  Time: 16:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Snake Game - Play Snake!</title>
    <link href="resources/general.css" rel="stylesheet" type="text/css">
    <link href="resources/game.css" rel="stylesheet" type="text/css">
    <script src="resources/jquery-3.5.0.min.js"></script>
    <script src="resources/ajax.js"></script>
</head>
<body>
    <header>
        <div>
            <h1>Snake Game</h1>
        </div>
        <form action="logout" method="post">
            <input type="submit" value="Log Out" style="background-color: crimson"/>
        </form>
    </header>
    <main>
        <%
            User user = (User) session.getAttribute("user");
            if (user == null || user.equals("")) {
                out.print("<h3>Session expired! Please Log In again..</h3>");
            } else {
                out.println("<h3>Welcome " + user.getName() + "!</h3>");
            }
        %>
        <br>
        <table>
            <tr>
                <td id="0"></td>
                <td id="1"></td>
                <td id="2"></td>
                <td id="3"></td>
                <td id="4"></td>
            </tr>
            <tr>
                <td id="5"></td>
                <td id="6"></td>
                <td id="7"></td>
                <td id="8"></td>
                <td id="9"></td>
            </tr>
            <tr>
                <td id="10"></td>
                <td id="11"></td>
                <td id="12"></td>
                <td id="13"></td>
                <td id="14"></td>
            </tr>
            <tr>
                <td id="15"></td>
                <td id="16"></td>
                <td id="17"></td>
                <td id="18"></td>
                <td id="19"></td>
            </tr>
            <tr>
                <td id="20"></td>
                <td id="21"></td>
                <td id="22"></td>
                <td id="23"></td>
                <td id="24"></td>
            </tr>
        </table>
        <br>
        <div id="messageBox">
        </div>
    </main>

    <script defer>
        var snakePosition = -1;
        var foodPosition = -1;
        var noOfObstacles = 4;
        var obstaclePositions = [];
        var gameFinished = false;
        var score = 0;
        var movesLeft = 10;

        $(document).ready(function () {
            initializeGame();

            $("td").on("click", function () {
                moveSnake(
                    parseInt($(this).attr("id")), 10);
            });

            function initializeGame() {
                deleteMoves(<%= user.getId() %>, function () { console.log("deleteMoves"); });

                // OBSTACLES
                for (var obstacle = 1; obstacle <= noOfObstacles; obstacle++) {
                    var position = Math.floor(Math.random() * 25);
                    while (obstaclePositions.includes(position) || obstaclePositions.find((currentPosition) => isAdjacent(currentPosition, position, true))) {
                        position = Math.floor(Math.random() * 25);
                    }
                    obstaclePositions.push(position);

                    $("#" + position).addClass("obstacle");
                }

                snakePosition = Math.floor(Math.random() * 25);
                while (obstaclePositions.includes(snakePosition)) {
                    snakePosition = Math.floor(Math.random() * 25);
                }
                $("#" + snakePosition).text("SNAKE");

                foodPosition = Math.floor(Math.random() * 25);
                while (foodPosition == snakePosition || obstaclePositions.includes(snakePosition) || obstaclePositions.includes(foodPosition)) {
                    foodPosition = Math.floor(Math.random() * 25);
                }
                $("#" + foodPosition).text("FOOD");
            }

            function moveSnake(position) {
                if (!gameFinished) {
                    if (snakePosition == position || !isAdjacent(snakePosition, position)) {
                        if (snakePosition == position) {
                            $("#messageBox").text("Selected position not different than the current position of the snake");
                        } else {
                            $("#messageBox").text("Selected position not adjacent to the current position of the snake");
                        }
                        saveMove(<%= user.getId() %>, -1, score, function () { console.log("saveMove", -1, score); });
                        return;
                    }

                    if (obstaclePositions.includes(position)) {
                        gameFinished = true;
                        $("#messageBox").text("You lost: snake hit an obstacle. Your score is: " + score);
                    } else {
                        if (foodPosition == position) {
                            score++;
                            foodPosition = Math.floor(Math.random() * 25);
                            while (foodPosition == position || foodPosition == snakePosition || obstaclePositions.includes(foodPosition)) {
                                foodPosition = Math.floor(Math.random() * 25);
                            }
                            $("#" + foodPosition).text("FOOD");
                        }

                        movesLeft--;
                        if (movesLeft == 0) {
                            gameFinished = true;
                            $("#messageBox").text("You won! Your score is: " + score);
                        } else {
                            $("#messageBox").html("Your score: " + score + "<br>" + "Moves left: " + movesLeft);
                        }

                        $("#" + snakePosition).text("");
                        snakePosition = position;
                        $("#" + snakePosition).text("SNAKE");
                    }

                    saveMove(<%= user.getId() %>, position, score, function () { console.log("saveMove", position, score); });
                }
            }

            function isAdjacent(position, position2, fully=false) {
                // checks if cell is (fully) adjacent with cell2
                let row = Math.floor(position / 5);
                let row2 = Math.floor(position2 / 5);
                let column = position % 5;
                let column2 = position2 % 5;

                return (Math.abs(row - row2) == 0 && Math.abs(column - column2) == 1) ||
                    (Math.abs(row - row2) == 1 && Math.abs(column - column2) == 0) ||
                    (fully && Math.abs(row - row2) == 1 && Math.abs(column - column2) == 1);
            }
        });
    </script>
</body>
</html>
