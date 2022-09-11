<%@ page import="ro.ubb.model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="ro.ubb.model.Asset" %>
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
    <title>Exam model</title>
    <link href="resources/general.css" rel="stylesheet" type="text/css">
    <script src="resources/jquery-3.5.0.min.js"></script>
    <script src="resources/ajax.js"></script>
</head>
<body>
    <header>
        <div>
            <h1>Exam model</h1>
        </div>
    </header>
    
    <hr>

    <main>
        <div>
            <h3 id="user"></h3>

            <form action="logout" method="post">
                <input type="submit" value="Log Out"/>
            </form>
        </div>
        <br>

        <h3>Assets:</h3>
        <div id="assets">
        </div>

        <br>
        <br>

        <div>
            <h4>Add one asset / multiple assets:</h4>

            <form>
                Name:
                <input type="text" id="assetName">
                <br/>

                Description:
                <input type="text" id="assetDescription">
                <br/>

                Value:
                <input type="text" id="assetValue">
                <br/>

                <input type="button" id="addAssetBtn" onclick="addUserAsset()" value="Add">
            </form>
            <br>

            <div id="addedAssetsSection">
                Your added assets:

                <div id="addedAssets">
                </div>

                <br>

                <button onclick="saveAddedUserAssets()">
                    Save assets
                </button>
            </div>
        </div>
    </main>

    <script defer>
        var assets = null;
        var addedAssets = null;

        $(document).ready(function() {
            setupUser();
        });

        function setupUser() {
            <%
                User user = (User) session.getAttribute("user");
            %>;
            $("#user").text("Welcome " + "<%= user.getName() %>" + "!");

            console.log("user=", "<%= user %>");

            getUserAssets();
            showUserAssets();
            showAddedUserAssets();
        };

        function getUserAssets() {
            getAssets(<%= user.getId() %>, function (result) {
                assets = result;
                console.log("assets", assets);
            });
        };

        function showUserAssets() {
            if (assets == null || assets.length == 0) {
                $("#assets").html("<i>You have no assets stored...</i>");
            }
            else {
                var table = "<table>" +
                    "<thead>" +
                    "<th>Name</th>" +
                    "<th>Description</th>" +
                    "<th>Value</th>" +
                    "</thead>";
                for (var asset of assets) {
                    var row = '<tr' + (asset["value"] > 10 ? ' style="background-color: red;">' : '>') +
                        '<td>' + asset["name"] + '</td>' +
                        '<td>' + asset["description"] + '</td>' +
                        '<td>' + asset["value"] + '</td>' +
                        '</tr>';
                    table += row;
                }
                table += "</table>";

                $("#assets").html(table);
            }
        };

        function addUserAsset() {
            let name = $("#assetName").val();
            let description = $("#assetDescription").val();
            let value = parseInt( $("#assetValue").val() );

            if (isNaN(value)) {
                alert("Value must be an integer...");
                return;
            }

            asset = {"name": name,
                     "description" :description,
                     "value": value};

            if (addedAssets == null) {
                addedAssets = [];
            }
            addedAssets.push(asset);

            showAddedUserAssets();
        }

        function saveAddedUserAssets() {
            saveAssets(<%= user.getId() %>, addedAssets, function (result) {
                if ($.parseJSON(result)) {
                    console.log("Assets saved!");
                    alert("Assets were saved!");
                }
                else {
                    console.log("Assets were not saved due to error!");
                }
            });

            addedAssets = null;
            showAddedUserAssets();

            getUserAssets();
            showUserAssets();
        }

        function showAddedUserAssets() {
            if (addedAssets == null) {
                $("#addedAssetsSection").hide();
            }
            else {
                var table = "<table>" + 
                            "<thead>" +
                            "<th>Name</th>" +
                            "<th>Description</th>" +
                            "<th>Value</th>" +
                            "</thead>";
                for (var asset of addedAssets) {
                    var row = '<tr>' +
                              '<td>' + asset["name"] + '</td>' +
                              '<td>' + asset["description"] + '</td>' +
                              '<td>' + asset["value"] + '</td>' +
                              '</tr>';
                    table += row;
                }
                table += "</table>";

                $("#addedAssets").html(table);
                $("#addedAssetsSection").show();

                console.log("addedAssets=", addedAssets);
            }
        }
    </script>
</body>
</html>
