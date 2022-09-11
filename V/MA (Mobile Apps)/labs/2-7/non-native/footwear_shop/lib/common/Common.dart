import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:footwear_shop/database/SQLite.dart';
import 'package:footwear_shop/model/Operation.dart';
import 'package:footwear_shop/server/Server.dart';
import 'package:path/path.dart';

class Common {
  //Server <-> SQLite
  static void performOperations() async {
    List<Operation> operations = await SQLite.getOperations();

    for (var operation in operations) {
      if (await Server.hasConnection()) {
        if (operation.type == 'add') {
          Server.addProductToCart(operation.pid);
        }
        else if (operation.type == 'delete') {
          Server.deleteProductFromCart(operation.pid);
        }
        else {
          Server.updateProductFromCart(operation.pid, operation.quantity);
        }

        SQLite.deleteOperation(operation.id);
      }
    }
  }

  //UI methods & functions
  static void showToastNoConnection(BuildContext context) {
    Fluttertoast.showToast(
        msg: "No Internet connection: performing changes when you're back online.",
        toastLength: Toast.LENGTH_SHORT,
        gravity: ToastGravity.BOTTOM,
        timeInSecForIosWeb: 1,
        backgroundColor: Theme.of(context).highlightColor,
        textColor: Colors.white,
        // fontSize: 16.0
    );
  }

  //UI
  static EdgeInsets listViewPadding() {
    return EdgeInsets.only(
        top: 16.0,
        left: 8.0,
        right: 8.0
    );
  }

  static Padding listViewPaddingInBetween() {
    return Padding(
        padding: EdgeInsets.only(top: 16.0)
    );
  }

  static EdgeInsets listContainerPadding() {
    return EdgeInsets.symmetric(
      horizontal: 0.0,
      vertical: 8.0,
    );
  }

  static BoxDecoration listContainerDecoration(BuildContext context) {
    return BoxDecoration(
        color: Colors.white,
        boxShadow: [
          BoxShadow(
            color: Theme.of(context).accentColor.withOpacity(0.5),
            blurRadius: 7,
            offset: Offset(0, 3),
          )
        ]
    );
  }

  static TextStyle biggerText() {
    return TextStyle(
      fontSize: 18.0,
    );
  }
}