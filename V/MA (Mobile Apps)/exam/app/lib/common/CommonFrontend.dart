
import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';

class CommonFrontend {
   static const String title = 'Rules';
   static const String staffLabel = 'Staff';
   static const String employeeLabel = 'Employee';

   static void showToast(BuildContext context, String message) {
     Fluttertoast.showToast(
       msg: message,
       toastLength: Toast.LENGTH_SHORT,
       gravity: ToastGravity.BOTTOM,
       timeInSecForIosWeb: 1,
     );
   }
}