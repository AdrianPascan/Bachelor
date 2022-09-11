import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:footwear_shop/cart/CartPage.dart';
import 'package:footwear_shop/common/Common.dart';
import 'package:footwear_shop/connectivity/ConnectivityStatus.dart';
import 'package:footwear_shop/database/SQLite.dart';
import 'package:footwear_shop/model/Operation.dart';
import 'package:footwear_shop/model/Product.dart';
import 'package:footwear_shop/repository/ProductRepository.dart';
import 'package:footwear_shop/server/Server.dart';
import 'package:provider/provider.dart';


class MainPage extends StatefulWidget {
  @override
  MainPageState createState() => new MainPageState();
}

class MainPageState extends State<MainPage> {
  ProductRepository productRepository;

  MainPageState() {
    productRepository = ProductRepository();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: _mainAppBar(),
      body: _mainView(),
    );
  }

  AppBar _mainAppBar() {
    return AppBar(
      title: Text('PASCÁNI footwear'),
      actions: [
        IconButton(icon: Icon(Icons.shopping_cart), onPressed: _pushCart),
      ],
    );
  }

  Widget _mainView() {
    return FutureBuilder<List<Product>>(
      future: productRepository.products.isEmpty ? Server.getProducts() : SQLite.getProducts(),

      builder: (context, snapshot) {
        if (snapshot.hasError) {
          return Center(
            child: Text("No connection to the Internet..."),
          );
        }

        if (snapshot.hasData) {
          productRepository.products = snapshot.data;
          SQLite.insertProducts(snapshot.data);

          return _productListView();
        }

        return Center(child: CircularProgressIndicator());
      },
    );
  }

  Widget _productListView() {
    var connectionStatus = Provider.of<ConnectivityStatus>(context);

    if (connectionStatus == ConnectivityStatus.WiFi || connectionStatus == ConnectivityStatus.Cellular) {
      Common.performOperations();
    }

    if (connectionStatus == ConnectivityStatus.Offline) {
      Common.showToastNoConnection(context);
    }

    return ListView.builder(
      padding: Common.listViewPadding(),

      itemCount: productRepository.products.length * 2,

      itemBuilder: _productItemBuilder,
    );
  }

  Widget _productItemBuilder(BuildContext context, int index) {
    if (index.isOdd) {
      return Common.listViewPaddingInBetween();
    }

    final productIndex = index ~/ 2;
    if (productIndex >= productRepository.products.length) {
      return null;
    }
    return _productListItem(productRepository.products[productIndex]);
  }

  Container _productListItem(Product product) {
    final addedToCart = product.inCart == 1;

    return Container(
      padding: Common.listContainerPadding(),

      decoration: Common.listContainerDecoration(context),

      child: ListTile(

        isThreeLine: true,

        title: Text(
          "${product.brand}",
        ),

        subtitle: Text(
            "${product.gender} ${product.type}\n\n${product.price}€"
        ),

        leading: Image.asset(
          "assets/images/${product.image}",
        ),

        trailing: Icon(
          addedToCart ? Icons.remove_shopping_cart : Icons.add_shopping_cart,
          color: addedToCart ? Theme.of(context).accentColor : Theme.of(context).highlightColor,
        ),

        onTap: () {
          setState(() {
            if (addedToCart) {
              product.inCart = 0;
              product.quantity = 0;
              SQLite.updateProduct(product);

              Operation operation = Operation(id: -1, type: 'delete', pid: product.pid);
              SQLite.insertOperation(operation);

              // var connectionStatus = Provider.of<ConnectivityStatus>(context);
              // if (connectionStatus == ConnectivityStatus.WiFi || connectionStatus == ConnectivityStatus.Cellular) {
              //   Common.performOperations();
              // }
              // if (connectionStatus == ConnectivityStatus.Offline) {
              //   Common.showToastNoConnection(context);
              // }
              // Server.hasConnection().then((connected) {
              //   if (connected) {
              //     Common.performOperations();
              //   }
              //   else {
              //     Common.showToastNoConnection(context);
              //   }
              // });
            } else {
              product.inCart = 1;
              product.quantity = 1;
              SQLite.updateProduct(product);

              Operation operation = Operation(id: -1, type: 'add', pid: product.pid);
              SQLite.insertOperation(operation);
              // var connectionStatus = Provider.of<ConnectivityStatus>(context);
              // if (connectionStatus == ConnectivityStatus.WiFi || connectionStatus == ConnectivityStatus.Cellular) {
              //   Common.performOperations();
              // }
              // if (connectionStatus == ConnectivityStatus.Offline) {
              //   Common.showToastNoConnection(context);
              // }
              // Server.hasConnection().then((connected) {
              //   if (connected) {
              //     Common.performOperations();
              //   }
              //   else {
              //     Common.showToastNoConnection(context);
              //   }
              // });
            }
          });
        },
      ),
    );
  }

  void _pushCart() {
    Navigator.of(context).push(
        MaterialPageRoute<void>(
          builder: (BuildContext context) {
            return CartPage();
          },
        )
    )
    .then((value) {
      setState(() {
      });
    });
  }
}