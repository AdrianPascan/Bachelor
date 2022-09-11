<html>
    <head>
        <title>Exam Model</title>

        <script src=jquery-3.5.0.min.js></script>    

        <style>
            table, th, td {
                border: 1px solid black;
            }
        </style>
    </head>

    <body>
        <div id="fail">
            <header>
                <div>
                    <h1>Fail</h1>
                </div>
            </header>

            <hr>
            <br>

            <main>
                <div>
                    Invalid username and password combination...
                </div>
            </main>
        </div>

        <div id="success"> 
            <header>
                <div>
                    <h1>
                        <h1 id="user"></h1>
                    </h1>
                </div>
            </header>

            <hr>
            <br>

            <main>
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

                    <div id="addedAssetsSection">
                        Your addded assets: 

                        <div id="addedAssets">
                        </div>

                        <button onclick="saveAddedUserAssets()">
                            Save assets
                        </button>
                    </div>

                </div>
            </main>
        </div>

        <script defer>
            var user = null;
            var assets = null;
            var addedAssets = null;

            $(document).ready(function() {
                setupUser();
            });

            function setupUser() {
                getUser();

                if (user == null) {
                    $("#success").hide();
                }
                else {
                    $("#fail").hide();
                    $("#user").text("Welcome " + user["username"] + "!");
                    
                    getUserAssets();
                    showUserAssets();
                    showAddedUserAssets();
                }
            };

            function getUser() {
                user = <?php
                    $connection = new mysqli("localhost", "root", "", "exam_model");
                    if ($connection->connect_error) {
                        die('Could not connect: ' . $connection->connect_error);
                    }
                
                    if (isset($_POST["username"], $_POST["password"])) {
                        $username = $_POST["username"];
                        $password = $_POST["password"];
                    
                        $sql = "SELECT * FROM users WHERE username LIKE '" . $username . "' AND password LIKE '" . $password . "'";
                    
                        $result = $connection->query($sql);
                    
                        $user = NULL;
                    
                        if ($result->num_rows > 0) {
                            $user = $result->fetch_assoc();
                        }
                    
                        $connection->close();

                        echo json_encode($user);
                    }
                    else {
                        echo json_encode(null);
                    }
                ?>;
                console.log("user=", user);


                // $.ajax({
                //     type: "POST",
                //     url: "./login.php",
                //     data: {username: <?php echo '"' . $_POST["username"] . '"'; ?>, password: <?php echo '"' . $_POST["password"] . '"'; ?>},
                //     async: false,
                //     success: function (result) {
                //         user = $.parseJSON(result);
                //         console.log("user=", user);
                //     }
                // });
            };

            function getUserAssets() {
                $.ajax({
                    type: "POST",
                    url: "./assets.php",
                    data: {userid: user["id"]},
                    async: false,
                    success: function (result) {
                        assets = $.parseJSON(result);
                        console.log("assets=", assets);
                    }
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
                console.log("Save: addedAssets=", addedAssets);

                $.ajax({
                    type: "POST",
                    url: "./saveAssets.php",
                    data: {userid: user["id"], assets: addedAssets},
                    async: false,
                    success: function (result) {
                        if ($.parseJSON(result)) {
                            console.log("Assets saved!");
                            alert("Assets were saved!");
                        }
                        else {
                            console.log("Assets were not saved due to error!");
                        }
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