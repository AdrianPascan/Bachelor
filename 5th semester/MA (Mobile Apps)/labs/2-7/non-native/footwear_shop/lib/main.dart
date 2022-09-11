import 'package:footwear_shop/connectivity/ConnectivityService.dart';
import 'package:footwear_shop/connectivity/ConnectivityStatus.dart';
import 'package:footwear_shop/database/SQLite.dart';

import 'package:flutter/material.dart';
import 'package:footwear_shop/main/MainPage.dart';
import 'package:flutter/widgets.dart';
import 'package:notifier/notifier.dart';
import 'package:provider/provider.dart';

void main() async {
  await SQLite.initialize();
  // await SQLite.populate();

  runApp(
      NotifierProvider(
          child: FootwearShopApp()
      )
  );
}

class FootwearShopApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return StreamProvider<ConnectivityStatus>(
      builder: (context) => ConnectivityService().connectionStatusController,
      child:  MaterialApp(
          title: 'PASCÁNI footwear',
          theme: ThemeData(
            primaryColor: Colors.grey[850],
            accentColor: Colors.grey[500],
            highlightColor: Colors.deepOrangeAccent,
          ),
          home: MainPage()
      )
    );
  }
}

