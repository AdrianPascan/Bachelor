import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:footwear_shop/common/Common.dart';
import 'package:footwear_shop/connectivity/ConnectivityStatus.dart';
import 'package:footwear_shop/database/SQLite.dart';
import 'package:footwear_shop/model/Operation.dart';
import 'package:footwear_shop/model/Product.dart';
import 'package:footwear_shop/repository/ProductRepository.dart';
import 'package:footwear_shop/server/Server.dart';
import 'package:numberpicker/numberpicker.dart';
import 'package:provider/provider.dart';

class CartPage extends StatefulWidget {
  @override
  _CartPageState createState() => _CartPageState();
}

class _CartPageState extends State<CartPage> {
  ProductRepository _productInCartRepository;
  String _totalText;

  _CartPageState() {
    _productInCartRepository = ProductRepository();
    _totalText = _getTotalText();
  }

  String _getTotalText() => "Total: ${_productInCartRepository.total()}€";

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: _cartAppBar(),
      body: _cartView(),
    );
  }

  AppBar _cartAppBar() {
    return AppBar(
      title: Text('Cart'),
    );
  }

  Widget _cartView() {
    return FutureBuilder<List<Product>>(
      future: SQLite.getProductsInCart(),

      builder: (context, snapshot) {
        if (snapshot.hasError) {
          return Center(
            child: Text("No connection to the Internet..."),
          );
        }

        if (snapshot.hasData) {
          _productInCartRepository.products = snapshot.data;
          _totalText = _getTotalText();

          if (snapshot.data.isEmpty) {
            return _emptyCartWidget();
          }
          return _notEmptyCartWidget();
        }

        return Center(child: CircularProgressIndicator());
      },
    );
  }

  Widget _emptyCartWidget() {
    return Center(
      child: Text(
          "Your cart is empty..."
      ),
    );
  }

  Widget _notEmptyCartWidget() {
    return Column(
        children: [
          _cartListView(),

          _totalWidget(),
        ]
    );
  }

  Widget _totalWidget() {
    return Text(
      _totalText,
      style: Common.biggerText(),
    );
  }

  ListView _cartListView() {
    var connectionStatus = Provider.of<ConnectivityStatus>(context);

    if (connectionStatus == ConnectivityStatus.WiFi || connectionStatus == ConnectivityStatus.Cellular) {
      Common.performOperations();
    }

    if (connectionStatus == ConnectivityStatus.Offline) {
      Common.showToastNoConnection(context);
    }

    return ListView.builder(
      padding: Common.listViewPadding(),

      shrinkWrap: true,

      itemCount: _productInCartRepository.products.length * 2,

      itemBuilder: _cartItemBuilder,
    );
  }

  Widget _cartItemBuilder(BuildContext context, int index) {
    if (index.isOdd) {
      return Padding(
          padding: EdgeInsets.only(top: 16.0)
      );
    }

    final cartItemIndex = index ~/ 2;
    if (cartItemIndex >= _productInCartRepository.products.length) {
      return null;
    }
    return _cartListItem(_productInCartRepository.products[cartItemIndex]);
  }

  Container _cartListItem(Product product) {
    String _getSubtotalText() => "Quantity: ${product.quantity}\nSubtotal: ${product.subtotal()}€";
    String subtotalText = _getSubtotalText();

    Widget _subtotalWidget() {
      return Text(
        subtotalText,
      );
    }

    AlertDialog _removeCartItemDialog() {
      return AlertDialog(
        title: Text('${product.brand},\n${product.gender} ${product.type}'),

        content: Text('Are you sure you want to remove this item from your cart?'),

        actions: [
          FlatButton(
            child: Text("Yes"),

            textColor: Theme.of(context).primaryColor,

            onPressed: () {
              setState(() {
                product.inCart = 0;
                product.quantity = 0;
                SQLite.updateProduct(product);

                Operation operation = Operation(id: -1, type: 'delete', pid: product.pid);
                SQLite.insertOperation(operation);

                _totalText = _getTotalText();
              });

              Navigator.of(context).pop();
            },
          ),

          FlatButton(
            child: Text("Cancel"),

            color: Theme.of(context).primaryColor,

            onPressed: () => Navigator.of(context).pop(),
          ),
        ],
      );
    }

    void _removeCartItem() {
      showDialog(
          context: context,
          barrierDismissible: true,
          builder: (_) => _removeCartItemDialog()
      );
    }

    NumberPickerDialog _updateQuantityDialog() {
      return NumberPickerDialog.integer(
        title: Text('${product.brand},\n${product.gender} ${product.type}'),

        minValue: 1,

        maxValue: 100,

        initialIntegerValue: product.quantity,
      );
    }

    void _updateQuantity() {
      showDialog<int>(
          context: context,
          barrierDismissible: true,
          builder: (_) => _updateQuantityDialog()
      )
      .then((int newQuantity) {
        if (newQuantity != null) {
          setState(() {
            product.quantity = newQuantity;
            SQLite.updateProduct(product);

            Operation operation = Operation(id: -1, type: 'update', pid: product.pid, quantity: newQuantity);
            SQLite.insertOperation(operation);

            subtotalText = _getSubtotalText();
            _totalText = _getTotalText();
          });
        }
      });
    }

    Widget _updateQuantityWidget() {
      return FlatButton(
        child: Text("Update quantity"),

        color: Theme.of(context).primaryColor,

        textColor: Colors.white,

        onPressed: () => _updateQuantity(),
      );
    }

    return Container(
      padding: Common.listContainerPadding(),

      decoration: Common.listContainerDecoration(context),

      child: Column(
        children: [

          ListTile(
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

            trailing: IconButton(
              icon: Icon(
                  Icons.remove_shopping_cart
              ),
              color: Theme.of(context).accentColor,
              onPressed: () => _removeCartItem(),
            ),
          ),

          Column(
            children: [
              _subtotalWidget(),

              _updateQuantityWidget(),
            ]
          )
        ]
      ),
    );
  }
}

