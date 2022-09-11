<html>
    <head>
        <title>Lab. 6: PHP + Ajax + JSON</title>

        <script src=jquery-3.5.0.min.js></script>    

    </head>

    <body>
        <header>
            <div>
                <p>
                    <h1>Welcome to MyBookStore</h1>
                </p>
            </div>
        </header>

        <hr>

        <br>

        <main>
            <div>
                <form>
                    Cathegory:
                    <input id="cathegory" type="text">
                    <input id="filterBtn" type="button" value="Filter">
                </form>
                <br>

                <div id="info">
                </div>

                <input id="previousBtn" type="button" value="Previous page">
                <input id="nextBtn" type="button" value="Next page">
            </div>

            <br>
            <hr>

            <div>
                <h3>CART</h3>

                <br>

                <form>
                    Id:
                    <input id="id" type="text">
                    Quantity: 
                    <input id="quantity" type="text">
                    <input id="addBtn" type="button" value="Add">
                </form>

                <form>
                    Id:
                    <input id="delete" type="text">
                    <input id="deleteBtn" type="button" value="Delete">
                </form>

                <br>

                <div id="cart">
                    Empty
                </div>
            </div>
        </main>

        <script>
            var books = null;
            var currentPage = 0;
            var booksOnPage = 4;

            function showPage(page) {
                var text = "<p><i>BOOKS:</i><br>";

                for (var currentBook = page * booksOnPage; currentBook < books.length && currentBook < (page + 1) * booksOnPage; currentBook++) {
                    var book = books[currentBook];
                    text += "<br>" + book["id"] + ": '" + book["title"] + "' - " + book["author"] + " | " + book["cathegory"] + " | " + book["price"];
                }

                text += "</p>"

                $("#info").html(text);
            }

            function showPreviousPage() {
                currentPage -= 1;
                showPage(currentPage);

                if (currentPage == 0) {
                    $("#previousBtn").prop("disabled", true);
                }
                $("#nextBtn").prop("disabled", false);
            }

            function showNextPage() {
                currentPage += 1;
                showPage(currentPage);

                if ((currentPage + 1) * booksOnPage >= books.length) {
                    $("#nextBtn").prop("disabled", true);
                }
                $("#previousBtn").prop("disabled", false);
            }
            
            function getBooks() {
                var cathegory = $("#cathegory").val();
                $.ajax({
                    type: "POST",
                    url: "./filter.php",
                    data: {cathegory: cathegory},
                    success: function (result) {
                        books = $.parseJSON(result);

                        if (books.length == 0) {
                            $("#info").html("<p><i>There are no books matching your cathegory...</i></p>");
                        } else {
                            showPage(0);
                            currentPage = 0;

                            if (books.length <= booksOnPage) {
                                $("#nextBtn").prop("disabled", true);    
                            } else {
                                $("#nextBtn").prop("disabled", false);
                            }
                            $("#previousBtn").prop("disabled", true);
                        }
                    }
                });
            };

            function showCart() {
                $.ajax({
                    type: "POST",
                    url: "./select.php",
                    data: {},
                    success: function (result) {
                        $("#cart").html(result);
                    }
                });
            }

            function addToCart() {
                var id = $("#id").val();
                var quantity = $("#quantity").val();
                $.ajax({
                    type: "POST",
                    url: "./insert.php",
                    data: {id:id, quantity:quantity},
                    datatype: "text",
                    success: function (result) {
                        console.log("Success addToCart");
                        if (result.length > 0) {
                            alert(result);
                        } else {
                            showCart();
                        }
                    }
                });
            }

            function deleteFromCart() {
                var id = $("#delete").val();
                $.ajax({
                    type: "POST",
                    url: "./delete.php",
                    data: {id:id},
                    datatype: "text",
                    success: function (result) {
                        console.log("Success deleteFromCart");
                        if (result.length > 0) {
                            alert(result);
                        } else {
                            showCart();
                        }
                    }
                });
            }

            $(document).ready(function() {
                getBooks();
                showCart();

                $("#filterBtn").on("click", function() {
                    getBooks();
                });

                $("#previousBtn").on("click", function() {
                    showPreviousPage();
                });

                $("#nextBtn").on("click", function() {
                    showNextPage();
                });

                $("#addBtn").on("click", function() {
                    addToCart();
                });

                $("#deleteBtn").on("click", function() {
                    deleteFromCart();
                });
            });

        </script>
    </body>
</html>