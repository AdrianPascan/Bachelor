import 'package:app/app/App.dart';
import 'package:app/connectivity/ConnectivityService.dart';
import 'package:app/database/MyDatabase.dart';
import 'package:flutter/material.dart';
import 'package:sqflite/sqflite.dart';

import 'common/CommonFrontend.dart';

void main() {
  WidgetsFlutterBinding.ensureInitialized();

  MyDatabase.initialize();

  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: CommonFrontend.title,
      // theme: CommonFrontend.theme,
      home: App(),
    );
  }
}
