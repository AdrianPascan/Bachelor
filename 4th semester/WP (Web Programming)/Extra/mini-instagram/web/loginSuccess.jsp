<%@ page import="ro.ubb.model.User" %>
<%@ page import="java.util.List" %>
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
    <title>Mini-instagram</title>
    <link href="resources/general.css" rel="stylesheet" type="text/css">
    <script src="resources/jquery-3.5.0.min.js"></script>
    <script src="resources/ajax.js"></script>
</head>
<body>
    <header>
        <div>
            <h1>Mini-instagram</h1>
        </div>
    </header>
    
    <hr>

    <main>
        <section id="userSection">
            <h3 id="userMessage"></h3>

            <form action="logout" method="post">
                <input type="submit" value="Log Out"/>
            </form>
        </section>
        <hr>

        <section id="uploadPhotoSection">
            <h3>Upload photo: </h3>
            <i style="font-size: smaller">Make sure you upload your photo in the photos directory beforehand.</i>
            <br>

            <br>

            <form>
                <label>Source: </label>
                <input type="text" id="uploadSource" placeholder="file name">
                <br>

                <br>

                <input type="button" id="uploadBtn" value="Upload" onclick="addPhoto()">
            </form>
        </section>

        <hr>

        <section id="photoSection">
            <h3>Photos:</h3>

            <br>

            <b>Top no. prictures</b>
            <br>

            <br>

            <label>No. :</label>
            <input type="number" id="photoTop" placeholder="number" >

            <br>

            <input type="button" value="Show" onclick="setTop()">

            <br><br><br>

            <div id="photoItems">
            </div>
        </section>
    </main>

    <script defer>
        var topNo = -1;

        $(document).ready(function() {
            setupUser();

            getPhotos();

            $(document).on("click", ".voteBtn", function () {
                let photoId = parseInt( $(this).attr("id") );
                votePhoto(photoId);
            });
        });

        function setupUser() {
            <%
                User user = (User) session.getAttribute("user");
            %>
            $("#userMessage").text("Welcome " + "<%= user.getName() %>" + "!");

            console.log("user=", "<%= user %>");
        }
        
        function getPhotos() {
            AJAXgetPhotos(topNo, function (result) {
                photos = result;
                console.log("photos=", photos);

                showPhotos();
            });
        }
        
        function showPhotos() {
            if (photos == null || photos.length == 0) {
                $("photoItems").html("<i>There are no photos stored...</i>");
            }
            else {
                let text = "";

                for (id in photos) {
                    let photo = photos[id];

                    text += "<div style='border: 1px black solid; float: left; margin-right: 0.5cm; margin-bottom: 1cm'>" +
                            "<img src=\"photos/" + photo["source"] + "\" alt=\"" + photo["source"] + "\" style=\"height: 50%\">" +
                            "<br><br>" +
                            "<i>" +
                            "Posted by: " + photo["user"]["name"] + " . " +
                            "Votes: " + photo["votes"] + " . " +
                            "</i>" +
                            "<br><br><br>" +
                            (photo["user"]["id"] != <%= user.getId() %>
                                ?   "<b>Vote</b>" +
                                    "<br><br>" +
                                    "<label>Rank: </label>" +
                                    "<input type='text' placeholder='rank' class='voteInput' id='" + photo["id"] + "'>" +
                                    "<br>" +
                                    "<input type='button' value='Vote' class='voteBtn' id='" + photo["id"] + "'>"
                                :   ""
                            ) +
                            "</div>";
                }

                $("#photoItems").html(text);
            }
        }
        
        function addPhoto() {
            let source = $("#uploadSource").val();

            AJAXaddPhoto(
                <%= user.getId() %>,
                source,
                function (result) {
                    if (result) {
                        alert("Photo added!");
                        getPhotos();
                    }
                    else {
                        alert("Photo not added due to error...");
                    }
                }
            );
        }
        
        function votePhoto(photoId) {
            let userId = <%= user.getId() %>;
            let rank = parseInt( $("#" + photoId + ".voteinput").val() );

            console.log("photoId=", photoId);
            console.log("userId=", userId);
            console.log("rank=", rank);

            if (isNaN(rank)) {
                alert("Rank must be an integer...");
                return;
            }
            if (rank <= 0) {
                alert("Rank must be a positive integer...");
                return;
            }

            AJAXvotePhoto(
                userId,
                photoId,
                rank,
                function (result) {
                    if (result) {
                        alert("Voted successfully!");
                        getPhotos();
                    }
                    else {
                        alert("You already voted this photo...");
                    }
            });
        }

        function setTop() {
            let no = $("#photoTop").val();
            if (no <= 0) {
                alert("Top no. must be greater than 1...");
                return;
            }

            topNo = no;
            console.log("topNo=", topNo);

            getPhotos();
        }
    </script>
</body>
</html>
