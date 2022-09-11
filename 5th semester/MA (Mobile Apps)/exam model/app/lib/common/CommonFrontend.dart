
import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';

class CommonFrontend {
   static final ThemeData theme = ThemeData(
     primaryColor: Colors.grey[850],
     accentColor: Colors.grey[500],
     highlightColor: Colors.deepOrangeAccent,
     visualDensity: VisualDensity.adaptivePlatformDensity
  );

   static const String title = 'Exams';
   static const String teacherLabel = 'Teacher';
   static const String studentLabel = 'Student';
   static const String statsLabel = 'Stats';

   static void showToast(BuildContext context, String message) {
     Fluttertoast.showToast(
       msg: message,
       toastLength: Toast.LENGTH_SHORT,
       gravity: ToastGravity.BOTTOM,
       timeInSecForIosWeb: 1,
     );
   }
}